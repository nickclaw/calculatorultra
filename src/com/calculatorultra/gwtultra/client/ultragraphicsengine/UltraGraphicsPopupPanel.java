package com.calculatorultra.gwtultra.client.ultragraphicsengine;

import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.STYLE_BLUE_MEDIUM;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.STYLE_GREY_MEDIUM_CURSOR;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.createLabelWithStyleChangeAndClickHandler;

import com.calculatorultra.gwtultra.common.Vector;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

abstract class UltraGraphicsPopupPanel extends DialogBox {
	protected final FlexTable flexTable = new FlexTable();
	protected UltraGraphicsEngine ultraGraphicsEngine;
	protected Vector position;
	
	UltraGraphicsPopupPanel(final String title, final UltraGraphicsEngine ultraGraphicsEngine, final Vector position) {
		this.ultraGraphicsEngine = ultraGraphicsEngine;
		this.position = position;
	
		this.setGlassStyleName("glass");
		this.setStyleName("popup");
		
		Label closeButton = createLabelWithStyleChangeAndClickHandler("X", STYLE_BLUE_MEDIUM, STYLE_GREY_MEDIUM_CURSOR, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
				ultraGraphicsEngine.setFocus(true);
			}
		});
		
		flexTable.setCellPadding(7);
		flexTable.setCellSpacing(0);
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		flexTable.setText(0, 0, title);
		flexTable.getFlexCellFormatter().setStyleName(0, 0, STYLE_BLUE_MEDIUM);
		flexTable.setWidget(0, numberOfColumns(), closeButton);
		flexTable.getRowFormatter().setStyleName(0, "flexTableHeader");
		
		
		setUpFlexTable();
		
		HorizontalPanel mainPanel = new HorizontalPanel();
		mainPanel.setStyleName("ultraGraphicsEngine");
		mainPanel.add(flexTable);
		
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);
		this.setWidget(mainPanel);
		this.setPopupPosition(position.x, position.y);
		
	}

	abstract void setUpFlexTable();
	
	abstract int numberOfColumns();
	
}
