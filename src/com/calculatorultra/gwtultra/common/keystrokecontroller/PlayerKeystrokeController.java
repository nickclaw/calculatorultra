package com.calculatorultra.gwtultra.common.keystrokecontroller;

import static com.calculatorultra.gwtultra.common.GwtUltraUtil.DOWN_VECTOR;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.LEFT_VECTOR;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.RIGHT_VECTOR;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.UP_VECTOR;

import com.calculatorultra.gwtultra.common.Player;
import com.calculatorultra.gwtultra.common.UltraController;


public class PlayerKeystrokeController extends KeystrokeController {
	private Player player;
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public PlayerKeystrokeController(UltraController ultraController) {
		super(ultraController);
	}
	
	@Override
	public void onKeystroke(int keystroke) {
		switch (keystrokes.get(keystroke)) {
		case UP: 
			player.setDirection(UP_VECTOR);
			break;
		case DOWN:
			player.setDirection(DOWN_VECTOR);
			break;
		case LEFT:
			player.setDirection(LEFT_VECTOR);
			break;
		case RIGHT:
			player.setDirection(RIGHT_VECTOR);
			break;
		}
	}
	

}
