package com.calculatorultra.gwtultra.common;



public class Obstacle extends FieldObject {

	public Obstacle(Vector position, GwtUltra gwtUltra) {
		this.position = position;
		this.graphics = gwtUltra.getGraphicsEngine();
		graphics.addToField(this);
	}
}
