package com.calculatorultra.gwtultra.client;

import com.google.gwt.user.client.ui.Widget;

public class GraphicsObject<TWidget extends Widget> {
	private Vector position;
	private TWidget widget;
	
	public TWidget getWidget() {
		return widget;
	}
	
	public void setWidget(TWidget widget) {
		this.widget = widget;
	}
	
	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}
	
	public void setXPosition(int xPosition) {
		this.position.x = xPosition;
	}
	
	public void setYPosition(int yPosition) {
		this.position.y = yPosition;
	}

	public int getXPosition() {
		return position.x;
	}
	
	public int getYPosition() {
		return position.y;
	}
	
	public GraphicsObject(TWidget widget, Vector position) {
		this.widget = widget;
		this.position = position;
	}
	
	public GraphicsObject(TWidget widget, int xPosition, int yPosition) {
		this.widget = widget;
		this.position = new Vector(xPosition, yPosition);
	}
}
