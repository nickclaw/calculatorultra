package com.calculatorultra.gwtultra.common;

import static com.calculatorultra.gwtultra.common.GwtUltraUtil.HIGH_SPEED;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.INITIAL_TARGET_MOVES;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.calculateAveragePoints;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.newObsticle;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.newRandomVector;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.playerIsMoving;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.playerIsOutOfBounds;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.setOptionsControls;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.setSinglePlayerControls;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.wrapPlayers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngine;
import com.calculatorultra.gwtultra.client.ultragraphicsengine.UltraGraphicsEngineIE;
import com.calculatorultra.gwtultra.common.GwtUltraUtil.Mode;
import com.calculatorultra.gwtultra.common.exception.IncorrectNameException;
import com.calculatorultra.gwtultra.common.exception.IncorrectPasswordException;
import com.calculatorultra.gwtultra.common.exception.NotUniqueNameException;
import com.calculatorultra.gwtultra.common.exception.SignInException;
import com.calculatorultra.gwtultra.common.keystrokecontroller.KeystrokeController;
import com.calculatorultra.gwtultra.common.keystrokecontroller.KeystrokeEvent;
import com.calculatorultra.gwtultra.common.keystrokecontroller.KeystrokeHandler;
import com.calculatorultra.gwtultra.common.keystrokecontroller.OptionsKeystrokeController;
import com.calculatorultra.gwtultra.common.keystrokecontroller.PlayerKeystrokeController;
import com.calculatorultra.gwtultra.shared.HumanPlayer;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;

public abstract class UltraController extends UltraRpcCaller implements EntryPoint, KeystrokeHandler {
	protected int speed = HIGH_SPEED;
	protected int highScore = 0;
	protected UltraGraphicsEngine graphics;
	protected final List<FieldObject> obstacles = new ArrayList<FieldObject>();
	protected Player player;
	protected final List<KeystrokeController> keystrokeControllers = new ArrayList<KeystrokeController>();
	protected final PlayerKeystrokeController playerKeyController = new PlayerKeystrokeController(this);
	protected final OptionsKeystrokeController optionsKeyController = new OptionsKeystrokeController(this);
	protected Target target;
	protected HumanPlayer humanPlayer;
	protected double roundTime = 0;
	protected boolean isPaused = false;
	protected Map<String, List<HumanPlayer>> top10HighScores;
	protected boolean isWrappingMode = false;
	protected Timer timer = new Timer() {
		@Override
		public void run() {
			onTimerEvent();
			graphics.repaint();
			if (!player.direction.equals(new Vector(0, 0))) {
				roundTime += speed;
			}
		}
	};
	
	
	@Override
	public void onModuleLoad() {
		GWT.log("starting");
		createGraphicsEngine();
		setControls();
		graphics.setupGame();
		startNewRound();
		getTop10HighScores();
	}
	
	abstract protected void onTimerEvent();
	
	public void startNewRound() {
		graphics.resetField(INITIAL_TARGET_MOVES);
		obstacles.clear();
		player = new Player(newRandomVector(), this, 1);
		playerKeyController.setPlayer(player);
		newTarget();
		startTimer();
	}
	
	protected void movePlayer() {
		if (isWrappingMode) {
			wrapPlayers(player);
		}
		player.move();
		if (player.position.equals(target.position)) {
			targetHit(target, player);
		} else if (playerIsOutOfBounds(player)) {
			gameOver();
		} else {
			for (FieldObject obsticle : obstacles) {
				if (player.position.equals(obsticle.position)) {
					gameOver();
					break;
				}
			}
		}
		additionalPositionChecks();
		if (playerIsMoving(player)) {
			reduceTargetValues(target);
		}
	}
	
	protected void additionalPositionChecks() {
		// Can be overridden
	}
	
	protected void targetHit(Target target, Player player) {
		player.addToScore(target.value);
		target.remove();
		newObsticle(target.position, obstacles, this);
		newTarget();
		graphics.setScore(player.getScore());
		graphics.setAveragePoints(calculateAveragePoints(player, obstacles));
		graphics.setRemainingTargetMoves(player.getTargetMovesRemaining());
	}
	
	public abstract void moveTarget();
	
	abstract protected void gameOver();
	
	abstract protected void newTarget();
	
	public void changeMode(Mode mode) {
		switch (mode) {
		case WRAPPING:
			isWrappingMode = !isWrappingMode;
			updateHighScore();
			break;
		}
	}
	
	protected Vector getOpenSpace() {
		Boolean spaceIsUnoccupied = false;
		Vector possibleSpace = newRandomVector();
		while (spaceIsUnoccupied == false) {
			possibleSpace = newRandomVector();
			// Assumes the new position is unoccupied
			spaceIsUnoccupied = true;
			// Checks if the new position is where the player is
			if (player.position.equals(possibleSpace)) {
				spaceIsUnoccupied = false;
			} else {
				// Checks if the new position is where any of the
				// obsticles are
				for (FieldObject obstacle : obstacles) {
					if (possibleSpace.equals(obstacle.position)) {
						spaceIsUnoccupied = false;
						break;
					}
				}
			}
		}
		return possibleSpace;
	}

	@Override
	public void successfulSignIn(HumanPlayer humanPlayer) {
		this.humanPlayer = humanPlayer;
		graphics.successfulSignIn();
		updateHighScore();
	}

	public HumanPlayer getHumanPlayer() {
		return humanPlayer;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = (speed * 20 + 100);
		player.setScore(0);
		gameOver();
	}

	public Player getPlayer() {
		return player;
	}

	public UltraGraphicsEngine getGraphicsEngine() {
		return graphics;
	}

	public List<FieldObject> getObstacles() {
		return obstacles;
	}
	
	public static void reduceTargetValues(Target... targets) {
		for (Target target : targets) {
			if (target.value > 1) {
				target.value = target.value - 1;
			}
		}
	}
	protected void setControls() {
		setSinglePlayerControls(playerKeyController);
		setOptionsControls(optionsKeyController);
	}
	
	protected void createGraphicsEngine() {
		if (Canvas.isSupported()) {
			graphics = new UltraGraphicsEngine(this);
		} else {
			graphics = new UltraGraphicsEngineIE(this);
		}
	}

	protected void updateHighScore() {
		if (humanPlayer != null) {
			highScore = getModeHighScore(humanPlayer);
		} else {
			highScore = 0;
		}
		graphics.setHighScore(highScore);
	}
	
	abstract protected int getModeHighScore(HumanPlayer humanPlayer);

	@Override
	protected void setTop10HighScores(Map<String, List<HumanPlayer>> top10HighScores) {
		this.top10HighScores = top10HighScores;
	}
	
	public Map<String, List<HumanPlayer>> getHighScores() {
		return this.top10HighScores;
	}
	
	public void pause() {
		if (isPaused) {
			timer.scheduleRepeating(speed);
		} else {
			timer.cancel();
		}
		isPaused = !isPaused;
	}

	@Override
	public void onKeystroke(KeystrokeEvent event) {
		for (KeystrokeController keystrokeController : keystrokeControllers) {
			if(keystrokeController.getKeystrokes().containsKey(event.getKeystroke())) {
				keystrokeController.onKeystroke(event.getKeystroke());
			}
		}
	}

	@Override
	public void registerKeystrokeController(KeystrokeController keyController) {
		keystrokeControllers.add(keyController);
	}

	@Override
	protected void unsuccessfulSignIn(SignInException signInException) {
		if (signInException.getClass().equals(IncorrectPasswordException.class)) {
			graphics.incorrectPassword();
		} else if (signInException.getClass().equals(IncorrectNameException.class)) {
			graphics.incorrectName();
		} else if (signInException.getClass().equals(NotUniqueNameException.class)) {
			graphics.notUniqueName();
		}
	}
	
	protected void startTimer() {
		roundTime = 0;
		timer.scheduleRepeating(speed);
	}
}
