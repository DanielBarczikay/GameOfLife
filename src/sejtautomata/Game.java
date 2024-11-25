package sejtautomata;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.SwingUtilities;


public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
	private ArrayList<Integer> born = new ArrayList<>();
	private ArrayList<Integer> survive = new ArrayList<>();
	private Board board;
	private boolean isRunning = false;
	private boolean firstRunning = true;
	private int speed = 60;
	private Thread gameThread;  // Új thread
	private static final int[][] NEIGHBORS = {
		    {-1, -1}, {-1, 0}, {-1, 1},
		    {0, -1},          {0, 1},
		    {1, -1}, {1, 0}, {1, 1}};
	// A deathListben azok a intek szerepelnek amely megadja hány szomszéd esetén pusztul el a cella
	private ArrayList<Integer> deathList = new ArrayList<>();

    
    public Game() {
    	// Alapértelmezett Conway-féle model (S23, B3)
    	setSurvive("23");
    	setBorn("3");
    }
    
    // Setterek
    public void setBoard(Board board) {
    	stopGame(); // Leállítjuk a futó játékot board csere előtt
        this.board = board;
        SwingUtilities.invokeLater(() -> board.repaint());
    }
    
    public void setSpeed(String speedStr) {
    	int speedInt = Integer.parseInt(speedStr);
    	this.speed = speedInt;
    }
    
    public void setSurvive(String survive){
    	this.survive = stringToList(survive);
    	calculateDeath();
    }
    
    public void setBorn(String born){
    	this.born = stringToList(born);
    }
    
    
    // Getterek
    public Board getBoard() {
    	return board;
    }
    
    public int getSpeed() {
    	return speed;
    }
    
    public ArrayList<Integer> getDeathList() {
    	return deathList;
    }
    
    
    // Kiszámolja milyen mennyiségű szomszédnál pusztul el a sejt
    private void calculateDeath() {
    	deathList.clear();
    	for (int i = 0; i <= 8; i++) {
    		deathList.add(i); // Feltöltjük a newList-et 0-8-ig
    	}
    	deathList.removeAll(survive);
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
    
    
    // Játék indítása
    public void startGame() throws InterruptedException {
    	if (isRunning) return; // Ha már fut, ne indítsunk új szálat
        
        isRunning = true;
        // Első futáskor beállítjuk az eredeti cella értékeket a Restart miatt
        if (firstRunning) {
            board.setOriginalCells();
            firstRunning = false;
        }

        gameThread = new Thread(() -> {
            try {
                while (isRunning) {
                    SwingUtilities.invokeLater(() -> {
                        refreshCells();
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
    
    
    // Játék megállítása
    public void stopGame() {
    	isRunning = false; // Megállítja a futást
        if (gameThread != null) {
            gameThread.interrupt();
            try {
                gameThread.join(500); // Várunk fél másodpercet a szál befejezésére
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    // Minden cellát false-ra állítunk
    public void resetGame() {
        stopGame();
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < board.getRows(); i++) {
                for (int k = 0; k < board.getCols(); k++) {
                    board.getCells()[i][k].setAlive(false);
                    board.getCells()[i][k].setFade(0);
                }
            }
            board.repaint();
        });
    }
    
    // Cellák visszaállítása az eredeti állapotukba
    public void restartGame() {
        stopGame();
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getCols(); j++) {
                	Cell originalCell = board.getOriginalCells()[i][j];
                	Cell currentCell = board.getCells()[i][j];
                	
                	currentCell.setAlive(originalCell.isAlive());
            		currentCell.setNextAlive(originalCell.isAlive());
            		// Ha az eredeti cella halott volt
                	if (!originalCell.isAlive()) 
                		currentCell.setFade(0);
                }
            }
            board.repaint();
        });
    }

    
    // Következő iterációba lépés
    public void leptetes() {
        for (int i = 0; i < board.getRows(); i++) {
            for (int k = 0; k < board.getCols(); k++) {
                Cell cell = board.getCells()[i][k];
                
                // Ha most halt meg, indul a fadeCounter
                if (!cell.getNextAlive() && cell.isAlive()) {
                    cell.setAlive(false);
                // Ha életben maradhat
                } else if (cell.getNextAlive()) {
                    cell.setAlive(true);
                // Ha korábban meghalt csak csökkentjük a fadeCountert
                } else {
                    cell.decreaseFade();
                }
            }
        }
    }
  
  
	// Végig megy a tömbön és az aktuális szabály alapján beállítja a cellák állapotát
	public void refreshCells() {
		
		for (int i = 0; i < board.getRows(); i++) {
    		for (int k = 0; k < board.getCols(); k++) {
    			Cell tmpCell = board.getCells()[i][k];
    			int aliveNeighbors = getAliveNeighbors(i, k, board.getCells());
    			// Ha a born lista tartalmazza a cellának a szomszéd számát
    			if (born.contains(aliveNeighbors)) tmpCell.setNextAlive(true);
    			// Ha a death lista tartalmazza a cellának a szomszéd számát
    			else if (deathList.contains(aliveNeighbors)) tmpCell.setNextAlive(false);
    		}
		}
	}
	
	
    // Visszaadja egy adott cella körüli élő szomszédjainak számát
    private int getAliveNeighbors(int x, int y, Cell[][] cells) {
    	int count = 0;
    	for (int[] offset : NEIGHBORS) {
            int tempRow = x + offset[0];
            int tempCol = y + offset[1];
            if (board.isValidPosition(tempRow, tempCol) && cells[tempRow][tempCol].isAlive())
                count++;
            }
        return count;
    }
}
