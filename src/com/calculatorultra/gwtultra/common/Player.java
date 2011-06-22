package com.calculatorultra.gwtultra.common;

import static com.calculatorultra.gwtultra.common.GwtUltraUtil.INITIAL_TARGET_MOVES;


public class Player extends MovingFieldObject {
	private int score = 0;
	private int targetsMoved = 0;
	public final Vector startPosition;
	public int playerNumber;

	// the positive Y component of the direction vector is down

	public Vector getStartPosition() {
		return startPosition;
	}

	public Player(Vector startPosition, UltraController ultraController, int playerNumber) {
		this.startPosition = new Vector(startPosition);
		this.position = startPosition;
		this.graphics = ultraController.getGraphicsEngine();
		this.playerNumber = playerNumber;
		graphics.addToField(this);
		direction = new Vector(0, 0);
	}
	
	public int getTargetMovesRemaining() {
		return (INITIAL_TARGET_MOVES + score / 10) - targetsMoved;
	}
	
	public void movedTarget() {
		targetsMoved++;
	}

	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

	public void addToScore(int score) {
		this.score += score;
	}

	@Override
	public void move() {
		position.add(direction);
	}

	@Override
	public void setDirection(Vector direction) {
		this.direction = direction;
	}
}
