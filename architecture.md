# Architecture
Virophage is a somewhat complex game, with a pretty daunting architecture(see our UML).
However, this architecture is actually meant to make it pretty easy to keep domain logic
(meaning game rules, how the game works) from render logic(how the game is displayed) to
networking/interfacing logic (how the game is shared between players.

Because of the multiple kinds of players(on the same machine, on a networked machine, or just an AI), it
is sometimes difficult to keep track of what's going on. So perhaps it is best to explain how Virophage works
by approaching it from the client and server side.

## Networking
Virophage is your standard, networked real time strategy game.
It is built on the client-server model, but in a special way.

When I start the Virophage client, I can choose to join a game or start a game.
Starting a game gives me the most control over how the game is played, but joining a game is way easier in most cases.

## Domain Logic / Game Logic
In this section, ignore the worries of mutable state, concurrency, and networked players.
The beauty of the design of the game logic is that it is a pure interface with which to modify
the game and recieve updates. Consider it as a constantly updating model with pure actions.

## Client-Side
Because it is somewhat simpler, we will start to explain the client-side architecture of Virophage.