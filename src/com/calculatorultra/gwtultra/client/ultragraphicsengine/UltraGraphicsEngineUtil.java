package com.calculatorultra.gwtultra.client.ultragraphicsengine;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class UltraGraphicsEngineUtil {
	//static final int ANNIMATION_STEPS = 1;
	public static final int FIELD_OFFSET_X = 70;
	public static final int FIELD_OFFSET_Y = 83;
	public static final int ICON_OFFSET = 10;
	public static final int SPACE_HEIGHT = 50;
	public static final int SPACE_WIDTH = 50;
	//static final int PX_PER_STEP_HORIZONTAL = SPACE_WIDTH / ANNIMATION_STEPS;
	//static final int PX_PER_STEP_VERTICAL = SPACE_HEIGHT / ANNIMATION_STEPS;
	public static final String STYLE_BLUE_SMALL = "blueSmall";
	public static final String STYLE_BLUE_MEDIUM = "blueMedium";
	public static final String STYLE_BLUE_BOLD = "blueBold";
	public static final String STYLE_BLUE_LARGE = "blueLarge";
	public static final String STYLE_GREY_SMALL = "greySmall";
	public static final String STYLE_GREY_MEDIUM = "greyMedium";
	public static final String STYLE_GREY_BOLD = "greyBold";
	public static final String STYLE_GREY_LARGE = "greyLarge";
	public static final String STYLE_GREY_SMALL_CURSOR = "greySmallCursor";
	public static final String STYLE_GREY_MEDIUM_CURSOR = "greyMediumCursor";
	public static final String STYLE_GREY_LARGE_CURSOR = "greyLargeCursor";
	
	public static final String PLAYER = "images/player.png";
	public static final String TARGET = "images/target.png";
	public static final String OBSTICLE = "images/obsticle.png";
	public static final String BACKGROUND = "images/background.png";
	public static final String HUNTER = "images/hunter.png";
	public static final String TONEY_IS_ANGRY = "images/toneyIsAngry.png";
	public static final String RESET_OVERLAY = "images/resetOverlay.png";
	
	public static void setTextAndStyle(FlexTable flexTable, int row, int column, String text, String style) {
		flexTable.setText(row, column, text);
		flexTable.getFlexCellFormatter().setStyleName(row, column, style);
	}
	
	public static Image createImageWithClickHandler(String imageResource, ClickHandler clickHandler) {
		Image image = new Image(imageResource);
		image.addClickHandler(clickHandler);
		return image;
	}
	
	public static Button createButtonWithClickHandler(String buttonLabel, ClickHandler clickHandler) {
		Button button = new Button(buttonLabel);
		button.addClickHandler(clickHandler);
		return button;
	}

	public static Label createLabelWithStyleChange(String labelText, final String style1, final String style2) {
		final Label label = new Label(labelText);
		label.setStyleName(style1);
		label.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				label.setStyleName(style2);
			}
			
		});
		
		label.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				label.setStyleName(style1);
			}
			
		});
		return label;
	}
	
	public static Label createLabelWithStyleChangeAndClickHandler(String labelText, final String upStyle, final String downStyle, ClickHandler clickHandler) {
		final Label label = new Label(labelText);
		label.setStyleName(upStyle);
		label.addClickHandler(clickHandler);
		label.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				label.setStyleName(downStyle);
			}
			
		});
		
		label.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				label.setStyleName(upStyle);
			}
			
		});
		
		label.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				label.setStyleName(upStyle);
			}
			
		});
		return label;
	}
	
	public static Label createLabelWithStyle(String labelText, final String style) {
		final Label label = new Label(labelText);
		label.setStyleName(style);
		return label;
	}
}
