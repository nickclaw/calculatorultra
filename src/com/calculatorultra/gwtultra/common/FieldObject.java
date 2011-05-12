package com.calculatorultra.gwtultra.common;

import com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngine;
import com.google.gwt.user.client.ui.Image;

public abstract class FieldObject {
	protected Vector position;
	protected UltraGraphicsEngine graphics;
	private Image image;
	
	public Image getImage() {
		return image;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public Vector getPosition() {
		return position;
	}
	
}
