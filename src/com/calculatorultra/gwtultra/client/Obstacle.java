package com.calculatorultra.gwtultra.client;


public class Obstacle extends FieldObject {

	public Obstacle(Vector position, GwtUltra gwtUltra) {
		this.position = position;
		this.graphics = gwtUltra.getGraphicsEngine();
		graphics.addToField(this);
	}
}
