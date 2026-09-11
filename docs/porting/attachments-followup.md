# Entity-state migration follow-up

Base: `ac35d773d7fab130f8c0050feaa47d2aa5f429c1`, branch `port/neoforge-1.21.1`.

**Local, uncompiled follow-up patch. Not a complete port and not published to GitHub.**

## Changes

`CHAttachments` registers a serializable `AttachmentType<CHCapImp>` as
`clanginghowl:misc` on the NeoForge mod event bus. The retained `CHCapHelper`
accessor now retrieves the attached instance through `getData`; it no longer
returns a detached fallback instance. The old Forge provider and its
registration/attachment handlers are removed.

`CHCapSerialization` owns the snapshot format independently of the gameplay
helper. It writes and reads all scalar values and explicitly clears an absent
mining position. In the previous code, zero mining progress and zero resistance
were omitted during serialization, but missing fields were not cleared when
loading into an existing client state. That could retain stale values. This
patch treats both disk data and network updates as full snapshots. Tag names
remain unchanged. Reusing a tag no longer retains stale position coordinates.
Mutable mining positions are copied on assignment.

`CHCapUpdatePacket` reads the actual attachment. Its wire ID and layout remain
unchanged. The client handler only applies entity state to `LivingEntity`.
`CHStateSyncEvents` supplies initial snapshots for login, respawn, dimension
changes and tracking. The old clone handler is removed from `CHEvents`; the new
NeoForge handler preserves its mining-reset behavior without `reviveCaps`.
There is deliberately no `copyOnDeath` option: the old clone code did not copy
persistent state from the original entity. NeoForge's non-death copy path uses
the serializable attachment; mining is reset after cloning.

The build workflow uses read-only repository permissions, checkout without
persisted credentials, and actions pinned to verified commit SHAs. It runs the
static contracts, real Gradle compilation/JUnit, data generation and JAR build.
It retains diagnostics on failure and produces no JAR artifact after a failed
step. It has not been uploaded or executed here. It does not test client,
dedicated-server or multiplayer startup.

## Validation and limits

Eight attachment-source contract checks were executed locally. They check
source structure only. Twelve real Minecraft-NBT JUnit tests were added but
could not be executed: this environment could not download the repository or
Gradle dependencies. Java syntax parsing does not resolve symbols or validate
NeoForge method signatures. See the delivery verification report for exact
coverage; it is not a full source-tree build.

The follow-up patch does not port the remaining Forge event subscriptions,
registries, item components, enchantments, entity APIs, rendering, resource
data, third-party integration or mixin descriptors. In particular, `CHEvents`
still contains unrelated Forge APIs. It is intentionally not made compilable
by deleting those systems or replacing them with no-ops.

A full-tree reference audit is still required. The old movement producer in
`CHEvents` sends a report for every client-side living entity each tick, whereas
the existing WIP payload accepts only the sender's player ID. This is an
identified remaining integration issue, not fixed by this state-storage patch.

NBT field names are retained inside the attachment, but migrating old Forge
`ForgeCaps` save containers into NeoForge attachment containers is **not**
implemented. Use new test worlds; do not assume 1.20.1 world compatibility.

## Required runtime checks

After the complete tree compiles, run:

```sh
bash gradlew --no-daemon test
bash gradlew --no-daemon runData
bash gradlew --no-daemon clean build
bash gradlew runServer
bash gradlew runClient
```

Exercise nonzero-to-zero state updates, saved-state reloads, tracking range
exit/re-entry, death/respawn, End return, dimension changes and reconnects with
a separate client on a dedicated server. Verify that neutral entities do not
retain stale mining/resistance data and that each player's attachment is
independent. Review the Minecraft EULA before starting a test server.

## API references checked

- https://docs.neoforged.net/docs/1.21.1/datastorage/attachments/
- https://github.com/neoforged/NeoForge/blob/1.21.1/src/main/java/net/neoforged/neoforge/attachment/AttachmentType.java
- https://github.com/neoforged/NeoForge/blob/1.21.1/src/main/java/net/neoforged/neoforge/common/util/INBTSerializable.java

These references support the API choices; they are not substitutes for
compilation against the selected NeoForge build.
