package com.calculatorultra.gwtultra.common;


public abstract class MovingFieldObject extends FieldObject{
	protected UltraController ultraController;
	protected Vector startPosition;
	protected Vector direction;
	
	abstract void move();

	public void setDirection(Vector direction) {
		this.direction = direction;
	}
}
