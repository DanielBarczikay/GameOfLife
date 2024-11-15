package sejtautomata;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;


public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
	private ArrayList<Cell> cells = new ArrayList<>();
	private ArrayList<Integer> survive = new ArrayList<>();
	private ArrayList<Integer> born = new ArrayList<>();
	
	// A deathListben azok a intek szerepelnek amely megadja hány szomszéd esetén pusztul el a cella
	private ArrayList<Integer> deathList = new ArrayList<>();
	private Board board;

    
    public Game() {}
    
    
    public void start() {
    	createList();
    	// A deathListben azok a intek szerepelnek amely megadja hány szomszéd esetén pusztul el a cella
    	calculateDeath(); 
    	
    	
    }
    
    // Getterek
    public ArrayList<Cell> getCells(){
    	return cells;
    }
    
    public ArrayList<Integer> getBorn(){
    	return born;
    }
    
    public ArrayList<Integer> getSurvive(){
    	return survive;
    }
    
    
    // Setterek
    public void setBorn(ArrayList<Integer> born) {
    	this.born = born;
    }
    
    public void setSurvive(ArrayList<Integer> survive){
    	this.survive = survive;
    }
    
    
    
    // Kell egy olyan függvény ami megkap egy Stringet és visszaad egy Arraylist<Integer>-t
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
    	//setBornAndDeath();
    	for (Cell cellItem : cells) {
    		cellItem.setAlive((cellItem.getNextAlive()) ? true : false);
    	}
    }
  
    
    // Listába felfűzi a cellákat
    public void createList() {
    	for (int i = 0; i < board.getRows(); i++) {
    		for (int k = 0; k < board.getCols(); k++) {
    			Cell tmpCell = new Cell(new Point(i,k));
    			cells.add(tmpCell);
    		}	
    	}
    	// Beállítjuk a szomszédokat
    	for (Cell cellItem : cells) {
    		cellItem.setNeighbors(cellItem.getPoint(), cells);
    	}
    	
    	cells.get(23).setAlive(true);
    }
    
    
    // Kiszámolja milyen mennyiségű szomszédnál pusztul el a sejt
    private void calculateDeath() {
    	for (int i = 1; i <= 8; i++) {
    		deathList.add(i); // Feltöltjük a newList-et 1-8-ig
    	}
    	deathList.removeAll(survive);
    }
    
  
	// Végig megy a listán és az aktuális szabály alapján beállítja a cellákat
	private void setBornAndDeath() {
		
		for (Cell cellItem : cells) {
			int neighbors = 0;
			for (Cell cellNeighbors : cellItem.getNeighbors()) {
				if (cellNeighbors.isAlive()) neighbors++;
			}
			
			if (born.contains(neighbors)) cellItem.setNextAlive(true);
			else if (deathList.contains(neighbors)) cellItem.setNextAlive(false);
		}
	}
	

}
