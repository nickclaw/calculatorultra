package com.calculatorultra.gwtultra.client.ultragraphicsengine;

import static com.calculatorultra.gwtultra.client.GwtUltraUtil.A;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.D;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.DOWN;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.DOWN_ARROW;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.E;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.HEIGHT_SPACES;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.LEFT;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.LEFT_ARROW;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.N;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.P;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.RIGHT;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.RIGHT_ARROW;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.S;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.SPACE_BAR;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.UP;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.UP_ARROW;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.W;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.WIDTH_SPACES;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.ZERO;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.Mode.CHASE;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.Mode.REPEATING;
import static com.calculatorultra.gwtultra.client.GwtUltraUtil.Mode.WRAPPING;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.BACKGROUND;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.FIELD_OFFSET_X;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.FIELD_OFFSET_Y;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.HUNTER;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.ICON_OFFSET;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.OBSTICLE;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.PLAYER;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.RESET_OVERLAY;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.SPACE_HEIGHT;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.SPACE_WIDTH;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.SPEED_SLIDER;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.STYLE_BLUE_LARGE;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.STYLE_BLUE_SMALL;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.STYLE_BLUE_SMALL_CURSOR;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.STYLE_GREY_BOLD;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.STYLE_GREY_LARGE_CURSOR;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.STYLE_GREY_MEDIUM;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.STYLE_GREY_SMALL_CURSOR;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.STYLE_OPACITY_40;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.TARGET;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.TONEY_IS_ANGRY;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.createImageWithClickHandler;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.createImageWithStyle;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.createLabelWithStyle;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.createLabelWithStyleAndClickHandler;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.createLabelWithStyleChangeAndClickHandler;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.setTextAndStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.calculatorultra.gwtultra.client.FieldObject;
import com.calculatorultra.gwtultra.client.GraphicsObject;
import com.calculatorultra.gwtultra.client.GwtUltra;
import com.calculatorultra.gwtultra.client.Hunter;
import com.calculatorultra.gwtultra.client.Obstacle;
import com.calculatorultra.gwtultra.client.Player;
import com.calculatorultra.gwtultra.client.Target;
import com.calculatorultra.gwtultra.client.Vector;
import com.calculatorultra.gwtultra.shared.HumanPlayer;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class UltraGraphicsEngine implements ClickHandler {
	
	private final GraphicsObject<Canvas> canvas = new GraphicsObject<Canvas>(Canvas.createIfSupported(), new Vector(0,0));
	Vector position = new Vector(10,10);
	static final int CANVAS_HEIGHT = 700;
	static final int CANVAS_WIDTH = 1000;
	Context2d context;
	
	static final int SCORES_POSITIONS_X = 725;
	static final int TOP_ROW_Y = 488;
	static final int MIDDLE_ROW_Y = 494;
	static final int BOTTOM_ROW_Y = 512;
	private final Vector scoreLabelPosition = new Vector(624, TOP_ROW_Y);
	private final GraphicsObject<Label> scoreLabel = new GraphicsObject<Label> (createLabelWithStyle("Score", STYLE_BLUE_SMALL), scoreLabelPosition);
	private final Vector highScoreLabelPosition = new Vector(624, BOTTOM_ROW_Y);
	private final GraphicsObject<Label> highScoreLabel = new GraphicsObject<Label> (createLabelWithStyle("High Score", STYLE_BLUE_SMALL), highScoreLabelPosition);
	private final Vector scoreValueLabelPosition = new Vector(712, TOP_ROW_Y);
	private final GraphicsObject<Label> scoreValueLabel = new GraphicsObject<Label> (createLabelWithStyle("0000", STYLE_BLUE_SMALL), scoreValueLabelPosition);
	private final Vector highScoreValueLabelPosition = new Vector(712, BOTTOM_ROW_Y);
	private final GraphicsObject<Label> highScoreValueLabel = new GraphicsObject<Label> (createLabelWithStyle("0000", STYLE_BLUE_SMALL), highScoreValueLabelPosition);
	private final Vector ageragePointsLabelPosition = new Vector(749, TOP_ROW_Y);
	private final GraphicsObject<Label> averagePointsLabel = new GraphicsObject<Label> (createLabelWithStyle("Average", STYLE_BLUE_SMALL), ageragePointsLabelPosition);
	private final Vector remainingTargetMovesLabelPosition = new Vector(749, BOTTOM_ROW_Y);
	private final GraphicsObject<Label> remainingTargetMovesLabel = new GraphicsObject<Label> (createLabelWithStyle("+ Moves", STYLE_BLUE_SMALL), remainingTargetMovesLabelPosition);
	private final Vector averagePointsValueLabelPosition = new Vector(837, TOP_ROW_Y);
	private final GraphicsObject<Label> averagePointsValueLabel = new GraphicsObject<Label> (createLabelWithStyle("0000", STYLE_BLUE_SMALL), averagePointsValueLabelPosition);
	private final Vector remainingTargetMovesValueLabelPosition = new Vector(837, BOTTOM_ROW_Y);
	private final GraphicsObject<Label> remainingTargetMovesValueLabel = new GraphicsObject<Label> (createLabelWithStyle("0000", STYLE_BLUE_SMALL), remainingTargetMovesValueLabelPosition);
	private final Vector wrappingLabelPosition = new Vector(581, TOP_ROW_Y);
	private final GraphicsObject<Label> wrappingLabel = new GraphicsObject<Label> (createLabelWithStyleAndClickHandler("Wrap", STYLE_BLUE_SMALL_CURSOR, this), wrappingLabelPosition);
	private final Vector chaseLabelPosition = new Vector(528, TOP_ROW_Y);
	private final GraphicsObject<Label> chaseLabel = new GraphicsObject<Label> (createLabelWithStyleAndClickHandler("Chase", STYLE_BLUE_SMALL_CURSOR, this), chaseLabelPosition);
	private final Vector repeatingLabelPosition = new Vector(473, TOP_ROW_Y);
	private final GraphicsObject<Label> repeatingLabel = new GraphicsObject<Label> (createLabelWithStyleAndClickHandler("Repeat", STYLE_BLUE_SMALL_CURSOR, this), repeatingLabelPosition);
	private final Vector speedLabelPosition = new Vector(580, 530);
	private final GraphicsObject<Label> speedLabel = new GraphicsObject<Label> (createLabelWithStyleAndClickHandler("High", STYLE_GREY_SMALL_CURSOR, this), speedLabelPosition);
	private final GraphicsObject<Image> resetImage = new GraphicsObject<Image> (createImageWithClickHandler(RESET_OVERLAY, this), new Vector(FIELD_OFFSET_X, FIELD_OFFSET_Y));
	private final GraphicsObject<Image> background = new GraphicsObject<Image> (createImageWithStyle(BACKGROUND, STYLE_OPACITY_40), new Vector(0, 0));
	private final GraphicsObject<Image> speedSlider = new GraphicsObject<Image> (createImageWithStyle(SPEED_SLIDER, STYLE_OPACITY_40), new Vector(580, 512));
	private GraphicsObject<Image> hitCounter;
	private final Vector signInLabelPosition = new Vector(82, MIDDLE_ROW_Y);
	private final GraphicsObject<Label> signInLabel = new GraphicsObject<Label> (createLabelWithStyleChangeAndClickHandler("Sign In", STYLE_BLUE_LARGE, STYLE_GREY_LARGE_CURSOR, this), signInLabelPosition);
	private final Vector instructionsLabelPosition = new Vector(298, MIDDLE_ROW_Y);
	private final GraphicsObject<Label> instructionsLabel = new GraphicsObject<Label> (createLabelWithStyleChangeAndClickHandler("Instructions", STYLE_BLUE_LARGE, STYLE_GREY_LARGE_CURSOR, this), instructionsLabelPosition);
	private final Vector leaderboardLabelPosition = new Vector(190, MIDDLE_ROW_Y);
	private final GraphicsObject<Label> leaderboardLabel = new GraphicsObject<Label> (createLabelWithStyleChangeAndClickHandler("Stats", STYLE_BLUE_LARGE, STYLE_GREY_LARGE_CURSOR, this), leaderboardLabelPosition);
	private final FocusPanel focusPanel = new FocusPanel();
	private final AbsolutePanel absPanel = new AbsolutePanel();
	private final GwtUltra gwtUltra;
	private final ArrayList<GraphicsObject<?>> graphicsObjects = new ArrayList<GraphicsObject<?>>();
	private final ArrayList<FieldObject> fieldObjects = new ArrayList<FieldObject>();
	private final Vector signInDialogBoxPosition = new Vector(67, 323);
	private final SignInDialogBox signInDialogBox = new SignInDialogBox(this, signInDialogBoxPosition);
	private final Vector instructionsDialogBoxPosition = new Vector(70, 70);
	private final InstructionsDialogBox instructionsDialogBox = new InstructionsDialogBox(this, instructionsDialogBoxPosition);
	private final Vector leaderboardDialogBoxPosition = new Vector(70, 70);
	private LeaderboardDialogBox leaderboardDialogBox;
	private boolean isToneysFace = false;
	private MouseMoveHandler mouseMoveHandler;
	private HandlerRegistration mouseMoveHandlerRegistration;
	private HandlerRegistration mouseUpHandlerRegistration;
	public UltraGraphicsEngine(GwtUltra gwtUltra) {
		this.gwtUltra = gwtUltra;
	}

	public GwtUltra getGwtUltra() {
		return gwtUltra;
	}

	public void setupGame() {
		
        canvas.getWidget().setStyleName("mainCanvas");
        canvas.getWidget().setWidth(CANVAS_WIDTH + "px");
        canvas.getWidget().setCoordinateSpaceWidth(CANVAS_WIDTH);
        canvas.getWidget().setHeight(CANVAS_HEIGHT + "px");
        canvas.getWidget().setCoordinateSpaceHeight(CANVAS_HEIGHT);        
		context = canvas.getWidget().getContext2d();
		
		
		RootPanel rootPanel = RootPanel.get();
		focusPanel.setStyleName("ultraGraphicsEngine");
		focusPanel.setFocus(true);
		focusPanel.addClickHandler(this);
		setupSpeedSlider();
		focusPanel.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				event.preventDefault();
				Integer key = event.getNativeKeyCode();
				GWT.log(key.toString());
				switch (key) {
				case W:
				case UP_ARROW:
					gwtUltra.getPlayer().setDirection(UP);
					break;
				case A:
				case LEFT_ARROW:
					gwtUltra.getPlayer().setDirection(LEFT);
					break;
				case S:
				case DOWN_ARROW:
					gwtUltra.getPlayer().setDirection(DOWN);
					break;
				case D:
				case RIGHT_ARROW:
					gwtUltra.getPlayer().setDirection(RIGHT);
					break;
				case E:
				case ZERO:
					gwtUltra.moveTarget();
					break;
				case N:
				case SPACE_BAR:
					gwtUltra.startNewRound();
					break;
				case P:
					gwtUltra.pause();
					break;
				}
			}
		});
		rootPanel.add(focusPanel);
		
		//Add all the GraphicsObjects to the ArrayList
		graphicsObjects.add(canvas);
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
		//graphicsObjects.add(speedLabel);
		
		focusPanel.add(absPanel);
		absPanel.setSize("939px", "1000px");

		setHighScore(0);
		
		Window.enableScrolling(false);
		Window.setTitle("Ultra");

	}

	private void setupSpeedSlider() {
		mouseMoveHandler = new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				if (event.getX() >= 471 && event.getX() <= 589) {
					int space = ((event.getX()-480) / 20);
					speedSlider.setXPosition(480 + 20 * space);
					gwtUltra.setSpeed(10 - 2 * space);
					speedLabel.getWidget().setText("Speed " + gwtUltra.getSpeed());
				} else {
					mouseMoveHandlerRegistration.removeHandler();
				}
			}
		};
		focusPanel.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				if (speedSlider.getXPosition() <= event.getX() 
						&& (speedSlider.getXPosition() + 20) >= event.getX()
						&& speedSlider.getYPosition() <= event.getY()
						&& (speedSlider.getYPosition() + 20) >= event.getY()) {
					mouseMoveHandlerRegistration = focusPanel.addMouseMoveHandler(mouseMoveHandler);
				}
			}
		});
		
		mouseUpHandlerRegistration = focusPanel.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				mouseMoveHandlerRegistration.removeHandler();
				gwtUltra.gameOver();
			}
		});
		
	}

	public void resetField() {
		absPanel.clear();
		for (GraphicsObject<?> object : graphicsObjects) {
			addToAbsPanel(object);
		}
		
		setFocus(true);
		
		fieldObjects.clear();
		canvas.getWidget().getContext2d().drawImage(((ImageElement) (background.getWidget()).getElement().cast()), background.getXPosition(), background.getYPosition());
		repaint();
		
		
	}

	private void addToAbsPanel(GraphicsObject<?> object) {
		absPanel.insert(object.getWidget(), object.getXPosition(), object.getYPosition(), graphicsObjects.indexOf(object));
	}
	
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
	
	public void removeFromField(FieldObject object) {
		fieldObjects.remove(object);
	}

	public void repaint() {
		//GWT.log("paint");
		Context2d context = canvas.getWidget().getContext2d();
		
		context.setGlobalAlpha(gwtUltra.getSpeed() * .0033);
		context.drawImage(((ImageElement) (background.getWidget()).getElement().cast()), background.getXPosition(), background.getYPosition());
		context.setGlobalAlpha(1);
		context.drawImage(((ImageElement) (speedSlider.getWidget()).getElement().cast()), speedSlider.getXPosition(), speedSlider.getYPosition());
		
		for(FieldObject object : fieldObjects) {
			if (((object.getPosition().x) < 0)
					|| ((object.getPosition().x) >= WIDTH_SPACES)
					|| ((object.getPosition().y) < 0)
					|| ((object.getPosition().y) >= HEIGHT_SPACES)) {
				// Don't draw image
			} else {
				Vector positionInPixels = new Vector();
				positionInPixels.x = object.getPosition().x * SPACE_WIDTH - ICON_OFFSET + FIELD_OFFSET_X;
				positionInPixels.y = object.getPosition().y * SPACE_HEIGHT - ICON_OFFSET + FIELD_OFFSET_Y;
				context.drawImage(((ImageElement) object.getImage().getElement().cast()), positionInPixels.x, positionInPixels.y);
			}
		}
		
	}

	public void setScore(int score) {
		String formattedText = NumberFormat.getFormat("0000").format(score);
		scoreValueLabel.getWidget().setText(formattedText);

	}

	public void setHighScore(int highScore) {
		GWT.log("new high score");
		String formattedText = NumberFormat.getFormat("0000").format(highScore);
		highScoreValueLabel.getWidget().setText(formattedText);
	}
	
	public void setRemainingTargetMoves(int targetMoves) {
		String formattedText = NumberFormat.getFormat("0000").format(targetMoves);
		remainingTargetMovesValueLabel.getWidget().setText(formattedText);
		
	}

	public void setAveragePoints(float averagePoints) {
		String formattedAverageScore = NumberFormat.getFormat("00.0").format(
				averagePoints);
		averagePointsValueLabel.getWidget().setText(formattedAverageScore);
	}
	
	public void gameOver() {
		absPanel.add(resetImage.getWidget(), resetImage.getXPosition(), resetImage.getYPosition());
	}
	
	public void notUniqueName() {
		signInDialogBox.notUniqueName();
	}
	
	public void incorrectName() {
		signInDialogBox.invalidName();
	}
	
	public void incorrectPassword() {
		signInDialogBox.invalidPassword();
	}
	
	public void successfulSignIn() {
		signInDialogBox.hide();
		gwtUltra.startNewRound();
	}
	
	public void setFocus(boolean setFocus) {
		focusPanel.setFocus(setFocus);
	}

	@Override
	public void onClick(ClickEvent event) {
		GWT.log("click");
		if (event.getSource() == resetImage.getWidget()) {
			gwtUltra.startNewRound();
			
		} else if (event.getSource() == signInLabel.getWidget()) {
			signInDialogBox.show();	
		
		} else if (event.getSource() == instructionsLabel.getWidget()) {
			instructionsDialogBox.show();	
			
		} else if (event.getSource() == leaderboardLabel.getWidget()) {
			leaderboardDialogBox = new LeaderboardDialogBox(this, leaderboardDialogBoxPosition, gwtUltra.getTop10HighScores(), gwtUltra.getHumanPlayer());
			leaderboardDialogBox.show();
			
		} else if (event.getSource() == wrappingLabel.getWidget()) {
			gwtUltra.changeMode(WRAPPING);
			if (wrappingLabel.getWidget().getStyleName().equals(STYLE_BLUE_SMALL_CURSOR)) {
				wrappingLabel.getWidget().setStyleName(STYLE_GREY_SMALL_CURSOR);
			} else {
				wrappingLabel.getWidget().setStyleName(STYLE_BLUE_SMALL_CURSOR);
			}
			
		} else if (event.getSource() == chaseLabel.getWidget()) {
			gwtUltra.changeMode(CHASE);
			if (chaseLabel.getWidget().getStyleName().equals(STYLE_BLUE_SMALL_CURSOR)) {
				chaseLabel.getWidget().setStyleName(STYLE_GREY_SMALL_CURSOR);
			} else {
				chaseLabel.getWidget().setStyleName(STYLE_BLUE_SMALL_CURSOR);
			}
			
		} else if (event.getSource() == repeatingLabel.getWidget()) {
			gwtUltra.changeMode(REPEATING);
			if (repeatingLabel.getWidget().getStyleName().equals(STYLE_BLUE_SMALL_CURSOR)) {
				repeatingLabel.getWidget().setStyleName(STYLE_GREY_SMALL_CURSOR);
			} else {
				repeatingLabel.getWidget().setStyleName(STYLE_BLUE_SMALL_CURSOR);
			}
		} else if (event.getSource() == focusPanel) {
			if ((event.getX() >= 708) && (event.getX() <= 730)
					&& (event.getY() >= 540) && (event.getY() <= 555)
					&& gwtUltra.isChaseMode()) {
				isToneysFace = !isToneysFace;
				gwtUltra.startNewRound();
			}
		}
		focusPanel.setFocus(true);

	}
	
	public static class SignInDialogBox extends UltraGraphicsPopupPanel { 

		private PasswordTextBox passwordTextBox;
		private TextBox nameTextBox;
		
		public SignInDialogBox(final UltraGraphicsEngine ultraGraphicsEngine, final Vector position) {
			super("Sign In", ultraGraphicsEngine, position);
		}
		
		

		public void notUniqueName() {
			flexTable.setText(4, 1, "Name Taken");
			passwordTextBox.setText("");
			nameTextBox.selectAll();
			
		}

		public void invalidName() {
			flexTable.setText(4, 1, "Invalid Name");
			passwordTextBox.setText("");
			nameTextBox.selectAll();
		}
		
		public void invalidPassword() {
			flexTable.setText(4, 1, "Invalid Password");
			passwordTextBox.selectAll();
		}

		@Override
		void setUpFlexTable() {
			
			passwordTextBox = new PasswordTextBox();
			nameTextBox = new TextBox();
			
			final Button newHumanPlayerButton = new Button("New Player");
			newHumanPlayerButton.setStyleName("ultraGraphicsEngine");
			newHumanPlayerButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(flexTable.isCellPresent(4, 1)) flexTable.clearCell(4, 1);
					if (!nameTextBox.getText().matches("[\\w]{1,10}")) invalidName();
					else if (!passwordTextBox.getText().matches("[\\w]{1,10}")) invalidPassword();
					ultraGraphicsEngine.getGwtUltra().registerPlayer(nameTextBox.getText(), passwordTextBox.getText());
					
				}
			});
			
			final Button signInButton = new Button("Sign In");
			signInButton.setStyleName("ultraGraphicsEngine");
			signInButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(flexTable.isCellPresent(4, 1)) flexTable.clearCell(4, 1);
					if (!nameTextBox.getText().matches("[\\w]{1,10}")) invalidName();
					else if (!passwordTextBox.getText().matches("[\\w]{1,10}")) invalidPassword();
					ultraGraphicsEngine.getGwtUltra().signIn(nameTextBox.getText(), passwordTextBox.getText());
				}
			});
			
			passwordTextBox.addKeyDownHandler( new KeyDownHandler() {

				@Override
				public void onKeyDown(KeyDownEvent event) {
					if (event.getNativeKeyCode() == 13) {
						signInButton.click();
					}
				}
			});
			
			HorizontalPanel buttonsPanel = new HorizontalPanel();
			buttonsPanel.add(newHumanPlayerButton);
			buttonsPanel.add(signInButton);
			buttonsPanel.setSpacing(5);
			buttonsPanel.getElement().getStyle().setProperty("marginLeft", "auto"); 
			buttonsPanel.getElement().getStyle().setProperty("marginRight", "0");
			
			setTextAndStyle(flexTable, 1, 0, "Name:", STYLE_GREY_MEDIUM);
			setTextAndStyle(flexTable, 2, 0, "Password:", STYLE_GREY_MEDIUM);
			flexTable.setWidget(1, 1, nameTextBox);
			flexTable.setWidget(2, 1, passwordTextBox);
			flexTable.getFlexCellFormatter().setColSpan(3, 0, 2);
			flexTable.setWidget(3, 0, buttonsPanel);
		}



		@Override
		int numberOfColumns() {
			return 2;
		}
	}
	
	public static class LeaderboardDialogBox extends UltraGraphicsPopupPanel {
		
		public LeaderboardDialogBox(final UltraGraphicsEngine ultraGraphicsEngine, final Vector position, final Map<String, List<HumanPlayer>> top10HighScores, final HumanPlayer humanPlayer) {
			super("Leaderboards", ultraGraphicsEngine, position);
		}

		@Override
		void setUpFlexTable() {
			
			Map<String, List<HumanPlayer>> top10HighScores = ultraGraphicsEngine.getGwtUltra().getTop10HighScores();
			HumanPlayer humanPlayer = ultraGraphicsEngine.getGwtUltra().getHumanPlayer();
			
			if (humanPlayer == null) {
				setTextAndStyle(flexTable, 1, 0, "Sign In to view Leaderboards", STYLE_GREY_BOLD);
			} else if (top10HighScores != null) {
				int NORMAL_COLUMN_NAME = 0;
				int NORMAL_COLUMN_SCORE = 1;
				int WRAPPING_COLUMN_NAME = 2;
				int WRAPPING_COLUMN_SCORE = 3;
				int CHASE_COLUMN_NAME = 4;
				int CHASE_COLUMN_SCORE = 5;
				
				List<HumanPlayer> normalHighScores = top10HighScores.get("normal");
				List<HumanPlayer> wrappingHighScores = top10HighScores.get("wrapping");
				List<HumanPlayer> chaseHighScores = top10HighScores.get("chase");
				
				setTextAndStyle(flexTable, 1, NORMAL_COLUMN_NAME, "Normal", STYLE_GREY_BOLD);
				for (int i = 0; normalHighScores.size() > i; i++) {
					setTextAndStyle(flexTable, i + 2, NORMAL_COLUMN_NAME, normalHighScores.get(i).getName(), STYLE_GREY_MEDIUM);
					setTextAndStyle(flexTable, i + 2, NORMAL_COLUMN_SCORE, normalHighScores.get(i).getNormalHighScoreString(), STYLE_GREY_MEDIUM);
				}
				
				setTextAndStyle(flexTable, 1, WRAPPING_COLUMN_NAME, "Wrapping", STYLE_GREY_BOLD);
				for (int i = 0; wrappingHighScores.size() > i; i++) {
					setTextAndStyle(flexTable, i + 2, WRAPPING_COLUMN_NAME, wrappingHighScores.get(i).getName(), STYLE_GREY_MEDIUM);
					setTextAndStyle(flexTable, i + 2, WRAPPING_COLUMN_SCORE, wrappingHighScores.get(i).getWrappingHighScoreString(), STYLE_GREY_MEDIUM);
				}
				
				setTextAndStyle(flexTable, 1, CHASE_COLUMN_NAME, "Chase", STYLE_GREY_BOLD);
				for (int i = 0; chaseHighScores.size() > i; i++) {
					setTextAndStyle(flexTable, i + 2, CHASE_COLUMN_NAME, chaseHighScores.get(i).getName(), STYLE_GREY_MEDIUM);
					setTextAndStyle(flexTable, i + 2, CHASE_COLUMN_SCORE, chaseHighScores.get(i).getChaseHighScoreString(), STYLE_GREY_MEDIUM);
				}
				
				setTextAndStyle(flexTable, 12, 0, "Name", STYLE_GREY_BOLD);
				setTextAndStyle(flexTable, 13, 0, humanPlayer.getName(), STYLE_GREY_MEDIUM);
				setTextAndStyle(flexTable, 12, 1, "Normal", STYLE_GREY_BOLD);
				setTextAndStyle(flexTable, 13, 1, humanPlayer.getNormalHighScoreString(), STYLE_GREY_MEDIUM);
				setTextAndStyle(flexTable, 12, 2, "Wrapping", STYLE_GREY_BOLD);
				setTextAndStyle(flexTable, 13, 2, humanPlayer.getWrappingHighScoreString(), STYLE_GREY_MEDIUM);
				setTextAndStyle(flexTable, 12, 3, "Chase", STYLE_GREY_BOLD);
				setTextAndStyle(flexTable, 13, 3, humanPlayer.getChaseHighScoreString(), STYLE_GREY_MEDIUM);
			}
		}

		@Override
		int numberOfColumns() {
			return 5;
		}
	}
	
	public static class InstructionsDialogBox extends UltraGraphicsPopupPanel {
		
		public InstructionsDialogBox(final UltraGraphicsEngine ultraGraphicsEngine, final Vector position) {
			super("Instructions", ultraGraphicsEngine, position);
		}

		@Override
		void setUpFlexTable() {
			setTextAndStyle(flexTable, 1, 0, "Use the arrow keys to move your player around the screen hitting the yellow targets. The faster you get to the target the more points it is worth, but don't hit the white circles or hit the edge. If High Speed is to fast for you, try a slower speed by clicking the Low or Med buttons. Wrapping mode will allow you to teleport across the screen when you hit an edge. Repeating mode will always respawn the targets in the same places. Chase mode is -- well you can find out for yourself! Sign in to save your High Scores and view the leaderboards.", STYLE_GREY_MEDIUM);
		}

		@Override
		int numberOfColumns() {
			return 5;
		}
	}
}
