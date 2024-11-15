package sejtautomata;

import java.io.Serializable;

import javax.swing.JPanel;

public class Board extends JPanel implements Serializable {
	private int rows;
	private int cols;

	
    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        initializeBoard();
    }
    
    
    public int getRows() {
    	return rows;
    }
    
    public int getCols() {
    	return cols;
    }

    
    // Inicializáljuk a táblát véletlenszerű állapotú sejtekkel vagy meghatározott mintával
    private void initializeBoard() {
    	
    }


    // Ellenőrzi, hogy a pozíció a tábla határain belül van-e
    public  boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    
    // Frissítjük a tábla állapotát a játékszabályok alapján
    public void updateBoard() {
    	
    }

    public void displayBoard() {
    	
    }
}
