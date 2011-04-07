package com.calculatorultra.gwtultra.client;

public abstract class MovingFieldObject extends FieldObject{
	protected GwtUltra gwtUltra;
	protected Vector startPosition;
	protected Vector direction;
	
	abstract void move();

	public void setDirection(Vector direction) {
		this.direction = direction;
	}
}
