# Entity-state attachment migration

This migration is integrated into `port/neoforge-1.21.1`. The earlier description
as an unpublished, uncompiled patch is obsolete. See
[the current port status](neoforge-1.21.1.md) for verified commits and limitations.

`CHAttachments` registers serializable `AttachmentType<CHCapImp>` under
`clanginghowl:misc`. `CHCapHelper` obtains the attached instance via `getData`;
there is no detached fallback provider. The Forge provider and registration
handlers have been removed.

`CHCapSerialization` owns disk/network snapshots independently of gameplay code.
It writes every scalar, clears absent position coordinates when reusing a tag,
clears fields absent from a loaded snapshot and copies mutable positions. This
prevents zero mining progress/resistance updates from retaining previous values.

`CHCapUpdatePacket` snapshots the real attachment, rejects a missing NBT payload,
and applies received state only to living entities. `CHStateSyncEvents` provides
login, respawn, dimension and tracking snapshots. Clone handling resets mining
state. There is deliberately no `copyOnDeath`: the original code did not copy
all state from a dead entity. Non-death NeoForge copies use the serializer.

Twelve real Minecraft-NBT JUnit tests run with the mod loaded. The GameTest suite
also checks attachments through save/load of all nine custom living entity
types. These checks do not substitute for multi-client tracking, reconnect,
death/respawn or End-return playtests.

Movement reports are accepted only for the authenticated sender. The outgoing
network wrapper ignores reports for other entities, and NPC neurotoxin instead
uses server-side mob movement. A GameTest covers this without any connected
client.

Legacy Forge `ForgeCaps` containers are **not** converted into NeoForge attachment
containers. Unchanged inner field names do not imply old-world compatibility.
Use new test worlds and backups.
