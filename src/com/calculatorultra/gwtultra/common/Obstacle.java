package com.calculatorultra.gwtultra.common;



public class Obstacle extends FieldObject {

	public Obstacle(Vector position, UltraController ultraController) {
		this.position = position;
		this.graphics = ultraController.getGraphicsEngine();
		graphics.addToField(this);
	}
}
