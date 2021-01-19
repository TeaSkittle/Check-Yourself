# ![queen](blackqueen.png) Check Yourself

> *Check Yourself* is a simple and minimalistic chess puzzle game where the player solves mate in one problems

- Designed for the casual player
- The goal is to have 100 problems for the player to solve, increasing in difficulty over time
- Will be getting the puzzles from actual chess games, mostly of grandmaster level
- Will be written using Java and JavaFX
- Uses the FEN chess notation format, making the project easily extensible

## Adding Chess Problems

To add your own chess problems, simply take any board position in the FEN format and afterwards add a colon and the correct move,
for example:

```
r1b1qr2/5pBp/2p1p3/p1PpP1R1/P2N1P1k/2P5/6PP/3n2K1 w - - 8 31

then add the correct move to the end: nf3

r1b1qr2/5pBp/2p1p3/p1PpP1R1/P2N1P1k/2P5/6PP/3n2K1 w - - 8 31:nf3
```

Then add this string to file in the resources folder "Problems.txt", and now it has been added to the game. 
To play the problem find the linenumber where it was added ( Problems.txt ) and go to that problem number in the game.
