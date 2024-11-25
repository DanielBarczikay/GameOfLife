package sejtautomata;

import java.io.Serializable;


public class Cell implements Serializable {
    private static final long serialVersionUID = 1L;
	private boolean isAlive = false;
	private boolean nextIsAlive = false;
	private int fadeCounter = 0; // Halványulási szint (0 = teljesen halott, 3 = éppen most halt meg)

	// Getter
	public boolean isAlive() {
		return isAlive;
	}

	public boolean getNextAlive() {
		return nextIsAlive;
	}
    
	public int getFade() {
	    return fadeCounter;
	}
	
	
	// Setter
	public void setAlive(boolean alive) {
	    this.isAlive = alive;
	    if (!alive) 
	        fadeCounter = 3; // Ha meghal, elindul a fade folyamat
	}
	
	public void setNextAlive(boolean alive) {
		nextIsAlive = alive;
	}
	
	public void setFade(int fade) {
		fadeCounter = fade;
	}
	
	
	// Csökkenti a fade számlálót
	public void decreaseFade() {
	    if (fadeCounter > 0) fadeCounter--; 
	}
}