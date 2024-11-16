package sejtautomata;

import java.io.*;
import java.util.ArrayList;


public class FileHandler {
	private static final String FILE_EXTENSION = ".ser";
    
    // Játékállás mentése fájlba (mindig tömörítve)
    public static void saveGameState(String fileName, Game game) {
    	
        // Fájlnév ellenőrzése és korrigálása
        if (!fileName.endsWith(FILE_EXTENSION)) fileName += FILE_EXTENSION;
        
        // Kiirjuk fájlba az adatokat
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))){
        	oos.writeObject(game);
        	System.out.println("Adatok sikeresen mentve.");
        } 
        catch (Exception e) {
        	System.err.println("Hiba történt az adatok mentése közben: " + e.getMessage());
		}
    }
    
    
    public static Game loadGameState(String fileName) throws ClassNotFoundException {
    	// Fájlnév ellenőrzése és korrigálása
    	if (!fileName.endsWith(FILE_EXTENSION)) fileName += FILE_EXTENSION; 
    	
    	// Beolvassuk fájlból az adatokat
    	try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))){
    		Game newGame = (Game) ois.readObject();
    		System.out.println("Adatok sikeresen betöltve.");
    		return newGame;
    	} 
    	catch (IOException e) {
            System.err.println("Hiba történt az adatok betöltése közben: " + e.getMessage());
        }
		return null;
    }
}