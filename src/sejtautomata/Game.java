package sejtautomata;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;


public class Game {
	private ArrayList<Cell> lista = new ArrayList<>();
	private Board board;

    
    public Game(int rows, int cols) {
        this.board = new Board(rows, cols);

    }
  
    
    // Listába felfűzi a cellákat
    public void createList(int rows, int cols) {
    	for (int i=0; i<20; i++) {
    		for (int k=0; k<20; k++) {
    			Cell tmpCell = new Cell(new Point(i,k));
    			lista.add(tmpCell);
    		}	
    	}
    	
    	lista.get(23).setAlive(true);
    	
	    JFrame frame = new JFrame("DrawRect");
		frame.setSize(600, 600);  
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().add(new drawBoard (lista));
	    frame.setVisible(true);
    }
    
    
    // Kiszámolja milyen mennyiségű szomszédnál pusztul el a sejt
    private ArrayList<Integer> calculateDeath(ArrayList<Integer> survive) {
    	ArrayList<Integer> newList = new ArrayList<>();
    	for (int i = 1; i <= 8; i++) {
    		newList.add(i); // Feltöltjük a newList-et 1-8-ig
    	}
    	newList.removeAll(survive);
    	return newList;
    }
    
    
    // Végig megy a listán és az aktuális szabály alapján beállítja az élő cellákat
    private void setBornAndDeath(ArrayList<Integer> born, ArrayList<Integer> survive) {
    	ArrayList<Integer> deathList = calculateDeath(survive); // A deathListben azok a intek szerepelnek amely megadja hány szomszéd esetén pusztul el a cella
    	for (Cell cellItem : lista) {
    		int neighbors = 0;
    		for (Cell cellNeighbors : cellItem.getNeighbors()) {
    			for (Cell cellItem2 : lista) {
    				if (cellNeighbors.getPoint() == cellItem2.getPoint()) {
    					neighbors++;
    				}
    			}
    		}
    		ArrayList<Integer> listDeath = calculateDeath(survive);
    		for (Integer item : listDeath) {
    			
    		}
    	}
    }
    
    

    public void start() {
    	
    	createList(10,10);
    	
    	/*
        while(true) {
         
            board.displayBoard();
            //board.updateBoard();
            try {
                Thread.sleep(1000); // 1 másodperc szünet minden generáció között
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } */
    }
}
