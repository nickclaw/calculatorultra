package com.calculatorultra.gwtultra.common.keystrokecontroller;

public class KeystrokeEvent {
	private final Object source;
	private final int keystroke;
	
	public KeystrokeEvent(Object source, int keystroke) {
		this.source = source;
		this.keystroke = keystroke;
	}
	
	public Object getSource() {
		return source;
	}
	
	public int getKeystroke() {
		return keystroke;
	}
}
