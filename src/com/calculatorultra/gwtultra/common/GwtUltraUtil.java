package com.calculatorultra.gwtultra.common;

import com.calculatorultra.gwtultra.common.keystrokecontroller.PlayerKeystrokeController;


public class GwtUltraUtil {
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
