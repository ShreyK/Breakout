# Breakout
CS349 - UI Assignment 1: Built the Breakout Game

Commands:
make

make clean

make run FPS="60" SPEED="200"

-------------------

commmands need to be typed exactly the same way for program to run with any given value

A - Move Player Left
D - Move Player Right
Mouse - Player moves with mouse pointer
Space - Add a new Ball (SPEACIAL ADDITION) - Makes game a whole lot more fun and harder :D
        You can only add a ball every 2-3 seconds or so!
ESC - Pause Game

While Paused you can hit Q to quit game.

You have 3 lives. Each time the ball reaches the bottom of the screen, you loose a life
On loosing all 3 lives, you can restart the game if you wish by pressing any KEY (not spacebar, keys like "q,w,e,r,t,y..."


200 is a good speed for the ball to run at, but you can play around with it. The speed has been set so as to move the same distance
in the same time no matter what the FPS.

There are some wierd collision issues, but I've heard that others are experiencing the same thing with the Java 2D Rectangle classes.

Enjoy!
