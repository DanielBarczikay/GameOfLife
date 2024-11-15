package sejtautomata;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cell implements Serializable {
    private static final long serialVersionUID = 1L;
	private boolean isAlive = false;
	private boolean nextIsAlive = false;
	private Point location;
	private ArrayList<Cell> neighbors = new ArrayList<>();
	private Board board;
	
	
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
	
	
    // Felveszi egy adott cella szomszédjait
    public void setNeighbors(Point position, List<Cell> lista) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Ne számoljuk saját magát
                int tempRow = (int) (position.getX() + i);
                int tempCol = (int) (position.getY() + j);
                if (board.isValidPosition(tempRow, tempCol)){
                	for (Cell item : lista) {
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