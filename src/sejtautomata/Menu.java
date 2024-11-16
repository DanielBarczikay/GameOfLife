package sejtautomata;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Menu {
	public Menu(JFrame frame, Game game) {
		
		// Create the menu bar and add it to the frame
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		
		// Create the individual menus and add them to the menu bar
		JMenu fileMenu = new JMenu("File");
		JMenu gameMenu = new JMenu("Game");
		JMenu settingsMenu = new JMenu("Settings");
		
		menuBar.add(fileMenu);
		menuBar.add(gameMenu);
		menuBar.add(settingsMenu);
		
		// Add menu items to the fileMenu
		JMenuItem importItem = new JMenuItem("Import");
		JMenuItem exportItem = new JMenuItem("Export");
		JMenuItem exitItem = new JMenuItem("Exit");
		
		importItem.addActionListener(e -> {
	
			// Létrehozunk egy új JFileChooser-t
		    JFileChooser fileChooser = new JFileChooser();
		    
		    // Beállítjuk hogy csak .ser kiterjesztésű fájlokat lásson/mentsen
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Serialized files (*.ser)", "ser");
		    fileChooser.setFileFilter(filter);
		    
		    // Beállítjuk a dialog címét
		    fileChooser.setDialogTitle("Import Game");
		    
		    // Megjelenítjük a mentés ablakot és elmentjük a választott opciót
		    int userSelection = fileChooser.showSaveDialog(frame);
		    
		    // Ha a felhasználó a "Save" gombra kattintott
		    if (userSelection == JFileChooser.APPROVE_OPTION) {
		        // Megkapjuk a kiválasztott fájlt
		        File fileToSave = fileChooser.getSelectedFile();
		        String fileName = fileToSave.getAbsolutePath();
		        
		        if (fileName.endsWith(".ser")) fileName += ".ser";
		        
		        // Meghívjuk a mentés függvényt a fájlnévvel
		        FileHandler.saveGameState(fileName, game);
		    }
		    
			
		});
		
		exportItem.addActionListener(e -> { 
		   JFileChooser fileChooser = new JFileChooser();
		    
		    // Beállítjuk hogy csak .ser kiterjesztésű fájlokat lásson
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Serialized files (*.ser)", "ser");
		    fileChooser.setFileFilter(filter);
		    
		    // Megjelenítjük a Windows-os megnyitás ablakot
		    int userSelection = fileChooser.showOpenDialog(frame);
		    
		    if (userSelection == JFileChooser.APPROVE_OPTION) {
		        File fileName = fileChooser.getSelectedFile();
		        try {
					FileHandler.loadGameState(fileName.getAbsolutePath());
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
		    }
		});
		
		fileMenu.add(importItem);
		fileMenu.add(exportItem);
		fileMenu.add(exitItem);
		
		// Add menu items to the gameMenu
		JMenuItem startItem = new JMenuItem("Start");
		JMenuItem stopItem = new JMenuItem("Stop");
		JMenuItem resetItem = new JMenuItem("Reset");
		JMenuItem restartItem = new JMenuItem("Restart");
		
		/*
		startItem.addActionListener(e -> {startGame();});
		stopItem.addActionListener(e -> {stopGame();});
		resetItem.addActionListener(e -> {resetGame();});
		*/
		gameMenu.add(startItem);
		gameMenu.add(stopItem);
		gameMenu.add(resetItem);
		gameMenu.add(restartItem);
		
		// Add menu items to the settingsMenu
		JMenuItem bsItem = new JMenuItem("Born/Survive");
		JMenuItem speedItem = new JMenuItem("Speed");
		
		settingsMenu.add(bsItem);
		settingsMenu.add(speedItem);
		
	
	}
}
