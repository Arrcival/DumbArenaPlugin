# DumbArenaPlugin
A minecraft small plugin for a small minigame idea

## What does the plugin do

It gives some commands to run a small arena BR-like type of game, with events happening 'till there's only one player remaining.

There's no limit of players, PvP starts off in a glass arena, and every 10 seconds, a new event happens.
Every 10 (+2 per player) seconds, a lava floor is created or gets higher, so you better always stay high.

Controlling the center is good as some events gives stuff at the middle of the arena.

*Not tested : Each kills should be rewarding a Strength buff to the killer (cumulable)*

---

## Commands

`/newgame <size>` creates a game so people can join. The arena will be created at the position where the player was, in all positive directions.
Only one game can exist at a time.
You can specify an arena size you wish (bigger arenas tends to make the server slightly freeze when it gets created

`/joingame` makes you join the game while it's in pending state, you can not join a game that haven't started or is currently running

`/startgame` starts the game : It creates the arena, teleports the player and clean their inventory
Players inventory are saved and they will be teleported back with their inventory once the game is over

`/exitgame` makes you exit the game if you have joined a game and it hasn't started yet.

`/destroygame` cancels the current game

---

## Events (spoiler)
*Those events have an equal chance to be called every 10 seconds.*
Events are written in the chat for everyone 5 seconds before they happen.

`Generate3DBlocks` generates blocks randomly in the arena, with a specified %.

You can see the list [here](https://github.com/Arrcival/DumbArenaPlugin/blob/1ffaf1244f0f004e548c013cac391907dc502fae/src/main/java/com/arrcival/dumbarenaplugin/gamevents/Generate3DBlocks.java#L21).


`GivePlayersItem` gives every player the same item, picked randomly. Some items are only given once per arena.

You can see the list [here](https://github.com/Arrcival/DumbArenaPlugin/blob/1ffaf1244f0f004e548c013cac391907dc502fae/src/main/java/com/arrcival/dumbarenaplugin/gamevents/GivePlayersItem.java#L19)


`TypeToGet` gives a list of 3 items in the chat for everyone.

The first player to type it will pick the item that will be given for everyone.

You can see the list [here](https://github.com/Arrcival/DumbArenaPlugin/blob/1ffaf1244f0f004e548c013cac391907dc502fae/src/main/java/com/arrcival/dumbarenaplugin/gamevents/TypeToGet.java#L22)


`GenerateTrees` tries to spawn trees at the ground/lava level, on a dirt block.


`RandomEffect` gives a random effect to every players.

The effect is either Absorption, Resistance or Speed. It lasts for 30 seconds.


`ItemDrop` drops one or multiple items in the center of the map (falling from the sky)

You can see the list [here](https://github.com/Arrcival/DumbArenaPlugin/blob/1ffaf1244f0f004e548c013cac391907dc502fae/src/main/java/com/arrcival/dumbarenaplugin/gamevents/ItemDrop.java#L16)


`BlockSpawn` spawns a random block in the right center of the arena (excluding lava)
It can be either a iron, gold or diamond block.


`ChickenLoot` spawns a 1-hp-chicken with a modified loot.

*Bug : If the chicken survives till a second one appears, they will both have the same loot table*

The chosen loot is picked randomly in the GivePlayersItem's item list.


`OneBlockGetsRemoved` removes every block in the arena of the specified type.
