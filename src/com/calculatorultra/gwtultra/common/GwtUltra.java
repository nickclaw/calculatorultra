package com.calculatorultra.gwtultra.common;

import static com.calculatorultra.gwtultra.common.GwtUltraUtil.HEIGHT_SPACES;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.HIGH_SPEED;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.WIDTH_SPACES;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.setSinglePlayerControls;

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

public class GwtUltra extends UltraRpcCaller implements EntryPoint, KeystrokeHandler {
	private int speed = HIGH_SPEED;
	private int highScore = 0;
	private UltraGraphicsEngine graphics;
	private final ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	private final ArrayList<Vector> sequentialTargetPositions = new ArrayList<Vector>();
	private boolean isRepeatingMode = false;
	private boolean isWrappingMode = false;
	private boolean isChaseMode = false;
	private int targetNumber = 0;
	private int targetMoves = 0;
	private Player player;
	private final List<KeystrokeController> keystrokeControllers = new ArrayList<KeystrokeController>();
	private final PlayerKeystrokeController player1KeyController = new PlayerKeystrokeController(this);
	private final OptionsKeystrokeController optionsKeyController = new OptionsKeystrokeController(this);
	private Target target;
	private Hunter hunter;
	private HumanPlayer humanPlayer;
	private double roundTime = 0;
	private boolean isPaused = false;
	private Map<String, List<HumanPlayer>> top10HighScores;
	private final Timer timer = new Timer() {
		@Override
		public void run() {
			movePlayer();
			if (isChaseMode) {
				moveHunter();
			}
			graphics.repaint();
			if (!player.direction.equals(new Vector(0, 0))) {
				roundTime += speed;
			}
		}
	};

	@Override
	public void onModuleLoad() {
		GWT.log("starting");
		if (Canvas.isSupported()) {
			graphics = new UltraGraphicsEngine(this);
		} else {
			graphics = new UltraGraphicsEngineIE(this);
		}
		setSinglePlayerControls(player1KeyController);
		graphics.setupGame();
		startNewRound();
		getTop10HighScores();

	}

	public void startNewRound() {
		targetMoves = 0;
		graphics.resetField();
		obstacles.clear();
		targetNumber = 0;
		Vector startPosition;
		if (isRepeatingMode) {
			startPosition = player.getStartPosition();
		} else {
			startPosition = newRandomVector();
		}
		player = new Player(startPosition, this, 1);
		player1KeyController.setPlayer(player);
		if (isChaseMode) {
			hunter = new Hunter(new Vector(WIDTH_SPACES - startPosition.x - 1,
					HEIGHT_SPACES - startPosition.y - 1), player, target, this);
		}
		newTarget();
		roundTime = 0;
		timer.scheduleRepeating(speed);
		graphics.setScore(0);
		graphics.setAveragePoints(0);
		graphics.setRemainingTargetMoves((5 + player.score / 10) - targetMoves);
	}

	private Vector newRandomVector() {
		return new Vector((int) (Math.random() * WIDTH_SPACES),
				(int) (Math.random() * HEIGHT_SPACES));
	}

	private void movePlayer() {
		if (isWrappingMode) {
			if ((player.position.x + player.direction.x) < 0) {
				player.position.x += WIDTH_SPACES;
			} else if ((player.position.x + player.direction.x) >= WIDTH_SPACES) {
				player.position.x -= WIDTH_SPACES;
			} else if ((player.position.y + player.direction.y) < 0) {
				player.position.y += HEIGHT_SPACES;
			} else if ((player.position.y + player.direction.y) >= HEIGHT_SPACES) {
				player.position.y -= HEIGHT_SPACES;
			}
			if (player.position.equals(target.position)) {
				targetHit();
			}
		}
		player.move();
		if (isChaseMode && player.position.equals(hunter.position)) {
			gameOver();
		} else if (player.position.equals(target.position)) {
			targetHit();

			// Checks if the player is out of bounds
		} else if (((player.position.x) < 0)
				|| ((player.position.x) >= WIDTH_SPACES)
				|| ((player.position.y) < 0)
				|| ((player.position.y) >= HEIGHT_SPACES)) {
			gameOver();
		} else {
			// Checks if the player is where an obsticle is
			for (Obstacle obsticle : obstacles) {
				if (player.position.equals(obsticle.position)) {
					gameOver();
					break;
				}
			}
		}
		if (player.direction.equals(new Vector(0, 0)) == false) {
			if (target.value > 1) {
				target.value = target.value - 1;
			}
		}
	}

	private void moveHunter() {
		if ((player.direction.x != 0) || (player.direction.y != 0)) {
			hunter.move();
			if (player.position.equals(hunter.position)) {
				gameOver();
			}
		}
	}

	private void targetHit() {
		player.score = player.score + target.value;
		target.remove();
		newObsticle();
		newTarget();
		graphics.setScore(player.score);
		graphics.setAveragePoints(calculateAveragePoints());
		graphics.setRemainingTargetMoves((5 + player.score / 10) - targetMoves);
	}

	private void newObsticle() {
		obstacles.add(new Obstacle(new Vector(target.position), this));
	}

	public void gameOver() {
		player.setDirection(new Vector(0, 0));
		timer.cancel();
		if ((humanPlayer != null) && (player.score > 0)) {
			humanPlayer.addTimePlayed(roundTime);
			humanPlayer.gamePlayed();
			gamePlayed(humanPlayer.getName(), roundTime);
			if (player.score > highScore) {
				highScore = player.score;
				if ((speed == HIGH_SPEED) && !isRepeatingMode
						&& !isWrappingMode && !isChaseMode) {
					humanPlayer.setNormalHighScore(highScore);
					setNewHighScore(humanPlayer);
				} else if ((speed == HIGH_SPEED) && !isRepeatingMode
						&& isWrappingMode && !isChaseMode) {
					humanPlayer.setWrappingHighScore(highScore);
					setNewHighScore(humanPlayer);
				} else if (!isRepeatingMode && isChaseMode) {
					humanPlayer.setChaseHighScore(highScore);
					setNewHighScore(humanPlayer);
				}
				getTop10HighScores();
			}
		}
		updateHighScore();
		graphics.gameOver();
	}

	public void moveTarget() {
		if ((player.direction.equals(new Vector(0, 0)) == false)
				&& (targetMoves < (5 + player.score / 10))) {
			target.remove();
			newObsticle();
			newTarget();
			targetMoves++;
			graphics.setRemainingTargetMoves((5 + player.score / 10)
					- targetMoves);
		}
	}

	private void newTarget() {
		if (obstacles.size() < (HEIGHT_SPACES * WIDTH_SPACES - 2)) {
			targetNumber++;
			if ((sequentialTargetPositions.size() >= targetNumber)
					&& isRepeatingMode) {
				Vector nextTargetPosition = sequentialTargetPositions
						.get(targetNumber - 1);
				target = new Target(nextTargetPosition, this);
				if (isChaseMode) {
					hunter.getHunterDeadSquares().add(nextTargetPosition);
				}
			} else {
				// Creates a new target in an unoccupied space
				Boolean newTargetPositionIsUnoccupied = false;
				Vector newTargetPosition = newRandomVector();
				while (newTargetPositionIsUnoccupied == false) {
					newTargetPosition = newRandomVector();
					// Assumes the new position is unoccupied
					newTargetPositionIsUnoccupied = true;
					// Checks if the new position is where the player is
					if (player.position.equals(newTargetPosition)) {
						newTargetPositionIsUnoccupied = false;
					} else if ((hunter != null)
							&& newTargetPosition.equals(hunter.position)) {
						newTargetPositionIsUnoccupied = false;
					} else {
						// Checks if the new position is where any of the
						// obsticles are
						for (Obstacle obstacle : obstacles) {
							if (newTargetPosition.equals(obstacle.position)) {
								newTargetPositionIsUnoccupied = false;
								break;
							}
						}
					}
				}
				target = new Target(newTargetPosition, this);
				if (isChaseMode) {
					hunter.getHunterDeadSquares().add(newTargetPosition);
				}
				if (isRepeatingMode) {
					sequentialTargetPositions.add(target.position);
				}
			}
		}
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
		player.score = 0;
		gameOver();
	}

	public Player getPlayer() {
		return player;
	}

	public Hunter getHunter() {
		return hunter;
	}

	public UltraGraphicsEngine getGraphicsEngine() {
		return graphics;
	}

	public ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}

	public boolean isRepeatingMode() {
		return isRepeatingMode;
	}

	public boolean isWrappingMode() {
		return isWrappingMode;
	}

	public boolean isChaseMode() {
		return isChaseMode;
	}

	public void changeMode(Mode mode) {
		switch (mode) {
		case WRAPPING:
			isWrappingMode = !isWrappingMode;
			updateHighScore();
			break;

		case REPEATING:
			if (isRepeatingMode) {
				sequentialTargetPositions.clear();
			} else {
				for (Obstacle obsticle : obstacles) {
					sequentialTargetPositions.add(obsticle.position);
				}
				sequentialTargetPositions.add(target.position);
			}
			isRepeatingMode = !isRepeatingMode;
			updateHighScore();
			break;

		case CHASE:
			isChaseMode = !isChaseMode;
			updateHighScore();
			startNewRound();
			break;

		}
	}

	private void updateHighScore() {
		highScore = 0;
		if (humanPlayer != null) {
			if ((speed == HIGH_SPEED) && !isRepeatingMode && !isWrappingMode
					&& !isChaseMode) {
				highScore = humanPlayer.getNormalHighScore();
			} else if ((speed == HIGH_SPEED) && !isRepeatingMode
					&& isWrappingMode && !isChaseMode) {
				highScore = humanPlayer.getWrappingHighScore();
			} else if (!isRepeatingMode && isChaseMode) {
				highScore = humanPlayer.getChaseHighScore();
			}
		}
		graphics.setHighScore(highScore);
	}

	private float calculateAveragePoints() {
		// Determines the Average Point Value (APV) received of each target
		if (obstacles.size() > 0) { // Prevents Divide by 0!
			float averagePoints = (float) (player.score)/ (float) (obstacles.size());
			return averagePoints;
		} else {
			return 0;
		}
	}

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
}
