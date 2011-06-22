package com.calculatorultra.gwtultra.common.keystrokecontroller;

public interface KeystrokeHandler {
	
	public void onKeystroke(KeystrokeEvent event);
	
	public void registerKeystrokeController(KeystrokeController keyController);

	
}
