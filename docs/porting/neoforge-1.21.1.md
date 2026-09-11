# Clanging Howl: NeoForge 1.21.1 port

## Status and compatibility

This branch now contains a buildable prerelease port, not the earlier
non-compiling migration scaffold. Minecraft **1.21.1**, Java **21** and
NeoForge **21.1.250** are the tested targets. The mod version is
`1.1.0-neoforge-port.1`; use the exact commit/artifact pair when reporting bugs.

Required dependencies: GeckoLib **4.8.2** and Curios **9.5.1**, both their
NeoForge 1.21.1 distributions. JEI **19.27.0.340** and Jade **15.10.5** are
optional integrations included in the development runtime.

This is a version-specific port. The original Forge 1.20.1 `master` has not
been changed. An upstream merge should target a dedicated 1.21.1 branch, not
replace an actively maintained 1.20.1 version without the maintainer's choice.
Original author credits, mod ID, and All Rights Reserved notice are retained.
No public release or permission to redistribute the original assets is implied.

## Migration scope

- Java 21 / ModDevGradle 2.0.146 / Gradle 9.2.1, NeoForge metadata and dependencies.
- Lifecycle, registries, holder-based effects, entity spawn/synchronized-data APIs,
  block entity serialization and inventory capabilities.
- Persistent entity attachments and explicit lifecycle synchronization, full NBT
  snapshots, defensive position/tag copies, and mining-state clone reset.
- Eight typed payloads with explicit directions and version agreement. Movement
  messages accept only their authenticated sender; outgoing reports for other
  entities are filtered. Non-player neurotoxin uses server-side mob velocity.
- Item components, weapon/attribute behavior, fuel and energy storage, recipes,
  all twelve data-driven enchantments, brewing and compatibility integrations.
- Client render/event APIs, six custom arm poses, particle codecs and vertex
  formats. Physical client initialization is isolated from the common entrypoint.
- Required mixin descriptors/access transformers, singular 1.21 datapack paths,
  current recipe/loot schemas and actual regenerated 1.21.1 JSON resources.
- All fourteen original binary gameplay structures retained byte-for-byte.

The main source set is not filtered to hide uncompilable code. Required mixins
remain enabled with `defaultRequire=1`. Development smoke tests live in a separate
source set and are not included in the mod JAR. Datagen cache files are excluded.

## Verification

Checkpoint `e9f87d8897a3291b7fee252742ade9f62f082ee4` passed the entire workflow:
https://github.com/LTDigor/ClangingHowl/actions/runs/34657560063

This is a **real Gradle build with actual dependencies**, not an offline
signature-only compilation. The checkpoint passed 24 JUnit tests without skips,
data generation, four GameTests, client title-screen/resource startup under
software rendering, resource validation and final JAR packaging. Its client probe only checked
startup; log review subsequently found legacy model loader identifiers. The
current revision fixes those identifiers/face data and strengthens the probe
to reject missing models for every registered mod item and block state.

The current workflow repeats these checks on each push and includes a fifth
GameTest for the non-player neurotoxin regression. Consult the run for the exact
commit before treating a newly changed revision as verified. Logs and JARs are
uploaded separately; failed workflows do not publish a successful-build artifact.

Coverage:

| Layer | What is exercised |
| --- | --- |
| JUnit | All eight payload codecs, all 32 meteor flag combinations, malformed/null payload cases, tag copying and twelve entity-state serialization cases |
| GameTest | Nine custom living entities tick and save/load; eleven block entity types tick and save/load; item energy copy/save independence; twelve enchantments, mod recipes and meteor world-data registration |
| Regression GameTest | Moving NPC takes neurotoxin damage on the server without connected clients; stationary NPC does not |
| Client smoke | Actual client reaches the title screen after resource reload; all registered item/block-state models resolve; all six custom arm poses and three weapon client extensions exist |
| Static contracts | Metadata/network/attachment/resource structure; exact retention of fourteen gameplay structures |

The entity smoke test intentionally disables AI and sets invulnerability to
isolate basic construction, ticking and persistence. It does **not** establish
combat or AI behavior. Client startup is not a human visual review or an
in-world render/playthrough test.

## Reproduce

```sh
bash ./gradlew --no-daemon clean build
bash ./gradlew --no-daemon runData
bash ./gradlew --no-daemon runGameTestServer
# Interactive client:
bash ./gradlew runClient
# Headless Linux startup probe (requires Xvfb and Mesa):
mkdir -p run-client-smoke
printf 'onboardAccessibility:false\n' > run-client-smoke/options.txt
LIBGL_ALWAYS_SOFTWARE=true xvfb-run -a bash ./gradlew runClientSmoke
```

The client probe exits itself after success and emits
`CLANGINGHOWL_CLIENT_SMOKE_PASS`. All commands must exit successfully. Do not
skip tests or suppress mixin failures to manufacture a passing build.

## Manual validation still required

Before calling this a production release, test the packaged JAR with a separate
client on a normal dedicated server. Check player combat/AI, every weapon's
energy/fuel behavior, Curios activation, jet boots, death/respawn, dimension
changes, reconnects, meteor events, worldgen and cross-mod interactions. Review
models, sounds and overlays in-world. Performance has not been benchmarked.

**Use new disposable worlds.** Legacy Forge `ForgeCaps` save-container migration
is not implemented, and compatibility with existing Forge 1.20.1 saves is not
claimed. Keep backups and do not replace the mod on a production world based
only on these automated probes.

## References

- https://github.com/Polarice3/ClangingHowl/tree/2c8d7cea51f776770e1ab82bd484a6640fa194eb
- https://github.com/NeoForgeMDKs/MDK-1.21.1-ModDevGradle
- https://github.com/neoforged/ModDevGradle
- https://docs.neoforged.net/primer/docs/1.21/
- https://docs.neoforged.net/docs/1.21.1/networking/payload/
- https://docs.neoforged.net/docs/1.21.1/datastorage/attachments/
- https://docs.neoforged.net/docs/1.21.1/misc/gametest/
