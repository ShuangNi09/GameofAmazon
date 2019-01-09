# GameofAmazon

The game of Amazons is a simple but rather interesting board game, usually for two players. It was invented in 1988 by Walter Zamkauskas of Argentina, and originally called El Juego de las Amazonas (now a trademark of Ediciones de Mente). The board is a 10 by 10 chessboard. Each player gets four "amazons" (represented as chess queens), white for the player who moves first, and black for the opponent.

Each move consists of two parts: first, an amazon of the appropriate color makes a chess queen move—any non-zero number of squares in a straight line horizontally, vertically, or diagonally with the restriction that the piece may not move onto or through an occupied square. Pieces are not captured in this game; the board keeps getting fuller. Next, the piece that moved "throws a spear" from her final position. Spears move exactly like pieces, and are subject to the same restrictions. The spear sticks permanently in the (previously empty) square in which it lands. 

A player who has no legal move loses.

## Commands

When running from the command line, the program will accept the following commands, which may be preceded by whitespace.

new: End any game in progress, clear the board to its initial position, and set the current player to white.
A move, either in the format described in Notation or (for convenience) with blanks replacing punctuation, as in g1 c5 e7.
seed N: If the AIs are using random numbers for move selection, this command seeds their random-number generator with the integer N. Given the same seed and the same opposing moves, an AI should always make the same moves. This feature makes games reproducible.
auto C: Make the C player an automated player. Here, C is "black" or "white", case-insensitive.
manual C: Make the C player a human player (entering moves as manual commands).
quit: Exit the program.

## Play the game
Run 
java -ea amazons.Main --display 
will display the GUI. 
