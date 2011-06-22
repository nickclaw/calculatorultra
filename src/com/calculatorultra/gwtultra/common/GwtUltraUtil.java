package com.calculatorultra.gwtultra.common;

import java.util.List;

import com.calculatorultra.gwtultra.common.keystrokecontroller.OptionsKeystrokeController;
import com.calculatorultra.gwtultra.common.keystrokecontroller.PlayerKeystrokeController;


public class GwtUltraUtil {
	public static final int INITIAL_TARGET_MOVES = 5;
	public static final Vector UP_VECTOR = new Vector(0,-1);
	public static final Vector DOWN_VECTOR = new Vector(0,1);
	public static final Vector RIGHT_VECTOR = new Vector(1,0);
	public static final Vector LEFT_VECTOR = new Vector(-1,0);
	public static final int HEIGHT_SPACES = 8;
	public static final int WIDTH_SPACES = 16;
	public static final int HIGH_SPEED = 100;
	public static final int MED_SPEED = 200;
	public static final int LOW_SPEED = 300;
	public static final int SPACE_SIZE_PX = 50;
	public static final int SPACE_BAR = 32;
	public static final int SLASH = 191;
	public static final int SHIFT = 16;
	public static final int OPTION = 18;
	public static final int Z = 90;
	public static final int R = 82;
	public static final int Q = 81;
	public static final int W = 87;
	public static final int A = 65;
	public static final int T = 84;
	public static final int ZERO = 96;
	public static final int E = 69;
	public static final int S = 83;
	public static final int D = 68;
	public static final int N = 78;
	public static final int P = 80;
	public static final int LEFT_ARROW = 37;
	public static final int RIGHT_ARROW = 39;
	public static final int UP_ARROW = 38;
	public static final int DOWN_ARROW = 40;
	public static final Vector UP = new Vector(0,-1);
	public static final Vector DOWN = new Vector(0,1);
	public static final Vector LEFT = new Vector(-1,0);
	public static final Vector RIGHT = new Vector(1,0);
	
	public static void newObsticle(Vector position, List<FieldObject> obstacles, UltraController controller) {
		obstacles.add(new Obstacle(new Vector(position), controller));
	}

	public static Vector newRandomVector() {
		return new Vector((int) (Math.random() * WIDTH_SPACES),
				(int) (Math.random() * HEIGHT_SPACES));
	}

	public static float calculateAveragePoints(Player player, List<FieldObject> obstacles) {
		// Determines the Average Point Value (APV) received of each target
		if (obstacles.size() > 0) { // Prevents Divide by 0!
			float averagePoints = (float) (player.getScore())/ (obstacles.size());
			return averagePoints;
		} else {
			return 0;
		}
	}
	
	public static boolean playerIsOutOfBounds(Player player) {
		if (((player.position.x) < 0)
			|| ((player.position.x) >= WIDTH_SPACES)
			|| ((player.position.y) < 0)
			|| ((player.position.y) >= HEIGHT_SPACES)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void stopPlayers(Player...players) {
		for (Player player : players) {
			player.setDirection(new Vector(0, 0));
		}
	}
	
	public static void wrapPlayers(Player...players) {
		for (Player player : players) {
			if ((player.position.x + player.direction.x) < 0) {
				player.position.x += WIDTH_SPACES;
			} else if ((player.position.x + player.direction.x) >= WIDTH_SPACES) {
				player.position.x -= WIDTH_SPACES;
			} else if ((player.position.y + player.direction.y) < 0) {
				player.position.y += HEIGHT_SPACES;
			} else if ((player.position.y + player.direction.y) >= HEIGHT_SPACES) {
				player.position.y -= HEIGHT_SPACES;
			}
		}
	}
	

	public static boolean playerIsMoving(Player...players) {
		for (Player player : players) {
			if (player.direction.equals(new Vector(0,0)) == false) {
				return true;
			}
		}
		return false;
	}
	
	public static void setSinglePlayerControls(PlayerKeystrokeController playerKeyController) {
		playerKeyController.addKeystroke(W, Function.UP);
		playerKeyController.addKeystroke(UP_ARROW, Function.UP);
		playerKeyController.addKeystroke(S, Function.DOWN);
		playerKeyController.addKeystroke(UP_ARROW, Function.DOWN);
		playerKeyController.addKeystroke(A, Function.LEFT);
		playerKeyController.addKeystroke(UP_ARROW, Function.LEFT);
		playerKeyController.addKeystroke(D, Function.RIGHT);
		playerKeyController.addKeystroke(UP_ARROW, Function.RIGHT);
	}
	
	public static void setOptionsControls(OptionsKeystrokeController optionsKeyController) {
		optionsKeyController.addKeystroke(E, Function.MOVE_TARGET);
		optionsKeyController.addKeystroke(SLASH, Function.MOVE_TARGET);
		optionsKeyController.addKeystroke(ZERO, Function.MOVE_TARGET);
		optionsKeyController.addKeystroke(N, Function.NEW_GAME);
		optionsKeyController.addKeystroke(SPACE_BAR, Function.NEW_GAME);
		optionsKeyController.addKeystroke(P, Function.PAUSE);
	}
	
	public static enum Mode {
		WRAPPING, SPEED, CHASE, REPEATING
	}
	
	public static enum Speed {
		HIGH, LOW, MED
	}
	
	public static enum Function {
		UP, DOWN, LEFT, RIGHT, PAUSE, NEW_GAME, MOVE_TARGET
	}
	
}
