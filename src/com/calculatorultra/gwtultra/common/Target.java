package com.calculatorultra.gwtultra.common;


public class Target extends FieldObject {
	public int value;

	public Target(Vector startingPosition, GwtUltra gwtUltra) {
		this.position = startingPosition;
		this.graphics = gwtUltra.getGraphicsEngine();
		this.value = 15;
		this.place();
	}

	public void place() {
		graphics.addToField(this);
	}

	public void remove() {
		graphics.removeFromField(this);
	}
}
