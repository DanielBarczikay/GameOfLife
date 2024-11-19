package sejtautomata;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.SwingUtilities;


public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
	private ArrayList<Integer> born = new ArrayList<>();
	private ArrayList<Integer> survive = new ArrayList<>();
	Board board;
	private boolean isRunning = false;
	boolean firstRunning = true;
	private int speed = 60;
	 private Thread gameThread;  // Új thread referencia
	
	// A deathListben azok a intek szerepelnek amely megadja hány szomszéd esetén pusztul el a cella
	private ArrayList<Integer> deathList = new ArrayList<>();

    
    public Game() {
    	// Alapértelmezett Conway-féle model (S23, B3)
    	survive.add(2);
    	survive.add(3);
    	calculateDeath(); 
    	born.add(3);
    }
    
    
    public void setBoard(Board board) {
    	stopGame(); // Leállítjuk a futó játékot board csere előtt
        this.board = board;
        SwingUtilities.invokeLater(() -> board.repaint());
    }
    
    public Board getBoard() {
    	return board;
    }
    
    public void setSpeed(String speedStr) {
    	int speedInt = Integer.parseInt(speedStr);
    	this.speed = speedInt;
    }
    
    
    public void setBorn(String born){
    	this.born = stringToList(born);
    }
    
    public void setSurvive(String survive){
    	this.survive = stringToList(survive);
    	calculateDeath();
    }
    
    public void newGame() {
    	firstRunning = true;
    	stopGame();
    	SwingUtilities.invokeLater(() -> {
            try {
                startGame();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
    
    
    public void startGame() throws InterruptedException {
    	
    	if (isRunning) return; // Ha már fut, ne indítsunk új szálat
        
        isRunning = true;
        if (firstRunning) {
            board.setOriginalCells();
            firstRunning = false;
        }

        gameThread = new Thread(() -> {
            try {
                while (isRunning) {
                    SwingUtilities.invokeLater(() -> {
                        setBornAndDeath();
                        leptetes();
                        board.repaint();
                    });
                    Thread.sleep(speed);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        gameThread.start(); // Indítja a játék szálát
    }
    
    
    public void stopGame() {
    	isRunning = false; // Megállítja a ciklust
        if (gameThread != null) {
            gameThread.interrupt();
            try {
                gameThread.join(1000); // Várunk max 1 másodpercet a szál befejezésére
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
    
    
    public void resetGame() {
        stopGame();
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < board.getRows(); i++) {
                for (int k = 0; k < board.getCols(); k++) {
                    board.getCells()[i][k].setAlive(false);
                }
            }
            board.repaint();
        });
    }

    
    
    public void restartGame() {
        stopGame();
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getCols(); j++) {
                    board.getCells()[i][j].setAlive(board.getOriginalCells()[i][j].isAlive());
                    board.getCells()[i][j].setNextAlive(board.getOriginalCells()[i][j].isAlive());
                }
            }
            board.repaint();
        });
    }

    
    
    // Megkap egy Stringet és visszaad egy Arraylist<Integer>-t
    private ArrayList<Integer> stringToList(String string) {
    	ArrayList<Integer> lista = new ArrayList<>();
    	
    	for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (Character.isDigit(c)) { // Ellenőrizzük, hogy a karakter egy számjegy-e
                lista.add(Character.getNumericValue(c)); // Karakter konvertálása számmá és hozzáadása a listához
            }
        }
        return lista;
    }
    
    
    // Következő iteráció beállítása
    private void leptetes() {
    	for (int i = 0; i < board.getRows(); i++) {
    		for (int k = 0; k < board.getCols(); k++) {
    			Cell tmpCell = board.getCells()[i][k];
    			tmpCell.setAlive((tmpCell.getNextAlive()) ? true : false);
    		}
    	}
    }
  
    
    // Kiszámolja milyen mennyiségű szomszédnál pusztul el a sejt
    private void calculateDeath() {
    	for (int i = 0; i <= 8; i++) {
    		deathList.add(i); // Feltöltjük a newList-et 1-8-ig
    	}
    	deathList.removeAll(survive);
    }
    
  
	// Végig megy a tömbön és az aktuális szabály alapján beállítja a cellákat
	private void setBornAndDeath() {
		
		for (int i = 0; i < board.getRows(); i++) {
    		for (int k = 0; k < board.getCols(); k++) {
    			Cell tmpCell = board.getCells()[i][k];
    			int aliveNeighbors = getAliveNeighbors(tmpCell.getPoint(), board.getCells());
    			
    			if (born.contains(aliveNeighbors)) tmpCell.setNextAlive(true);
    			else if (deathList.contains(aliveNeighbors)) tmpCell.setNextAlive(false);
    		}
		}
	}
	
	
    // Visszaadja egy adott cella körüli élő szomszédjainak számát
    private int getAliveNeighbors(Point position, Cell[][] cells) {
    	int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Ne számoljuk saját magát
                int tempRow = (int) (position.getX() + i);
                int tempCol = (int) (position.getY() + j);
                if (board.isValidPosition(tempRow, tempCol) && cells[tempRow][tempCol].isAlive()){
                	count++;
                }
            }
        }
        return count;
    }
	

}
