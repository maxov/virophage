# “Virophage”
#### Max Ovsiankin & Leon Ren - Period 1, 5/7/14

## Description

What does it do: Real-Time-Strategy Game

Hexagonal grid
- Every cell can be ‘occupied’ by only one player
- Each cell has an energy level -- this is changed by the player, and reset when a cell is taken
- Energy can be transferred between cells
- Goal is to eliminate all of your opponent’s occupied cells
- At the start of the game, the map is randomly generated with several dead cells, these are colored black

Player-Vs-Player combat
- Each player starts with occupying some number of cells
- Each player starts with some number of energy on every cell
- To take an empty cell, the player must move at least one energy to that cell

![blue taking empty](http://i.imgur.com/lvKi9af.png)
Blue has just infected the middle tile by transferring 1 energy.

To take an opposing player’s cells, a player must move energy greater than the other player’s energy on that cell,
to that cell, the energy formerly on that cell is split between players,
each player can choose to which adjacent tile(if any) the energy goes.

![blue taking red](http://i.imgur.com/eAWatlg.png)
Blue has infected Red’s middle tile by transferring 3 energy. Both players have chosen what to do with their energy.

at least 2 players
- Can choose to be multiplayer or single AI, or AI vs AI

What problems does it solve?
- Our program solves the problem of boredom or lack of entertainment.

Rules?
- There is a hexagonal grid with 2 different players.
- Each player will control an army of “virophages”, or viruses that can infect other viruses.
- Each player will start of with 3 viruses on opposite (west & east) sides of the grid.
- The objective is to eliminate all virus’s of the opposing player.
The detailed mechanisms of movement and “infection” are described in sections 3 - instructions.

Target market?
- Young or middle aged users looking for some entertainment.

Primary features?
- GUI, strategy & gameplay in real time.

Instructions
- There is a hexagonal grid with 2 different players.
- Each player will control an army of “virophages”, or viruses that can infect other viruses.
- Each player will start of with 3 viruses on opposite (west & east) sides of the grid.
- Each virus occupies a cell. Every virus has an amount of energy used to move and kill other viruses.
- Each player starts with occupying some number of cells
- Each player starts with some number of energy on every cell
- The game starts after a 3 - 2 -1 countdown.
- To take an empty cell, the player must move at least one energy to get to that cell. He/She would use the mouse to select an initial space, and then click on one of the highlighted possibilities. All moves are made in real time (i.e there are no turns).
- To take an opposing player’s cells, a player must move energy greater than the other player’s energy on that cell, to that cell, the energy formerly on that cell is split between players, each player can choose to which adjacent tile(if any) the energy goes.
- Each player can make as many movements/transfers of energy as needed in a turn.
- The opposing player acts in real time.
- When a player has eliminated all viruses of the opposing player, he/she has won.

Features List

Must-Haves
- ‘Stupid’ AI
- Occupation of cells
- GUI
- Energy
- Dead cells

Want-To-Haves
- ‘Smart’ AI
- Ability upgrades
- Picture on Background

Stretch Features
- Multiplayer over a network

Class List
- Tissue - the game board
- Virus - a virus, which can occupy one tile at a time
- Player - a player, or a collection of viruses working together
- Cell - a tile
- Location
- Energy
- Dead Cell

Responsibility List 
- GUI - Leon
- Energy System - Leon
- Art - Leon
- Sounds - Leon
- Networking - Max
- AI - Max
- Core Gameplay - Max
