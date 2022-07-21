# TicTacToe game with CLI and AI working with minimax algorithm(with alpha beta pruning)

here is the CLI command help:

command: /start -<player1> -<player2> 
Description: start the game with given players name.If you choose 'ai' as one of them players you will
play with an AI.if you choose both players as 'ai' then 2 AIs will play with each other.

command:/put <number> 
Description: put your move on the map with given number.it can be only between 1 and 9.

command:/undo
Description: this one will undo your move 

command: /save
Description: save your game on last state that it has.After you saved,CLI will give you a code for load game after.

command:/load
Description: load your game on last state that it was with given code.
