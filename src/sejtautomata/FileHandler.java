package sejtautomata;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class FileHandler {
	private static final String FILE_EXTENSION = ".ser";
    
    // Játékállás mentése fájlba (mindig tömörítve)
    public static void saveGameState(String fileName, Board board) {
    	
        // Fájlnév ellenőrzése és korrigálása
        if (!fileName.endsWith(FILE_EXTENSION)) fileName += FILE_EXTENSION;
        
        // Kiirjuk fájlba az adatokat
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))){
        	oos.writeObject(board);
        } 
        catch (IOException e) {
        	System.err.println("Hiba történt az adatok mentése közben: " + e.getMessage());
		}
    }
    
    
    public static Board loadGameState(String fileName) throws ClassNotFoundException {
    	// Fájlnév ellenőrzése és korrigálása
    	if (!fileName.endsWith(FILE_EXTENSION)) fileName += FILE_EXTENSION; 
    	
    	// Beolvassuk fájlból az adatokat
    	try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))){
    		Board newBoard = (Board) ois.readObject();
    		return newBoard;
    	} 
    	catch (IOException e) {
            System.err.println("Hiba történt az adatok betöltése közben: " + e.getMessage());
        }
		return null;
    }
}