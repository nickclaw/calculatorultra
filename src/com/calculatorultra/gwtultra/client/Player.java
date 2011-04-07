package com.calculatorultra.gwtultra.client;



public class Player extends MovingFieldObject {
	public int score = 0;
	private final Vector startPosition;

	// the positive Y component of the direction vector is down

	public Vector getStartPosition() {
		return startPosition;
	}

	public Player(Vector startPosition, GwtUltra gwtUltra) {
		this.startPosition = new Vector(startPosition);
		this.position = startPosition;
		this.graphics = gwtUltra.getGraphicsEngine();
		graphics.addToField(this);
		direction = new Vector(0, 0);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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
