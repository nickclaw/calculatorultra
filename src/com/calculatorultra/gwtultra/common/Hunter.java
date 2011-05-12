package com.calculatorultra.gwtultra.common;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.DOWN_VECTOR;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.LEFT_VECTOR;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.RIGHT_VECTOR;
import static com.calculatorultra.gwtultra.common.GwtUltraUtil.UP_VECTOR;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;


public class Hunter extends MovingFieldObject {
	private final GwtUltra gwtUltra;
	private final Player playerTarget;
	private final Target target;
	private final ArrayList<Vector> hunterDeadSquares = new ArrayList<Vector>();

	// the positive Y component of the direction vector is down
	
	public Hunter(Vector startPosition, Player playerTarget, Target target, GwtUltra gwtUltra) {
		this.position = startPosition;
		this.graphics = gwtUltra.getGraphicsEngine();
		this.gwtUltra = gwtUltra;
		this.playerTarget = playerTarget;
		this.target = target;
		graphics.addToField(this);
		direction = new Vector(0, 0);
	}

	@Override
	public void move() {
		setDirection(calcBestDirection());
		position.add(direction);
	}
	
	public boolean checkOpen(Vector positionToCheck)
	{
		for (Obstacle obstacle : gwtUltra.getObstacles()) {
			if (positionToCheck.equals(obstacle.position)) {
				return false;
			}
		}
		for (Vector deadSquare : hunterDeadSquares) {
			if (positionToCheck.equals(deadSquare)) {
				return false;
			}
		}
		return true;
	}
	
	public Vector calcDirection()
	{
		Vector distanceFromPlayer = new Vector(Math.abs(position.x - playerTarget.position.x), Math.abs(position.y - playerTarget.position.y));
		boolean upIsOpen = checkOpen(new Vector(position.x, position.y - 1));
		boolean downIsOpen = checkOpen(new Vector(position.x, position.y + 1));
		boolean leftIsOpen = checkOpen(new Vector(position.x - 1, position.y));
		boolean rightIsOpen = checkOpen(new Vector(position.x + 1, position.y));
		if(distanceFromPlayer.equals(new Vector(0,0)))
			return (new Vector(0, 0));
		int openDirections = 0;
		if (upIsOpen) openDirections++;
		if (downIsOpen) openDirections++;
		if (leftIsOpen) openDirections++;
		if (rightIsOpen) openDirections++;
		if(openDirections <= 1) {
			hunterDeadSquares.add(position);
			if(upIsOpen)
				return UP_VECTOR;
			if(downIsOpen)
				return DOWN_VECTOR;
			if(leftIsOpen)
				return LEFT_VECTOR;
			if(rightIsOpen)
				return RIGHT_VECTOR;
		}
		if(distanceFromPlayer.x > distanceFromPlayer.y && leftIsOpen && position.x > playerTarget.position.x) {
			return LEFT_VECTOR;
		}
		else if(distanceFromPlayer.x > distanceFromPlayer.y && rightIsOpen && position.x < playerTarget.position.x) {
			return RIGHT_VECTOR;
		}
		else if(downIsOpen && position.y < playerTarget.position.y) {
			return DOWN_VECTOR;
		}
		else if(upIsOpen && position.y > playerTarget.position.y) {
			return UP_VECTOR;
		}
		
		return (new Vector(-2, -2));
	}
	
	public Vector calcBestDirection() {
		int[][] matrix = new int[16][8];
		ArrayList<Vector> squaresToCheck = new ArrayList<Vector>();

		for(int col = 0; col < matrix.length; col++) {
			for(int row = 0; row < matrix[0].length; row++) {
				matrix[col][row] = -1;
			}
		}
		
		int count = 0;
		matrix[playerTarget.position.x][playerTarget.position.y] = count;
		squaresToCheck.add(new Vector(playerTarget.position.x, playerTarget.position.y));
		Vector current = squaresToCheck.get(0);
		boolean foundTarget = false;
		
		while(squaresToCheck.size() > 0 && !foundTarget)
		{
			current = squaresToCheck.get(0);
			count = matrix[current.x][current.y];
			{
				if(current.x == position.x && current.y == position.y) {
					foundTarget = true;
				}
				if(current.x < 15 && (matrix[current.x + 1][current.y] < 0 || matrix[current.x + 1][current.y] > count + 1)) {
					matrix[current.x + 1][current.y] = count + 1;
					Vector right = new Vector(current.x + 1, current.y);
					if(checkOpen(right))
						squaresToCheck.add(right);
				}
				if(current.x > 0 && (matrix[current.x - 1][current.y] < 0 || matrix[current.x - 1][current.y] > count + 1)) {
					matrix[current.x - 1][current.y] = count + 1;
					Vector left = new Vector(current.x - 1, current.y);
					if(checkOpen(left))
						squaresToCheck.add(left);
				}
				if(current.y < 7 && (matrix[current.x][current.y + 1] < 0 || matrix[current.x][current.y + 1] > count + 1)) {
					matrix[current.x][current.y + 1] = count + 1;
					Vector down = new Vector(current.x, current.y + 1);
					if(checkOpen(down))
						squaresToCheck.add(down);
				}
				if(current.y > 0 && (matrix[current.x][current.y - 1] < 0 || matrix[current.x][current.y - 1] > count + 1)) {
					matrix[current.x][current.y - 1] = count + 1;
					Vector up = new Vector(current.x, current.y - 1);
					if(checkOpen(up))
						squaresToCheck.add(up);
				}
			}
			squaresToCheck.remove(0);
		}
		if(foundTarget) {
			
			ArrayList<Vector> shortestOptions = new ArrayList<Vector>();
			int min = -1;
			if(checkOpen(new Vector(current.x + 1, current.y)) && current.x < 15){
				min= matrix[current.x + 1][current.y];
				shortestOptions.add(new Vector(1, 0));
			}
			if(checkOpen(new Vector(current.x - 1, current.y)) && current.x > 0 && (min == -1 || matrix[current.x - 1][current.y] <= min)) {
				if(matrix[current.x - 1][current.y] != -1 && matrix[current.x - 1][current.y] < min)
				{
					shortestOptions.clear();
				}
				shortestOptions.add(new Vector(-1, 0));
				min = matrix[current.x - 1][current.y];
			}
			if(checkOpen(new Vector(current.x, current.y + 1)) && current.y < 7 && (min == -1 || matrix[current.x][current.y + 1] <= min)) {
				if(matrix[current.x][current.y + 1] != -1 && matrix[current.x][current.y + 1] < min)
				{
					shortestOptions.clear();
				}
				shortestOptions.add(new Vector(0, 1));
				min = matrix[current.x][current.y + 1];
			}
			if(checkOpen(new Vector(current.x, current.y - 1)) && current.y > 0 && (min == -1 || matrix[current.x][current.y - 1] <= min)) {
				if(matrix[current.x][current.y - 1] != -1 && matrix[current.x][current.y - 1] < min)
				{
					shortestOptions.clear();
				}
				shortestOptions.add(new Vector(0, -1));
				min = matrix[current.x][current.y - 1];
			}
			if(Math.abs(playerTarget.position.x - current.x) + Math.abs(playerTarget.position.y - current.y) == 1 && !checkOpen(new Vector(playerTarget.position.x, playerTarget.position.y))) {
				GWT.log("00");
				Vector distanceFromTarget = new Vector(Math.abs(position.x - target.position.x), Math.abs(position.y - target.position.y));
				boolean upIsOpen = checkOpen(new Vector(position.x, position.y - 1));
				boolean downIsOpen = checkOpen(new Vector(position.x, position.y + 1));
				boolean leftIsOpen = checkOpen(new Vector(position.x - 1, position.y));
				boolean rightIsOpen = checkOpen(new Vector(position.x + 1, position.y));
				
				int openDirections = 0;
				if (upIsOpen) openDirections++;
				if (downIsOpen) openDirections++;
				if (leftIsOpen) openDirections++;
				if (rightIsOpen) openDirections++;
				if(openDirections <= 1) {
					hunterDeadSquares.add(position);
					if(upIsOpen)
						return UP_VECTOR;
					if(downIsOpen)
						return DOWN_VECTOR;
					if(leftIsOpen)
						return LEFT_VECTOR;
					if(rightIsOpen)
						return RIGHT_VECTOR;
				}
				if(distanceFromTarget.x >= distanceFromTarget.y && leftIsOpen && position.x > target.position.x) {
					return LEFT_VECTOR;
				}
				else if(distanceFromTarget.x >= distanceFromTarget.y && rightIsOpen && position.x < target.position.x) {
					return RIGHT_VECTOR;
				}
				else if(downIsOpen && position.y <= target.position.y) {
					return DOWN_VECTOR;
				}
				else if(upIsOpen && position.y >= target.position.y) {
					return UP_VECTOR;
				}
				else if(leftIsOpen && position.x >= target.position.x) {
					return LEFT_VECTOR;
				}
				else if(rightIsOpen && position.x <= target.position.x) {
					return RIGHT_VECTOR;
				}
				else if(downIsOpen) {
					return DOWN_VECTOR;
				}
				else if(upIsOpen) {
					return UP_VECTOR;
				}
				else if(leftIsOpen) {
					return LEFT_VECTOR;
				}
				else if(rightIsOpen) {
					return RIGHT_VECTOR;
				}
				GWT.log("00");
				return new Vector(0,0);
			}
			Vector distanceFromPlayer = new Vector(Math.abs(position.x - playerTarget.position.x), Math.abs(position.y - playerTarget.position.y));
			ArrayList<Vector> toRemove = new ArrayList<Vector>();

			if(distanceFromPlayer.x > distanceFromPlayer.y)	{
				for(Vector checkThis : shortestOptions) {
					if(checkThis.x == 0)
						toRemove.add(checkThis);
				}
			}
			else if(distanceFromPlayer.y > distanceFromPlayer.x){
				for(Vector checkThis : shortestOptions) {
					if(checkThis.y == 0)
						toRemove.add(checkThis);
				}
			}
			if(toRemove.size() < shortestOptions.size()) {
				for(Vector removeThis : toRemove) {
					shortestOptions.remove(removeThis);
				}
			}
			return shortestOptions.get(0);
		}
		else
		{
			Vector distanceFromTarget = new Vector(Math.abs(position.x - target.position.x), Math.abs(position.y - target.position.y));
			boolean upIsOpen = checkOpen(new Vector(position.x, position.y - 1));
			boolean downIsOpen = checkOpen(new Vector(position.x, position.y + 1));
			boolean leftIsOpen = checkOpen(new Vector(position.x - 1, position.y));
			boolean rightIsOpen = checkOpen(new Vector(position.x + 1, position.y));
			
			if(distanceFromTarget.x >= distanceFromTarget.y && leftIsOpen && position.x > target.position.x) {
				return LEFT_VECTOR;
			}
			else if(distanceFromTarget.x >= distanceFromTarget.y && rightIsOpen && position.x < target.position.x) {
				return RIGHT_VECTOR;
			}
			else if(downIsOpen && position.y <= target.position.y) {
				return DOWN_VECTOR;
			}
			else if(upIsOpen && position.y >= target.position.y) {
				return UP_VECTOR;
			}
			else if(leftIsOpen && position.x >= target.position.x) {
				return LEFT_VECTOR;
			}
			else if(rightIsOpen && position.x <= target.position.x) {
				return RIGHT_VECTOR;
			}
			else if(downIsOpen) {
				return DOWN_VECTOR;
			}
			else if(upIsOpen) {
				return UP_VECTOR;
			}
			else if(leftIsOpen) {
				return LEFT_VECTOR;
			}
			else if(rightIsOpen) {
				return RIGHT_VECTOR;
			}
			else
				return new Vector(0,0); 
		}
	}

	public ArrayList<Vector> getHunterDeadSquares() {
		return hunterDeadSquares;
	}
	
}
