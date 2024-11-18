package sejtautomata;

import java.awt.GridLayout;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
		
		// Add menu items
		JMenuItem importItem = new JMenuItem("Import"); // Ready
		JMenuItem exportItem = new JMenuItem("Export"); // Ready
		JMenuItem exitItem = new JMenuItem("Exit"); //
		JMenuItem startItem = new JMenuItem("Start"); // Ready
		JMenuItem stopItem = new JMenuItem("Stop");
		JMenuItem resetItem = new JMenuItem("Reset"); // Ready
		JMenuItem restartItem = new JMenuItem("Restart"); // Ready
		JMenuItem bsItem = new JMenuItem("Born/Survive"); // Ready
		JMenuItem speedItem = new JMenuItem("Speed");
		
		fileMenu.add(importItem);
		fileMenu.add(exportItem);
		fileMenu.add(exitItem);
		gameMenu.add(startItem);
		gameMenu.add(stopItem);
		gameMenu.add(resetItem);
		gameMenu.add(restartItem);
		settingsMenu.add(bsItem);
		settingsMenu.add(speedItem);
		
		
		importItem.addActionListener(e -> {
			// Létrehozunk egy új JFileChooser-t
		    JFileChooser fileChooser = new JFileChooser();
		    
		    // Beállítjuk hogy csak .ser kiterjesztésű fájlokat lásson/mentsen
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Serialized files (*.ser)", "ser");
		    fileChooser.setFileFilter(filter);
		    
		    // Beállítjuk az ablak címét
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
		    
		    // Megjelenítjük az ablakot
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
		
		exitItem.addActionListener(e -> {System.exit(0);});
		startItem.addActionListener(e -> {try {
			game.startGame();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}});
		stopItem.addActionListener(e -> {game.stopGame();});
		restartItem.addActionListener(e -> {game.restartGame();});
		resetItem.addActionListener(e -> {game.resetGame();});
		
		
		
		bsItem.addActionListener(e -> {
		    // Létrehozunk egy JPanel-t a beviteli mezőknek
		    JPanel panel = new JPanel();
		    panel.setLayout(new GridLayout(2, 2, 5, 5));  // 2x2 grid, 5px spacing

		    // Létrehozzuk a beviteli mezőket
		    JTextField bornField = new JTextField(10);
		    JTextField surviveField = new JTextField(10);

		    // Hozzáadjuk a címkéket és mezőket a panelhez
		    panel.add(new JLabel("Born:"));
		    panel.add(bornField);
		    panel.add(new JLabel("Survive:"));
		    panel.add(surviveField);

		    // Megjelenítjük a párbeszédablakot
		    int result = JOptionPane.showConfirmDialog(
		        frame, 
		        panel, 
		        "Born/Survive Settings", 
		        JOptionPane.OK_CANCEL_OPTION,
		        JOptionPane.PLAIN_MESSAGE
		    );

		    // Ha az OK gombra kattintott a felhasználó
		    if (result == JOptionPane.OK_OPTION) {
		        String bornStr = bornField.getText();
		        String surviveStr = surviveField.getText();

		        // Beállítjuk az új értékeket a játékban
		        game.setBorn(bornStr);
		        game.setSurvive(surviveStr);
		    }
		});

		
	
	}
}
