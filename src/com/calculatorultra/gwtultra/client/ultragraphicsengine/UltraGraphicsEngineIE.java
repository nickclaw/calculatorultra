package com.calculatorultra.gwtultra.client.ultragraphicsengine;

import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.FIELD_OFFSET_X;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.FIELD_OFFSET_Y;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.HUNTER;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.ICON_OFFSET;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.OBSTICLE;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.PLAYER;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.SPACE_HEIGHT;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.SPACE_WIDTH;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.TARGET;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.TONEY_IS_ANGRY;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.HEIGHT_SPACES;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.WIDTH_SPACES;

import java.util.HashMap;
import java.util.Map;

import com.calculatorultra.gwtultra.common.FieldObject;
import com.calculatorultra.gwtultra.common.GraphicsObject;
import com.calculatorultra.gwtultra.common.Hunter;
import com.calculatorultra.gwtultra.common.Obstacle;
import com.calculatorultra.gwtultra.common.Player;
import com.calculatorultra.gwtultra.common.Target;
import com.calculatorultra.gwtultra.common.UltraController;
import com.calculatorultra.gwtultra.common.Vector;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class UltraGraphicsEngineIE extends UltraGraphicsEngine implements ClickHandler {
	private boolean gameIsOver = false;
	public UltraGraphicsEngineIE(UltraController ultraController) {
		super(ultraController);
	}

	@Override
	public void setUpGraphicsObjectList() {
		//Add all the GraphicsObjects to the ArrayList
		graphicsObjects.add(background);
		graphicsObjects.add(scoreLabel);
		graphicsObjects.add(highScoreLabel);
		graphicsObjects.add(averagePointsLabel);
		graphicsObjects.add(remainingTargetMovesLabel);
		graphicsObjects.add(scoreValueLabel);
		graphicsObjects.add(highScoreValueLabel);
		graphicsObjects.add(averagePointsValueLabel);
		graphicsObjects.add(remainingTargetMovesValueLabel);
		if (GWT.isProdMode()) {
			hitCounter = new GraphicsObject<Image> (new Image("http://simplehitcounter.com/hit.php?uid=1050556&f=65535&b=0"), 0, 0);
			graphicsObjects.add(hitCounter);
		}
		graphicsObjects.add(signInLabel);
		graphicsObjects.add(leaderboardLabel);
		graphicsObjects.add(instructionsLabel);
		graphicsObjects.add(wrappingLabel);
		graphicsObjects.add(chaseLabel);
		graphicsObjects.add(repeatingLabel);
		graphicsObjects.add(sliderBar);
	}

	@Override
	public void resetField(int targetMoves) {
		absPanel.clear();
		for (GraphicsObject<?> object : graphicsObjects) {
			addToAbsPanel(object);
		}

		setFocus(true);
		
		setScore(0);
		setAveragePoints(0);
		setRemainingTargetMoves(targetMoves);
		
		gameIsOver = false;
		fieldObjects.clear();
		repaint();
	}
	
	@Override
	public void addToField(FieldObject object) {
		Map<Class<?>, Image> classToImagesMap = new HashMap<Class<?>,Image>();
		classToImagesMap.put(Hunter.class, new Image(HUNTER));
		classToImagesMap.put(Player.class, new Image(PLAYER));
		classToImagesMap.put(Obstacle.class, new Image(OBSTICLE));
		classToImagesMap.put(Target.class, new Image(TARGET));
		Image i = classToImagesMap.get(object.getClass());
		if (i == null) {
			throw new IllegalArgumentException(object + " does not have a class mapping in " + classToImagesMap);
		}
		object.setImage(i);
		if (object.getClass().equals(Hunter.class) && isToneysFace) {
			object.setImage(new Image(TONEY_IS_ANGRY));
		}
		
		object.getImage().setVisible(false);
		RootPanel.get().add(object.getImage());
		fieldObjects.add(object);
	}
	
	@Override
	public void removeFromField(FieldObject object) {
		fieldObjects.remove(object);
		absPanel.remove(object.getImage());
	}

	@Override
	public void repaint() {
		GWT.log("repaint");
		for (FieldObject object : fieldObjects) {
			if (((object.getPosition().x) < 0)
					|| ((object.getPosition().x) >= WIDTH_SPACES)
					|| ((object.getPosition().y) < 0)
					|| ((object.getPosition().y) >= HEIGHT_SPACES)) {
				// Don't draw image
			} else {
				absPanel.remove(object.getImage());
				Vector positionInPixels = new Vector();
				positionInPixels.x = object.getPosition().x * SPACE_WIDTH - ICON_OFFSET + FIELD_OFFSET_X;
				positionInPixels.y = object.getPosition().y * SPACE_HEIGHT - ICON_OFFSET + FIELD_OFFSET_Y;
				object.getImage().setVisible(true);
				absPanel.add(object.getImage(), positionInPixels.x, positionInPixels.y);
				if (gameIsOver) {
					absPanel.add(resetImage.getWidget(), resetImage.getXPosition(), resetImage.getYPosition());
				}
			}
		}
		
	}
	
	@Override
	public void gameOver() {
		gameIsOver = true;
		repaint();
	}
	
}
