# NeoForge 1.21.1 compiler checkpoint

Base snapshot: `55a6f173cd5c99a7883538354103a5f03c1af027`.

The complete production Java source tree now compiles against the real dependency ABI
exported by the earlier GitHub Actions run. The ABI archive contains signatures only;
it was not used to execute the game or the unit tests and must never be distributed
as an executable dependency. Java compilation has no errors, with deprecation warnings.

This checkpoint also migrates the item attributes and use actions, six extensible arm
poses, vanilla enchantment calls, block interactions, entity AI API, damage events,
registry-aware persistence, data paths and loot/recipe component schemas. All fourteen
binary structure templates are preserved byte-for-byte in the new `structure` directory.
The old SRG access transformer is no longer needed: direct source access is validated
against the unmodified API, and the one remaining private timestamp uses a named mixin
accessor. Mandatory mixins remain enabled.

Real Gradle build, JUnit, data generation and runtime verification must be recorded
separately using the actual dependency implementations, not the offline ABI.
