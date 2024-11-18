package sejtautomata;

import java.awt.Point;
import java.io.Serializable;


public class Cell implements Serializable {
    private static final long serialVersionUID = 1L;
	private boolean isAlive = false;
	private boolean nextIsAlive = false;
	private Point location;
	
	
	public Cell(Point position) {
		location = position;
	}
	
	// Getter
	public boolean isAlive() {
		return isAlive;
	}
	
	public Point getPoint() {
		return location;
	}
	
	public boolean getNextAlive() {
		return nextIsAlive;
	}
	
    
	// Setter
	public void setAlive(boolean alive) {
		isAlive = alive;
	}
	
	public void setNextAlive(boolean alive) {
		nextIsAlive = alive;
	}
	
	
    
}