package sejtautomata;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.Serializable;
import javax.swing.JPanel;


public class Board extends JPanel implements Serializable {
	private static final long serialVersionUID = 1L;
	private final int rows; // Hány cella van egy adott sorban
	private final int cols; // Hány cella van egy adott oszlopban
	private int cellSize;
	private Cell[][] cells;
	private Cell[][] originalCells; // A restart miatt
	private Point zoomPoint = null;
	private double zoomFactor = 1.0;

	
	public Board(int rows, int cols, int cellSize) {
		this.rows = rows;
		this.cols = cols;
		this.cellSize = cellSize;
		
		// Inicializáljuk a tömböt
        cells = new Cell[rows][cols];
        originalCells = new Cell[rows][cols];
        
		// A tömbben felviszi a Cellákat
    	for (int i = 0; i < rows; i++) {
    		for (int k = 0; k < cols; k++) {
    			cells[i][k] = new Cell(new Point(i,k)); 
    			originalCells[i][k] = new Cell(new Point(i,k)); 
    		}	
    	}
  
    	mouseListener();
    }
   
	
	public void mouseListener() {
		MouseAdapter cellSelectionAdapter = new MouseAdapter() {
	        private boolean isDragging = false;

	        @Override
	        public void mousePressed(MouseEvent e) {
	            isDragging = true;
	            handleCellSelection(e);
	        }

	        @Override
	        public void mouseReleased(MouseEvent e) {
	            isDragging = false;
	        }

	        @Override
	        public void mouseDragged(MouseEvent e) {
	            if (isDragging) {
	                handleCellSelection(e);
	            }
	        }

	        private void handleCellSelection(MouseEvent e) {
	            int x = e.getX();
	            int y = e.getY();
	            
	            int cellX;
	            int cellY;
	            if (zoomPoint != null) {
	                double adjustedX = (x - zoomPoint.x) / zoomFactor + zoomPoint.x;
	                double adjustedY = (y - zoomPoint.y) / zoomFactor + zoomPoint.y;

	                cellX = (int) (adjustedX / cellSize);
	                cellY = (int) (adjustedY / cellSize);
	            } else {
	                cellX = (int) (x / cellSize);
	                cellY = (int) (y / cellSize);
	            }
	            
	            if (isValidPosition(cellX, cellY)) {
	                Cell cell = cells[cellX][cellY];
	                cell.setAlive(true); // Always set to alive when dragging
	                repaint();
	            }
	        }
	    };

	    addMouseListener(cellSelectionAdapter);
	    addMouseMotionListener(cellSelectionAdapter);
		
	    
		addMouseWheelListener(new MouseWheelListener() {
		    @Override
		    public void mouseWheelMoved(MouseWheelEvent e) {
		        int rotation = e.getWheelRotation();
		        if (rotation < 0) {
		            // Nagyítás
		            if (zoomFactor < 9.0) { // Maximális zoom tényező
		                zoomFactor *= 1.1;
		            }
		        } else {
		            // Kicsinyítés
		            if (zoomFactor > 1.0) { // Minimális zoom tényező
		                zoomFactor /= 1.1;
		            }
		        }

		        // A zoom pont az egér pozíciójánál
		        zoomPoint = e.getPoint();

		        repaint();
		    }
		});
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	    Graphics2D g2d = (Graphics2D) g;
	    
	    // Nagyítás és eltolás alkalmazása
	    if (zoomPoint != null) {
	        g2d.translate(zoomPoint.x, zoomPoint.y);
	        g2d.scale(zoomFactor, zoomFactor);
	        g2d.translate(-zoomPoint.x, -zoomPoint.y);
	    }
	    
		 // Háttér szín
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
		
        // Végigmegyünk a tömbön
       	for (int i = 0; i < rows; i++) {
    		for (int k = 0; k < cols; k++) {
    			if (cells[i][k].isAlive()) g.setColor(Color.WHITE);
    			else g.setColor(Color.DARK_GRAY);
    			
    			g.fillRect(i * cellSize, k * cellSize, cellSize, cellSize);
    			
    			// Szélek
    			g.setColor(Color.DARK_GRAY);
    			g.drawRect(i * cellSize, k * cellSize, cellSize, cellSize);
    		}
		}
	}
	
	
	// Getterek
	public Cell[][] getCells() {
    	return cells;
    }
	
	
	public Cell[][] getOriginalCells() {
    	return originalCells;
    }
	
	
    public int getRows() {
    	return rows;
    }
    
    public int getCols() {
    	return cols;
    }
    
    public int getCellSize() {
    	return cellSize;
    }
    
	
	public void setOriginalCells() {
    	for (int i = 0; i < rows; i++) {
    	    for (int j = 0; j < cols; j++) {
    	    	originalCells[i][j].setAlive(cells[i][j].isAlive());
    	    }
    	}
	}

    
    // Ellenőrzi, hogy a pozíció a tábla határain belül van-e
    public boolean isValidPosition(int row, int col) {
    	return row >= 0 && row < rows && col >= 0 && col < cols;
    }

}
