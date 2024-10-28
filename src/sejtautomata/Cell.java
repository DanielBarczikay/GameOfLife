package sejtautomata;

import java.awt.Point;
import java.util.ArrayList;

public class Cell {
	private boolean isAlive = false;
	private boolean nextIsAlive;
	Point location;
	private ArrayList<Cell> neighbors = new ArrayList<>();
	private Board board;
	
	public Cell(Point position) {
		location = position;
	}
	
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public Point getPoint() {
		return location;
	}
	
	
	public void setAlive(boolean alive) {
		isAlive = alive;
	}
	
    
    // Felveszi a cellák szomszédjait
    public void setNeighbors(Point position) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Ne számoljuk saját magát
                int tempRow = (int) (position.getX() + i);
                int tempCol = (int) (position.getY() + j);
                if (board.isValidPosition(tempRow, tempCol)){
                	for (Cell item : neighbors) {
                		if ((item.getPoint().getX() == tempRow) && (item.getPoint().getY() == tempCol)) {
                			neighbors.add(item);
                		}
                	}
                }
            }
        }
    }
    
    
    public ArrayList<Cell> getNeighbors(){
    	return neighbors;
    }
    
}
