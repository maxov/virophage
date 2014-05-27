APCS README

HEADER - “Virophage”
By Max Ovsiankin & Leon Ren - Period 1, 5/7/14


1. Description
   1. What does it do?
      1. Real-Time-Strategy Game
      2. NON-INFINITE Hexagonal grid
         1. Every cell can be ‘occupied’ by only one player
         2. Each cell has an energy level -- this is increased every few seconds,and reset when a cell is taken
         3. Energy can be transferred between cells via channels.
         4. The goal is to eliminate all of your opponent’s occupied cells.
         5. At the start of the game, the map is randomly generated with several dead cells, these are colored black
      3. Player-vs-Player combat
         1. Each player starts with occupying 7 cells.
         2. Each player starts with some number of energy on every cell
         3. To take an empty cell, the player must move at least one energy to that cell
         4. >= 2 players
         5. Can choose to be multiplayer or single vs AI.
   1. What problems does it solve?
      1. Our program solves the problem of boredom, or a lack of entertainment.
   1. Rules?
      (IF there is a hexagonal grid with 2 different players)
      2. Each player will control an army of “virophages”, or viruses that can infect other viruses.
      3. Each player will start with 7 viruses on opposite corners (northwest and southeast) of the hexagonal flat-topped grid.
      4. The objective is to eliminate all viruses of the opposing player. The detailed mechanisms of movement and “infection” are described in section 2 - instructions.
   1. Target market?
      1. Young or middle aged users looking for some entertainment.
   1. Primary features?
      1. GUI, strategy & gameplay in real time.
2. Instructions
   1. Click Join Game to join a networked game, or Start Game to host a game networked or against AI players.
   		a. In Start game, you are represented by the server (or a name you choose). Click add AI player to add a machine opponent.
   		b. Check networked game to host a networked game.
   		c. If you choose to join a networked game, enter the IP address of the host in the localHost box.
   2. Each player will control an army of “virophages”, or viruses that can infect other viruses.
   3. The create a channel, click and drag from an occupied cell to one of the highlighted gray areas. Energy will begin pumping to the new location immediately.
   4. Control the center of the tissue to have the advantage, an increased rate in cell energy growth.
   5. Click and drag into an enemy cell to conquer it. Once the energy in your cell exceeds the energy of the enemy cell, 
      it will be yours!
   6. Once a cell is conquered, all channels to and from it are destroyed.
   7. Once a user has won, press ESC to exit a game.
3. Features List
   1. Must-Haves
      1. ‘Stupid’ AI
      2. Occupation of cells
      3. GUI
      4. Energy
      5. Dead cells
   2. Want-To-Haves
      1. ‘Smart’ AI
      2. Buildup of Energy
      3. Ability upgrades
      4. Picture on Background
   3. Stretch Features
      1. Multiplayer over a network
4. Class List
   	Action
	AIPlayer
	AssignPlayer
	BonusCell
	Cell
	Channel
	ClientGame
	ClientLobbyScreen
	ConnectionDialog
	CreditsScreen
	DeadCell
	ErrorPacket
	Event
	Game
	GameClient
	GameConstants
	GameScreen
	HexagonConstants
	InstructionScreen
	Listening
	LobbyPacket
	LobbyScreen
	Location
	MenuScreen
	Packet
	PacketBundle
	PacketStream
	PacketStreamListener
	Player
	PlayerError
	PlayerNameError
	RemotePlayer
	RequestPlayerName
	Selection
	ServerGame
	SocketBundle
	Start
	StartGamePacket
	TextScreen
	Tissue
	TissueSegment
	TissueUpdate
	TooManyPlayersError
	Vector
	Virus

5. Responsibility List 
   1. Main - Leon
   2. GameClient - Leon
   3. Energy System - Leon
   5. GUI - ALL
   7. Networking - Max
   8. AI - Max
   9. Core Gameplay - Max
