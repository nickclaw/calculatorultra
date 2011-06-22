package com.calculatorultra.gwtultra.common.keystrokecontroller;

import com.calculatorultra.gwtultra.common.UltraController;

public class OptionsKeystrokeController extends KeystrokeController {

	public OptionsKeystrokeController(UltraController ultraController) {
		super(ultraController);
	}

	@Override
	public void onKeystroke(int keystroke) {
		switch (keystrokes.get(keystroke)) {
		case PAUSE: 
			ultraController.pause();
			break;
		case NEW_GAME:
			ultraController.startNewRound();
			break;
		case MOVE_TARGET:
			ultraController.moveTarget();
			break;
		}
	}
}
