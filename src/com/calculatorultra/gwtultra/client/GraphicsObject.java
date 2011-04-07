package com.calculatorultra.gwtultra.client;

import com.google.gwt.user.client.ui.Widget;

public class GraphicsObject<T extends Widget> {
	private Vector position;
	private T widget;
	
	public Widget getWidget() {
		return widget;
	}
	
	public void setWidget(T widget) {
		this.widget = widget;
	}
	
	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public int getXPosition() {
		return position.x;
	}
	
	public int getYPosition() {
		return position.y;
	}
	
	public GraphicsObject(T widget, Vector position) {
		this.widget = widget;
		this.position = position;
	}
	
	public GraphicsObject(T widget, int xPosition, int yPosition) {
		this.widget = widget;
		this.position = new Vector(xPosition, yPosition);
	}
}
