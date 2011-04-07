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
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.STYLE_BLUE_LARGE;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.STYLE_BLUE_SMALL;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.STYLE_GREY_BOLD;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.STYLE_GREY_LARGE_CURSOR;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.STYLE_GREY_MEDIUM;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.TARGET;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.TONEY_IS_ANGRY;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.createImageWithClickHandler;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.createLabelWithStyle;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.createLabelWithStyleChangeAndClickHandler;
import static com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineUtil.setTextAndStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.calculatorultra.gwtultra.client.FieldObject;
import com.calculatorultra.gwtultra.client.GraphicsObject;
import com.calculatorultra.gwtultra.client.GwtUltra;
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
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class UltraGraphicsEngine implements ClickHandler {
	
	private final Canvas canvas = Canvas.createIfSupported();
	Vector position = new Vector(10,10);
	static final int canvasHeight = 700;
	static final int canvasWidth = 1000;
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
	private final GraphicsObject<Image> resetImage = new GraphicsObject<Image> (createImageWithClickHandler(RESET_OVERLAY, this), new Vector(FIELD_OFFSET_X, FIELD_OFFSET_Y));
	private final GraphicsObject<Image> background = new GraphicsObject<Image> (new Image(BACKGROUND), new Vector(0, 0));
	private GraphicsObject<Image> hitCounter;
	private final Vector signInLabelPosition = new Vector(82, MIDDLE_ROW_Y);
	private final GraphicsObject<Label> signInLabel = new GraphicsObject<Label> (createLabelWithStyleChangeAndClickHandler("Sign In", STYLE_BLUE_LARGE, STYLE_GREY_LARGE_CURSOR, this), signInLabelPosition);
	private final Vector modeLabelPosition = new Vector(398, MIDDLE_ROW_Y);
	private final GraphicsObject<Label> modeLabel = new GraphicsObject<Label> (createLabelWithStyleChangeAndClickHandler("Mode", STYLE_BLUE_LARGE, STYLE_GREY_LARGE_CURSOR, this), modeLabelPosition);
	private final FocusPanel focusPanel = new FocusPanel();
	private final AbsolutePanel absPanel = new AbsolutePanel();
	private final  GwtUltra gwtUltra;
	private final ArrayList<GraphicsObject> graphicsObjects = new ArrayList<GraphicsObject>();
	private final ArrayList<FieldObject> fieldObjects = new ArrayList<FieldObject>();
	private final Vector signInDialogBoxPosition = new Vector(70, 323);
	private final SignInDialogBox signInDialogBox = new SignInDialogBox(this, signInDialogBoxPosition);
	private final Vector modeDialogBoxPosition = new Vector(370, 333);
	private final ModeDialogBox modeDialogBox = new ModeDialogBox(this, modeDialogBoxPosition);
	private final GraphicsObject<FlexTable> leaderboardsTable = new GraphicsObject<FlexTable> (new FlexTable(), 850, 40);
	private boolean isToneysFace = false;
	//private final Label cursor = new Label("A");

	public UltraGraphicsEngine(GwtUltra gwtUltra) {
		this.gwtUltra = gwtUltra;
	}

	public GwtUltra getGwtUltra() {
		return gwtUltra;
	}

	public void setupGame() {
		
        canvas.setStyleName("mainCanvas");
        canvas.setWidth(canvasWidth + "px");
        canvas.setCoordinateSpaceWidth(canvasWidth);
        canvas.setHeight(canvasHeight + "px");
        canvas.setCoordinateSpaceHeight(canvasHeight);        
		context = canvas.getContext2d();
		
		
		RootPanel rootPanel = RootPanel.get();
		focusPanel.setStyleName("ultraGraphicsEngine");
		focusPanel.setFocus(true);
		focusPanel.addClickHandler(this);
		/**
		focusPanel.addMouseMoveHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				if (event.getX() > 880 && event.getX() < 1000
						&& event.getY() > 480 && event.getY() < 600) {
					field.remove(cursor);
					field.insert(cursor, event.getX(), event.getY(), graphicsObjects.size());
				}
			}
		});
		**/
		focusPanel.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				Integer key = event.getNativeKeyCode();
				GWT.log(key.toString());
				if ((key == W) || (key == UP_ARROW)) {
					event.preventDefault();
					gwtUltra.getPlayer().setDirection(UP);
				} else if ((key == A) || (key == LEFT_ARROW)) {
					event.preventDefault();
					gwtUltra.getPlayer().setDirection(LEFT);
				} else if ((key == S) || (key == DOWN_ARROW)) {
					event.preventDefault();
					gwtUltra.getPlayer().setDirection(DOWN);
				} else if ((key == D) || (key == RIGHT_ARROW)) {
					event.preventDefault();
					gwtUltra.getPlayer().setDirection(RIGHT);
				} else if ((key == E) || (key == ZERO)) {
					gwtUltra.moveTarget();
				} else if ((key == N) || (key == SPACE_BAR)) {
					gwtUltra.startNewRound();
				}
			}
		});
		rootPanel.add(focusPanel);
		
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
		graphicsObjects.add(modeLabel);
		
		focusPanel.add(absPanel);
		absPanel.setSize("939px", "1000px");

		setHighScore(0);
		
		Window.enableScrolling(false);
		Window.setTitle("Ultra");

	}

	public void resetField() {
		absPanel.clear();
		for (GraphicsObject object : graphicsObjects) {
			addToAbsPanel(object);
		}
		
		setFocus(true);
		
		fieldObjects.clear();
		
		repaint();
		
		
	}

	private void addToAbsPanel(GraphicsObject object) {
		absPanel.insert(object.getWidget(), object.getXPosition(), object.getYPosition(), graphicsObjects.indexOf(object));
	}
	
	public void addToField(FieldObject object) {
		GWT.log(object.getClass().getName());
		final String filePath = "com.calculatorultra.gwtultra.client.";
		if (object.getClass().getName().equals(filePath + "Hunter")) {
			if (isToneysFace) {
				object.setImage(new Image(TONEY_IS_ANGRY));
			} else object.setImage(new Image(HUNTER));
		} else if (object.getClass().getName().equals(filePath + "Player")) {
			object.setImage(new Image(PLAYER));
		} else if (object.getClass().getName().equals(filePath + "Obstacle")) {
			object.setImage(new Image(OBSTICLE));
		} else if (object.getClass().getName().equals(filePath + "Target")) {
			object.setImage(new Image(TARGET));
		}
		object.getImage().setVisible(false);
		RootPanel.get().add(object.getImage());
		fieldObjects.add(object);
	}
	
	public void removeFromField(FieldObject object) {
		fieldObjects.remove(object);
	}

	public void repaint() {
		GWT.log("paint");
		Context2d context = canvas.getContext2d();
		
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
				GWT.log("drawing");
				//object.getImage().setVisible(true);
				//absPanel.insert(object.getImage(), positionInPixels.x, positionInPixels.y, graphicsObjects.size());
			}
		}
		
	}

	public void setScore(int score) {
		String formattedText = NumberFormat.getFormat("0000").format(score);
		((Label) scoreValueLabel.getWidget()).setText(formattedText);

	}

	public void setHighScore(int highScore) {
		GWT.log("new high score");
		String formattedText = NumberFormat.getFormat("0000").format(highScore);
		((Label) highScoreValueLabel.getWidget()).setText(formattedText);
	}
	
	public void setRemainingTargetMoves(int targetMoves) {
		GWT.log(new Integer(targetMoves).toString());
		String formattedText = NumberFormat.getFormat("0000").format(targetMoves);
		((Label) remainingTargetMovesValueLabel.getWidget()).setText(formattedText);
		
	}

	public void setAveragePoints(float averagePoints) {
		String formattedAverageScore = NumberFormat.getFormat("00.0").format(
				averagePoints);
		((Label) averagePointsValueLabel.getWidget()).setText(formattedAverageScore);
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
	
	public void updateLeaderboards(List<HumanPlayer> normalHighScores,
			List<HumanPlayer> wrappingHighScores,
			List<HumanPlayer> chaseHighScores) {
		FlexTable leaderboardsFlexTable = ((FlexTable) leaderboardsTable.getWidget());
		leaderboardsFlexTable.clear();
		leaderboardsFlexTable.setStyleName("blueText");
		leaderboardsFlexTable.getRowFormatter().setStyleName(0, "boldBlueText");
		int NORMAL_COLUMN_NAME = 0;
		int NORMAL_COLUMN_SCORE = 1;
		int WRAPPING_COLUMN_NAME = 2;
		int WRAPPING_COLUMN_SCORE = 3;
		int CHASE_COLUMN_NAME = 4;
		int CHASE_COLUMN_SCORE = 5;

		//leaderboardsFlexTable.getFlexCellFormatter().setColSpan(0, NORMAL_COLUMN_NAME, 2);
		leaderboardsFlexTable.setText(0, NORMAL_COLUMN_NAME, "Normal");
		for (int i = 0; normalHighScores.size() > i; i++) {
			leaderboardsFlexTable.setText(i + 1, NORMAL_COLUMN_NAME, normalHighScores.get(i).getName());
			leaderboardsFlexTable.setText(i + 1, NORMAL_COLUMN_SCORE, normalHighScores.get(i).getNormalHighScoreString());
		}
		
		//leaderboardsFlexTable.getFlexCellFormatter().setColSpan(0, WRAPPING_COLUMN_NAME, 2);
		leaderboardsFlexTable.setText(0, WRAPPING_COLUMN_NAME, "Wrapping");
		for (int i = 0; wrappingHighScores.size() > i; i++) {
			leaderboardsFlexTable.setText(i + 1, WRAPPING_COLUMN_NAME, wrappingHighScores.get(i).getName());
			leaderboardsFlexTable.setText(i + 1, WRAPPING_COLUMN_SCORE, wrappingHighScores.get(i).getWrappingHighScoreString());
		}
		
		//leaderboardsFlexTable.getFlexCellFormatter().setColSpan(0, CHASE_COLUMN_NAME, 2);
		leaderboardsFlexTable.setText(0, CHASE_COLUMN_NAME, "Chase");
		for (int i = 0; chaseHighScores.size() > i; i++) {
			leaderboardsFlexTable.setText(i + 1, CHASE_COLUMN_NAME, chaseHighScores.get(i).getName());
			leaderboardsFlexTable.setText(i + 1, CHASE_COLUMN_SCORE, chaseHighScores.get(i).getChaseHighScoreString());
		}
		
		
	}

	/**
	public void movePlayerAnnimated() {
		GWT.log("move player");
		Timer timer = new Timer() {
			@Override
			public void run() {
				GWT.log("run");
				if (annimationStepCounter <= ANNIMATION_STEPS) {
					annimationStepCounter++;
					field.remove(gwtUltra.player.icon);
					field.add(gwtUltra.player.icon, gwtUltra.player.position.x
							* SPACE_WIDTH + gwtUltra.player.direction.x
							* PX_PER_STEP_HORIZONTAL - ICON_OFFSET,
							gwtUltra.player.position.x * SPACE_WIDTH
									+ gwtUltra.player.direction.x
									* PX_PER_STEP_HORIZONTAL - ICON_OFFSET);
				} else {
					this.cancel();
					gwtUltra.player.position.add(gwtUltra.player.direction);
				}

			}
		};
		timer.scheduleRepeating(gwtUltra.speed / ANNIMATION_STEPS);
	}
	**/
	
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
			
		} else if (event.getSource() == modeLabel.getWidget()) {
			modeDialogBox.show();
		
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
		
		Map<String, List<HumanPlayer>> top10HighScores;
		HumanPlayer humanPlayer;
		
		public LeaderboardDialogBox(final UltraGraphicsEngine ultraGraphicsEngine, final Vector position, final Map<String, List<HumanPlayer>> top10HighScores, final HumanPlayer humanPlayer) {
			super("Leaderboards", ultraGraphicsEngine, position);
			this.top10HighScores = top10HighScores;
			this.humanPlayer = humanPlayer;
		}

		@Override
		void setUpFlexTable() {
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
			
			setTextAndStyle(flexTable, 12, 0, "Name", STYLE_GREY_MEDIUM);
			setTextAndStyle(flexTable, 13, 0, humanPlayer.getName(), STYLE_GREY_MEDIUM);
			setTextAndStyle(flexTable, 12, 1, "Normal", STYLE_GREY_MEDIUM);
			setTextAndStyle(flexTable, 13, 1, humanPlayer.getNormalHighScoreString(), STYLE_GREY_MEDIUM);
			setTextAndStyle(flexTable, 12, 2, "Wrapping", STYLE_GREY_MEDIUM);
			setTextAndStyle(flexTable, 13, 2, humanPlayer.getWrappingHighScoreString(), STYLE_GREY_MEDIUM);
			setTextAndStyle(flexTable, 12, 3, "Chase", STYLE_GREY_MEDIUM);
			setTextAndStyle(flexTable, 13, 3, humanPlayer.getChaseHighScoreString(), STYLE_GREY_MEDIUM);
			
		}

		@Override
		int numberOfColumns() {
			// TODO Auto-generated method stub
			return 0;
		}
	}
	
	public static class ModeDialogBox extends UltraGraphicsPopupPanel {
		
		public ModeDialogBox(final UltraGraphicsEngine ultraGraphicsEngine, final Vector position) {
			super("Mode", ultraGraphicsEngine, position);
		}

		@Override
		void setUpFlexTable() {
			Button wrappingModeButton = new Button("W");
			wrappingModeButton.setStyleName("ultraGraphicsEngine");
			wrappingModeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ultraGraphicsEngine.getGwtUltra().changeMode(WRAPPING);
				}
			});
			
			Button chaseModeButton = new Button("C");
			chaseModeButton.setStyleName("ultraGraphicsEngine");
			chaseModeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ultraGraphicsEngine.getGwtUltra().changeMode(CHASE);
					if (!ultraGraphicsEngine.getGwtUltra().isWrappingMode()) {
						ultraGraphicsEngine.getGwtUltra().changeMode(WRAPPING);
					}
				}
			});
			
			Button repeatingModeButton = new Button("R");
			repeatingModeButton.setStyleName("ultraGraphicsEngine");
			repeatingModeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ultraGraphicsEngine.getGwtUltra().changeMode(REPEATING);
				}
			});
			
			setTextAndStyle(flexTable, 1, 0, "Wrapping:", STYLE_GREY_MEDIUM);
			setTextAndStyle(flexTable, 2, 0, "Chase:", STYLE_GREY_MEDIUM);
			setTextAndStyle(flexTable, 3, 0, "Repeating:", STYLE_GREY_MEDIUM);
			flexTable.setWidget(1, 1, wrappingModeButton);
			flexTable.setWidget(2, 1, chaseModeButton);
			flexTable.setWidget(3, 1, repeatingModeButton);
			
		}

		@Override
		int numberOfColumns() {
			return 2;
		}
	}
}
