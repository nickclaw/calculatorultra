package com.calculatorultra.gwtultra.common;

public class Vector {
	public int x, y;

	public Vector() {
		this(0, 0);
	}

	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector(Vector v) {
		this(v.x, v.y);
	}

	public void add(int x, int y) {
		this.x += x;
		this.y += y;
	}

	public void add(Vector v) {
		add(v.x, v.y);
	}

	public void sub(Vector v) {
		sub(v.x, v.y);
	}

	public void sub(int x, int y) {
		this.x -= x;
		this.y -= y;
	}

	public void mult(int x, int y) {
		this.x *= x;
		this.y *= y;
	}

	public void mult(Vector v) {
		mult(v.x, v.y);
	}

	public void mult(int c) {
		mult(c, c);
	}

	public void set(Vector v) {
		x = v.x;
		y = v.y;
	}

	public static Vector sub(Vector a, Vector b) {
		return new Vector(a.x - b.x, a.y - b.y);
	}

	public static Vector mult(Vector v, int c) {
		return new Vector(v.x * c, v.y * c);
	}
	
	public boolean equals(Vector vector) {
		if (this.x == vector.x && this.y == vector.y) {
			return true;
		} else return false;
	}
 }
