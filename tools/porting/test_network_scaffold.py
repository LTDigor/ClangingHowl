"""Static migration checks. These do not compile Java or test Minecraft behavior.

Run from any directory with Python 3.11+: python3 tools/porting/test_network_scaffold.py
"""
import json
from pathlib import Path
import re
import tomllib
import unittest

ROOT = Path(__file__).resolve().parents[2]
JAVA = ROOT / 'src/main/java/com/mongoose/clanginghowl'
PACKETS = {
    'CIsMovingPacket': ('common/network/client', 'playToServer'),
    'CActivateCurioKeyPacket': ('common/network/client', 'playToServer'),
    'CJetBootsJumpPacket': ('common/network/client', 'playToServer'),
    'SInstaLookPacket': ('common/network/server', 'playToClient'),
    'SPlayWorldSoundPacket': ('common/network/server', 'playToClient'),
    'SSendCHWorldData': ('common/network/server', 'playToClient'),
    'SReanimatorDeathPacket': ('common/network/server', 'playToClient'),
    'CHCapUpdatePacket': ('common/capabilities', 'playToClient'),
}


def read(path):
    return path.read_text(encoding='utf-8') if path.exists() else ''


class NetworkScaffoldChecks(unittest.TestCase):
    def test_common_entrypoint_has_no_client_class_references(self):
        common = read(JAVA / 'ClangingHowl.java')
        for client_symbol in ['ClientSideInit', 'ClientProxy', 'SIDED_INIT', 'FMLEnvironment.dist']:
            self.assertNotIn(client_symbol, common)
        client = read(JAVA / 'client/ClangingHowlClient.java')
        self.assertIn('@Mod(value = ClangingHowl.MOD_ID, dist = Dist.CLIENT)', client)
        self.assertIn('new ClientSideInit().init(modEventBus)', client)
        self.assertIn('ClangingHowl.PROXY = new ClientProxy()', client)

    def test_target_versions_are_pinned(self):
        props = dict(line.split('=', 1) for line in read(ROOT / 'gradle.properties').splitlines()
                     if line and not line.startswith('#'))
        self.assertEqual('1.21.1', props.get('minecraft_version'))
        self.assertEqual('[1.21.1]', props.get('minecraft_version_range'))
        self.assertEqual('21.1.250', props.get('neo_version'))
        self.assertNotIn('mapping_version', props)

    def test_moddev_replaces_legacy_build(self):
        build = read(ROOT / 'build.gradle')
        self.assertIn("id 'net.neoforged.moddev' version '2.0.146'", build)
        self.assertIn('JavaLanguageVersion.of(21)', build)
        for obsolete in ['fg.deobf', 'reobfJar', 'net.neoforged:forge:',
                         "id 'org.spongepowered.mixin'", 'yeetus-635427']:
            self.assertNotIn(obsolete, build)
        self.assertIn('validateAccessTransformers = true', build)

    def test_neoforge_metadata_replaces_forge_metadata(self):
        template = ROOT / 'src/main/templates/META-INF/neoforge.mods.toml'
        self.assertTrue(template.exists(), 'NeoForge metadata template is missing')
        meta = tomllib.loads(read(template))
        self.assertFalse((ROOT / 'src/main/resources/META-INF/mods.toml').exists())
        self.assertEqual('All Rights Reserved', meta['license'])
        self.assertEqual('clanginghowl', meta['mods'][0]['modId'])
        deps = {dep['modId']: dep for dep in meta['dependencies']['clanginghowl']}
        for mod in ['minecraft', 'neoforge', 'curios', 'geckolib']:
            self.assertEqual('required', deps[mod]['type'])
        for mod in ['jei', 'jade']:
            self.assertEqual('optional', deps[mod]['type'])
        self.assertEqual('clanginghowl.mixins.json', meta['mixins'][0]['config'])
        self.assertTrue(all('mandatory' not in dep for dep in deps.values()))

    def test_metadata_generation_is_wired_into_resources_and_ide(self):
        build = read(ROOT / 'build.gradle')
        self.assertIn('sourceSets.main.resources.srcDir generateModMetadata', build)
        self.assertIn('neoForge.ideSyncTask generateModMetadata', build)
        self.assertIn('inputs.properties replaceProperties', build)

    def test_wrapper_distribution_is_updated(self):
        wrapper = read(ROOT / 'gradle/wrapper/gradle-wrapper.properties')
        self.assertIn('gradle-9.2.1-bin.zip', wrapper)
        self.assertIn('validateDistributionUrl=true', wrapper)

    def test_all_eight_payloads_define_codec_and_unique_ids(self):
        ids = []
        for name, (directory, _) in PACKETS.items():
            with self.subTest(packet=name):
                source = read(JAVA / directory / f'{name}.java')
                self.assertRegex(source, rf'class {name} implements CustomPacketPayload')
                self.assertIn(f'Type<{name}> TYPE', source)
                self.assertIn(f'StreamCodec<FriendlyByteBuf, {name}> STREAM_CODEC', source)
                self.assertIn('return TYPE;', source)
                match = re.search(r'ClangingHowl\.MOD_ID, "([a-z0-9_]+)"', source)
                self.assertIsNotNone(match)
                ids.append(match.group(1))
        self.assertEqual(8, len(ids))
        self.assertEqual(8, len(set(ids)))

    def test_registration_has_exact_directions(self):
        source = read(JAVA / 'common/network/CHNetwork.java')
        found = re.findall(r'registrar\.(playToServer|playToClient)\(\s*(\w+)\.TYPE,\s*\2\.STREAM_CODEC,\s*\2::consume\)', source)
        self.assertEqual({(direction, name) for name, (_, direction) in PACKETS.items()}, set(found))
        self.assertEqual(8, len(found))
        self.assertIn('RegisterPayloadHandlersEvent', source)
        self.assertIn('EventBusSubscriber.Bus.MOD', source)
        self.assertNotIn('playBidirectional(', source)
        self.assertNotIn('.optional()', source)

    def test_handlers_explicitly_execute_on_main_thread(self):
        source = read(JAVA / 'common/network/CHNetwork.java')
        self.assertIn('.executesOn(HandlerThread.MAIN)', source)
        self.assertIn('event.registrar(PROTOCOL_VERSION)', source)

    def test_network_no_longer_imports_forge_networking(self):
        paths = [JAVA / directory / f'{name}.java' for name, (directory, _) in PACKETS.items()]
        paths.append(JAVA / 'common/network/CHNetwork.java')
        for path in paths:
            with self.subTest(path=path.name):
                source = read(path)
                for obsolete in ['net.minecraftforge.network', 'SimpleChannel',
                                 'NetworkEvent.Context', 'setPacketHandled', 'getSender()']:
                    self.assertNotIn(obsolete, source)

    def test_common_payloads_do_not_import_minecraft_client_classes(self):
        for name, (directory, _) in PACKETS.items():
            with self.subTest(packet=name):
                source = read(JAVA / directory / f'{name}.java')
                self.assertNotIn('import net.minecraft.client.', source)
                self.assertNotIn('Minecraft.getInstance()', source)
        client = read(JAVA / 'client/network/CHClientPayloadHandlers.java')
        self.assertIn('Minecraft.getInstance()', client)
        self.assertIn('@OnlyIn(Dist.CLIENT)', client)

    def test_movement_only_updates_the_sender(self):
        source = read(JAVA / 'common/network/client/CIsMovingPacket.java')
        self.assertIn('packet.entityID != player.getId()', source)
        self.assertIn('CHCapHelper.setMoving(player, packet.moving)', source)
        self.assertNotIn('.getEntity(packet.entityID)', source)

    def test_existing_send_wrappers_are_retained(self):
        source = read(JAVA / 'common/network/CHNetwork.java')
        for method in ['sendTo(', 'sendToServer(', 'sentToTrackingChunk(',
                       'sentToTrackingEntity(', 'sentToTrackingEntityAndPlayer(',
                       'sendToALL(', 'sendToClient(', 'sendToClientLevel(']:
            self.assertIn(method, source)
        for method in ['sendToPlayer(', 'sendToPlayersTrackingChunk(',
                       'sendToPlayersTrackingEntity(', 'sendToPlayersTrackingEntityAndSelf(',
                       'sendToAllPlayers(', 'sendToPlayersInDimension(']:
            self.assertIn('PacketDistributor.' + method, source)
        self.assertIn('<MSG extends CustomPacketPayload>', source)

    def test_old_registration_call_is_removed(self):
        source = read(JAVA / 'ClangingHowl.java')
        self.assertNotIn('CHNetwork.init()', source)
        client = read(JAVA / 'client/events/ClientEvents.java')
        self.assertNotIn('CHNetwork.INSTANCE', client)
        self.assertIn('CHNetwork.sendToServer(new CActivateCurioKeyPacket())', client)
        self.assertIn('ResourceLocation.fromNamespaceAndPath(MOD_ID, path)', source)

    def test_nbt_is_snapshot_and_null_wire_nbt_is_rejected(self):
        source = read(JAVA / 'common/capabilities/CHCapUpdatePacket.java')
        self.assertIn('Objects.requireNonNull(tag, "tag").copy()', source)
        self.assertIn('throw new DecoderException(', source)
        self.assertIn('return tag.copy()', source)

    def test_required_mixins_are_not_disabled_to_hide_failures(self):
        mixins = json.loads(read(ROOT / 'src/main/resources/clanginghowl.mixins.json'))
        self.assertIs(True, mixins['required'])
        self.assertEqual(1, mixins['injectors']['defaultRequire'])
        self.assertEqual('JAVA_21', mixins['compatibilityLevel'])
        self.assertNotIn('refmap', mixins)
        self.assertEqual({
            'EnchantRandomlyFunctionMixin', 'EntityMixin', 'FireBlockAccessor',
            'LivingEntityMixin', 'MobEffectInstanceMixin', 'ServerLevelMixin',
            'LivingEntityAccessor',
        }, set(mixins['mixins']))
        self.assertEqual(8, len(mixins['client']))

    def test_junit_codec_tests_cover_all_packets(self):
        source = read(ROOT / 'src/test/java/com/mongoose/clanginghowl/common/network/PayloadCodecTest.java')
        self.assertIn('@Test', source)
        for name in PACKETS:
            self.assertIn(name + '.STREAM_CODEC', source)
        build = read(ROOT / 'build.gradle')
        self.assertIn('useJUnitPlatform()', build)
        self.assertIn('unitTest {', build)


if __name__ == '__main__':
    unittest.main(verbosity=2)
