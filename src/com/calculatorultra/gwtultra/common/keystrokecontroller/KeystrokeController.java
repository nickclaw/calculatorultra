package com.calculatorultra.gwtultra.common.keystrokecontroller;

import java.util.HashMap;
import java.util.Map;

import com.calculatorultra.gwtultra.common.GwtUltraUtil.Function;
import com.calculatorultra.gwtultra.common.UltraController;

public abstract class KeystrokeController {
	protected Map<Integer, Function> keystrokes = new HashMap<Integer, Function>();
	protected final UltraController ultraController;
	
	public KeystrokeController(UltraController ultraController) {
		this.ultraController = ultraController;
		ultraController.registerKeystrokeController(this);
	}
	
	public void addKeystroke(Integer keystroke, Function function) {
		keystrokes.put(keystroke, function);
	}
	
	public abstract void onKeystroke(int keystroke);
	
	public Map<Integer, Function> getKeystrokes() {
		return keystrokes;
	}
}
