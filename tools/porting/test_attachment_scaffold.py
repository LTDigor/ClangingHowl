"""Source-contract checks ONLY. These do not compile or execute the Java mod."""
from pathlib import Path
import re
import unittest
ROOT = Path(__file__).resolve().parents[2]
JAVA = ROOT / 'src/main/java/com/mongoose/clanginghowl'

def read(relative):
    path = JAVA / relative
    return path.read_text() if path.exists() else ''

class AttachmentScaffoldChecks(unittest.TestCase):
    def test_attachment_registered_on_mod_bus(self):
        text = read('common/capabilities/CHAttachments.java')
        self.assertIn('EventBusSubscriber.Bus.MOD', text)
        self.assertIn('NeoForgeRegistries.Keys.ATTACHMENT_TYPES', text)
        self.assertIn('AttachmentType.serializable(CHCapImp::new).build()', text)
        self.assertNotIn('.copyOnDeath()', text)

    def test_gameplay_accessor_uses_attached_instance(self):
        text = read('common/capabilities/CHCapHelper.java')
        self.assertIn('player.getData(CHAttachments.STATE)', text)
        self.assertNotIn('new CHCapImp()', text)
        self.assertNotIn('CHCapProvider', text)
        self.assertIn('return CHCapSerialization.load(tag, cap)', text)

    def test_old_provider_and_attachment_events_are_removed(self):
        self.assertFalse((JAVA/'common/capabilities/CHCapProvider.java').exists())
        text = read('init/InitEvents.java')
        for forbidden in ('CHCapProvider', 'RegisterCapabilitiesEvent', 'AttachCapabilitiesEvent', 'net.minecraftforge'):
            self.assertNotIn(forbidden, text)
        self.assertIn('CHCommands.register(commandDispatcher, event.getBuildContext())', text)

    def test_packet_and_client_use_attachment_state(self):
        for path in ['common/capabilities/CHCapUpdatePacket.java', 'client/network/CHClientPayloadHandlers.java']:
            text = read(path)
            self.assertNotRegex(text, r'\b(?:entity|living)\.getCapability\(')
            self.assertNotIn('CHCapProvider', text)
            self.assertIn('CHCapHelper.getCapability(living)', text)
        self.assertIn('entity instanceof LivingEntity living', read('client/network/CHClientPayloadHandlers.java'))

    def test_serialization_does_not_depend_on_gameplay_helper(self):
        text = read('common/capabilities/CHCapSerialization.java')
        self.assertIn('state.setMiningPos(null)', text)
        self.assertIn('state.setMiningProgress(tag.getInt("miningProgress"))', text)
        self.assertIn('state.setTechnoResist(tag.getFloat("technoResist"))', text)
        self.assertNotIn('CHCapHelper', text)
        self.assertNotIn('net.minecraft.client.', text)
        self.assertNotIn('CHNetwork', text)

    def test_state_is_serializable_and_snapshots_position(self):
        text = read('common/capabilities/CHCapImp.java')
        self.assertIn('INBTSerializable<CompoundTag>', text)
        self.assertIn('HolderLookup.Provider provider', text)
        self.assertIn('blockPos.immutable()', text)

    def test_all_initial_sync_lifecycle_hooks_exist(self):
        text = read('common/capabilities/CHStateSyncEvents.java')
        for event in ('Clone', 'StartTracking', 'PlayerLoggedInEvent', 'PlayerRespawnEvent', 'PlayerChangedDimensionEvent'):
            self.assertIn('PlayerEvent.' + event, text)
        self.assertIn('instanceof ServerPlayer', text)
        self.assertNotIn('net.minecraft.client.', text)
        self.assertNotIn('reviveCaps', text)

    def test_real_nbt_regression_tests_are_present(self):
        text = (ROOT/'src/test/java/com/mongoose/clanginghowl/common/capabilities/CHCapSerializationTest.java').read_text()
        self.assertEqual(12, len(re.findall(r'@Test\b', text)))
        self.assertIn('import net.minecraft.nbt.CompoundTag', text)
        self.assertNotIn('Mockito', text)

if __name__ == '__main__':
    unittest.main(verbosity=2)
