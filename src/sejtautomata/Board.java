package sejtautomata;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JPanel;


public class Board extends JPanel implements Serializable {
	private static int rows; // Hány cella van egy adott sorban
	private static int cols; // Hány cella van egy adott oszlopban

	private static final int CELL_SIZE = 7;
	private static int absoluteRows = rows * CELL_SIZE;
	private static int absoluteCols = cols * CELL_SIZE;
	private ArrayList<Cell> cells = new ArrayList<>();
	
	public Board(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	
		// Listába felfűzi a cellákat
    	for (int i = 0; i < rows; i++) {
    		for (int k = 0; k < cols; k++) {
    			Cell tmpCell = new Cell(new Point(i,k));
    			cells.add(tmpCell);
    		}	
    	}
    	// Beállítjuk a szomszédokat
    	for (Cell cellItem : cells) {
    		cellItem.setNeighbors(cellItem.getPoint(), cells);
    	}
    	
    	cells.get(23).setAlive(true);
    	cells.get(24).setAlive(true);
    	cells.get(25).setAlive(true);
    }
   
	
	public void mouseListener() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
                int y = e.getY();
                
                // Kiszámoljuk, melyik cellába kattintottunk
                int cellX = x / CELL_SIZE;
                int cellY = y / CELL_SIZE;
                
                if (isValidPosition(cellX, cellY)
			}
		
		
		});
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		 // Háttér szín
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
		
		for (Cell cellItem : cells) {
			if (cellItem.isAlive()) g.setColor(Color.WHITE);
			else g.setColor(Color.DARK_GRAY);
			
			int x = (int) cellItem.getPoint().getX();
			int y = (int) cellItem.getPoint().getY();
			g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
			
			// Szélek
			g.setColor(Color.DARK_GRAY);
			g.drawRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
		}
	}
	
	
	// Getterek
    public int getRows() {
    	return rows;
    }
    
    public int getCols() {
    	return cols;
    }
    
    public int getCellSize() {
    	return CELL_SIZE;
    }

    
    // Ellenőrzi, hogy a pozíció a tábla határain belül van-e
    public static boolean isValidPosition(int row, int col) {
    	return row >= 0 && row < rows && col >= 0 && col < cols;
    }

}
