default:
		javac Breakout/*.java -d .

clean:
		$(RM) Breakout/*.class

run: default
		java Breakout/Main ${FPS} ${SPEED}