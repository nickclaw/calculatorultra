package com.calculatorultra.gwtultra.common;

import static com.calculatorultra.gwtultra.common.GwtUltraUtil.HEIGHT_SPACES;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.HIGH_SPEED;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.INITIAL_TARGET_MOVES;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.WIDTH_SPACES;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.newObsticle;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.newRandomVector;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.playerIsMoving;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.stopPlayers;

import java.util.ArrayList;

import com.calculatorultra.gwtultra.shared.HumanPlayer;

public class GwtUltra extends UltraController {
	private final ArrayList<Vector> sequentialTargetPositions = new ArrayList<Vector>();
	private final boolean isChaseMode = false;
	private Hunter hunter;
	
	@Override
	public void startNewRound() {
		graphics.resetField(INITIAL_TARGET_MOVES);
		obstacles.clear();
		player = new Player(newRandomVector(), this, 1);
		playerKeyController.setPlayer(player);
		if (isChaseMode) {
			hunter = new Hunter(new Vector(WIDTH_SPACES - player.startPosition.x - 1,
					HEIGHT_SPACES - player.startPosition.y - 1), player, target, this);
			obstacles.add(hunter);
		}
		newTarget();
		startTimer();
	}

	@Override
	protected void additionalPositionChecks() {
		if (isChaseMode && player.position.equals(hunter.position)) {
			gameOver();
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

	@Override
	public void gameOver() {
		stopPlayers(player);
		timer.cancel();
		if ((humanPlayer != null) && (player.getScore() > 0)) {
			humanPlayer.addTimePlayed(roundTime);
			humanPlayer.gamePlayed();
			gamePlayed(humanPlayer.getName(), roundTime);
			if (player.getScore() > highScore) {
				highScore = player.getScore();
				if ((speed == HIGH_SPEED)
						&& !isWrappingMode && !isChaseMode) {
					humanPlayer.setNormalHighScore(highScore);
					setNewHighScore(humanPlayer);
				} else if ((speed == HIGH_SPEED)
						&& isWrappingMode && !isChaseMode) {
					humanPlayer.setWrappingHighScore(highScore);
					setNewHighScore(humanPlayer);
				} else if (isChaseMode) {
					humanPlayer.setChaseHighScore(highScore);
					setNewHighScore(humanPlayer);
				}
				getTop10HighScores();
			}
		}
		updateHighScore();
		graphics.gameOver();
	}

	@Override
	public void moveTarget() {
		if (playerIsMoving(player) && (player.getTargetMovesRemaining() > 0)) {
			target.remove();
			newObsticle(target.position, obstacles, this);
			newTarget(); 
			player.movedTarget();
			graphics.setRemainingTargetMoves(player.getTargetMovesRemaining());
		}
	}

	@Override
	protected void newTarget() {
		if (obstacles.size() < (HEIGHT_SPACES * WIDTH_SPACES - 2)) {
			Vector newTargetPosition = getOpenSpace();
			target = new Target(newTargetPosition, this);
			if (isChaseMode) {
				hunter.getHunterDeadSquares().add(newTargetPosition);
			}
		}
	}
	
	@Override
	protected int getModeHighScore(HumanPlayer humanPlayer) {
		if ((speed == HIGH_SPEED) && !isWrappingMode
				&& !isChaseMode) {
			return humanPlayer.getNormalHighScore();
		} else if ((speed == HIGH_SPEED)
				&& isWrappingMode && !isChaseMode) {
			return humanPlayer.getWrappingHighScore();
		} else if (isChaseMode) {
			return humanPlayer.getChaseHighScore();
		} else {
			return 0;
		}
		
	}
	
	@Override
	protected void onTimerEvent() {
		movePlayer();
		if (isChaseMode) {
			moveHunter();
		}
		
	}

	public Hunter getHunter() {
		return hunter;
	}

}
