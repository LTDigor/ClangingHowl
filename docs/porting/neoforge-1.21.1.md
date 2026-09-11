# NeoForge 1.21.1 port: initial migration status

Follow-up: [entity-state attachment migration](attachments-followup.md). The
original preparation history below is retained; the follow-up updates the
capability integration points but is also uncompiled and not release-ready.

**Status: WIP, not merge-ready, not a working mod. The source tree still contains known incompatible Forge 1.20.1 APIs and does not constitute a compilable NeoForge 1.21.1 port.**

Prepared against `Polarice3/ClangingHowl`, `master`, commit
`2c8d7cea51f776770e1ab82bd484a6640fa194eb`.
This patch switches the development target; it does not preserve a simultaneous Forge 1.20.1 build. A finished port should live on the maintainer's chosen 1.21.1 branch rather than replacing a maintained 1.20.1 branch accidentally.

## Implemented in this patch

- Configure Java 21, ModDevGradle 2.0.146 and NeoForge 21.1.250 for Minecraft 1.21.1. Update the wrapper distribution URL to Gradle 9.2.1. The wrapper JAR/scripts have not been regenerated here.
- Replace the legacy loader descriptor with generated `META-INF/neoforge.mods.toml`; declare required NeoForge/GeckoLib/Curios and optional JEI/Jade dependencies. Preserve the original mod ID, credits, authors and All Rights Reserved notice. Use an explicit prerelease version, `1.1.0-neoforge-port.1`.
- Select NeoForge dependency artifacts, remove the Forge 1.20.1 testing-mod list, use named mappings without legacy reobfuscation and update resource-pack metadata. Keep all required mixins and enable access-transformer validation.
- Replace the `SimpleChannel` transport for eight packets with `CustomPacketPayload` types and `StreamCodec`s, registered on `RegisterPayloadHandlersEvent`. Preserve packet constructors and serialized field ordering. This is a new protocol, not wire-compatible with the old Forge channel.
- Register exactly three serverbound and five clientbound play payloads. Require matching payload versions and dispatch handlers on the main thread.
- Retain the existing send-wrapper names using NeoForge `PacketDistributor`; update the direct Curios keybind call in `ClientEvents` and remove the old common-setup registration call.
- Move client handling into `CHClientPayloadHandlers`; common payload declarations no longer import Minecraft client classes. Dedicated-server class loading is still untested.
- Restrict movement reports to the authenticated sender's entity ID. Existing movement-related behavior must be checked in multiplayer, including any non-player call sites found in the full tree.
- Snapshot capability NBT when constructing a packet and reject a missing NBT tag on decode. **This is transport hardening only, not a capability-to-attachment migration.**
- Add 12 JUnit codec test methods covering all eight packets, all 32 meteor-state flag combinations, payload IDs, NBT copies/nulls and truncated movement payloads. Add a separate Python static-scaffold checker.

## Known blockers already visible in inspected source

1. `ClangingHowl.java` still uses Forge lifecycle, registration and config APIs, the old biome-modifier serializer type, old brewing setup, `SpawnPlacements.Type`, and legacy Curios IMC. Only the obsolete networking registration and `ResourceLocation` construction were changed here. Migrate the mod entry point and the registration helpers together; a bulk import replacement is insufficient.
2. The follow-up replaces the inspected provider/access/packet/clone paths with a serializable attachment and adds lifecycle synchronization. Its Java compilation, full-tree reference audit and multiplayer behavior remain unverified; see `attachments-followup.md`. Legacy Forge save-container migration is not implemented.
3. Apart from its direct packet send, `ClientEvents.java` is unchanged: Forge GUI overlays, event annotations and tick handling still need migration. Port rendering/event APIs before claiming client support.
4. `LivingEntityMixin` still shadows the old `hasEffect(MobEffect)` signature. Audit every mixin descriptor against 1.21.1; retaining `required=true` and `defaultRequire=1` is intentional. Access-transformer targets have not been validated against Minecraft here.

## Subsystems not completed or fully audited

The complete repository was not cloned in the preparation environment. An exact subset of inspected source files was reconstructed and checked against GitHub blob hashes. Do not interpret this document as an exhaustive compiler-error inventory.

The remaining work includes registry/holder changes, entity APIs and synchronized data, item data components and attributes, data-driven enchantments, recipe codecs/inputs, block entities, biome modifiers/world generation, GeckoLib/Curios/JEI/Jade integration changes, and generated data resources. Review each subsystem against the official 1.20.1-to-1.21.1 migration guidance and real compiler diagnostics.

Search the **full checkout**, not just the changed files:

```bash
rg 'net\.minecraftforge|FMLJavaModLoadingContext|ModLoadingContext|ForgeRegistries' src/main/java
rg 'CHNetwork\.(INSTANCE|nextID|init)|SimpleChannel|NetworkEvent|PacketDistributor\.SERVER' src/main/java
rg 'getCapability|LazyOptional|AttachCapabilitiesEvent' src/main/java
rg 'getOrCreateTag|\.getTag\(|\.setTag\(' src/main/java
rg 'SpawnPlacements\.Type|defineSynchedData|hasEffect\(MobEffect' src/main/java
```

Audit the send-wrapper callers and movement-report producer(s). Verify that each serverbound effect is authorized from actual server-side equipment, fuel and cooldown state; this patch does not establish those gameplay invariants.

## Verification actually performed

- Reconstructed 18 existing files were matched byte-for-byte to the Git blob SHA-1 values returned by GitHub.
- Python static checks ran on the pre-migration scaffold and failed, then passed after the migration. These checks inspect source/configuration structure; they are not Java or gameplay tests.
- JDK 21 parsed the changed/added Java files without syntax errors. This did **not** resolve symbols or compile them.
- The exported diff was checked for whitespace errors and applied to a clean reconstruction of the inspected upstream file subset. The result was compared byte-for-byte with the edited files.

**Not performed:** Gradle dependency resolution, wrapper regeneration, Java compilation, JUnit execution, data generation, client startup, dedicated-server startup, multiplayer, world-save migration or performance tests. The preparation container had Java 21 but no Gradle executable or working network route for cloning/downloading dependencies. There is no compiled JAR in this patch.

## Required completion gates in a full checkout

Install JDK 21 and obtain a complete checkout with the original Gradle wrapper. First run:

```bash
java -version
./gradlew --no-daemon help
./gradlew --no-daemon compileJava
```

Use the actual failures to complete the migration. Do not exclude Java source, suppress mixin failures, disable access-transformer validation or skip failed tasks to present this as a successful port.

Once all production code compiles:

```bash
./gradlew --no-daemon test
./gradlew --no-daemon runData
./gradlew --no-daemon clean build
./gradlew runServer
./gradlew runClient
```

Review generated-resource changes, including singular 1.21 data directory names and updated loot/recipe/registry schemas. A resource `pack_format` change alone does not migrate the data pack. Review/accept the Minecraft EULA yourself when required by a local test server.

Before release, exercise new-world creation, all registered entities, weapon energy/fuel behavior, Curios activation and jet boots, sound/camera effects, meteor-state synchronization, death/respawn, dimension changes, reconnects and server restarts. Test the packaged JAR on a dedicated server with a separate client, and verify that incompatible protocol versions cannot connect. Use disposable worlds or backups, not production saves.

## Primary references used

- Original source: https://github.com/Polarice3/ClangingHowl/tree/2c8d7cea51f776770e1ab82bd484a6640fa194eb
- Official 1.21.1 MDK: https://github.com/NeoForgeMDKs/MDK-1.21.1-ModDevGradle
- ModDevGradle build and unit-test setup: https://github.com/neoforged/ModDevGradle
- Payload registration: https://docs.neoforged.net/docs/1.21.1/networking/payload/
- Loader metadata: https://docs.neoforged.net/docs/1.21.1/gettingstarted/modfiles/
- Minecraft 1.21 migration primer: https://docs.neoforged.net/primer/docs/1.21/
- Minecraft resource/data pack versions: https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21
- GeckoLib NeoForge 1.21.1 4.8.2 distribution: https://www.curseforge.com/minecraft/mc-mods/geckolib/files/7023453
- Curios NeoForge 1.21.1 9.5.1 distribution: https://www.curseforge.com/minecraft/mc-mods/curios/files/6529130
- JEI NeoForge 1.21.1 19.27.0.340 distribution: https://www.curseforge.com/minecraft/mc-mods/jei/files/7420587
- Jade NeoForge 1.21.1 15.10.5 distribution: https://www.curseforge.com/minecraft/mc-mods/jade/files/7545219

Dependency versions are selected build targets, not a claim that they are the latest releases. Their artifacts were identified through project/distribution sources; Gradle resolution of the selected coordinates remains unverified.
