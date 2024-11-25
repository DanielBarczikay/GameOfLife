package sejtautomata;

import java.awt.GridLayout;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ActionListenerFunctions {
	private static String bornStr;
	private static String surviveStr;
	private JFrame frame;
	private Game game;

	
	public ActionListenerFunctions(Game game, JFrame frame) {
		this.frame = frame;
		this.game = game;
	}
	
	
	public void importItem() {
		JFileChooser fileChooser = new JFileChooser();
	    
	    // Beállítjuk hogy csak .ser kiterjesztésű fájlokat lásson
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Serialized files (*.ser)", "ser");
	    fileChooser.setFileFilter(filter);
	    
	    // Beállítjuk az ablak címét
	    fileChooser.setDialogTitle("Import Game");
	    
	    // Megjelenítjük az ablakot
	    int userSelection = fileChooser.showOpenDialog(frame);
	    
	    if (userSelection == JFileChooser.APPROVE_OPTION) {
	        File fileName = fileChooser.getSelectedFile(); // Megkapjuk a kiválasztott fájlt
	        try {
	        	game.stopGame(); // Leállítjuk a futó játékot
				Board newBoard = FileHandler.loadGameState(fileName.getAbsolutePath()); // Létrehozzuk az új Board-ot
				SwingUtilities.invokeLater(() -> {
                    game.setBoard(newBoard); // Beállítjuk az új Board-ot
                    newBoard.setupMouseListener();
                    frame.getContentPane().removeAll(); // Eltávolítunk mindent a frame-ből
                    frame.add(newBoard);
                    frame.revalidate();
                    frame.repaint();
                });
				
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
	    }
	}
	
	
	public void exportItem() {
	    JFileChooser fileChooser = new JFileChooser();
	    
	    // Beállítjuk hogy csak .ser kiterjesztésű fájlokat lásson
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Serialized files (*.ser)", "ser");
	    fileChooser.setFileFilter(filter);
	    
	    // Beállítjuk az ablak címét
	    fileChooser.setDialogTitle("Export Game");
	    
	    // Megjelenítjük a mentés ablakot
	    int userSelection = fileChooser.showSaveDialog(frame);
	    
	    // Ha a felhasználó a Save gombra kattintott
	    if (userSelection == JFileChooser.APPROVE_OPTION) {

	        File fileToSave = fileChooser.getSelectedFile();
	        String fileName = fileToSave.getAbsolutePath();
	        
	        if (fileName.endsWith(".ser")) fileName += ".ser";
	        
	        // Elmentjük a játékot
	        FileHandler.saveGameState(fileName, game.getBoard());
	    }
	}
	
	
	public void bsItem() {
		// Létrehozunk egy JPanel-t a beviteli mezőknek
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(3, 2, 5, 5));  // 3x2 grid

	    // Létrehozzuk a legörgethető menüsort
	    String[] options = {"Classic", "Day and Night", "HighLife", "Seeds", 
	    		"Life without Death", "Gnarl", "Replicator", "Anneal", "Serviettes", "Maze", "Cave"};
	    JComboBox<String> comboBox = new JComboBox<>(options);
	    
	    // Létrehozzuk a beviteli mezőket
	    JTextField bornField = new JTextField(10);
	    JTextField surviveField = new JTextField(10);
	    
	    // Hozzáadjuk a címkéket és mezőket a panelhez
	    panel.add(comboBox);
	    panel.add(new JLabel(""));
	    panel.add(new JLabel("Born:"));
	    panel.add(bornField);
	    panel.add(new JLabel("Survive:"));
	    panel.add(surviveField);

	    comboBox.addActionListener(e -> comboBoxItems(comboBox));

	    // Megjelenítjük a párbeszédablakot
	    int result = JOptionPane.showConfirmDialog(frame, panel, "Settings", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

	    // Ha az OK gombra kattintott a felhasználó
	    if (result == JOptionPane.OK_OPTION) {
	    	// Ha megadott a felhasználó valamilyen értéket a mezőkben akkor az lesz a végleges
	    	if (!bornField.getText().isBlank()) {
	    		bornStr = bornField.getText();
		    	surviveStr = surviveField.getText();
	    	}
	    	
	        // Beállítjuk az új értékeket a játékban
	        game.setBorn(bornStr);
	        game.setSurvive(surviveStr);
	    }
	}
	
	
	public void speedItem() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Függőleges elrendezés

		JTextField speedField = new JTextField(10);
		panel.add(speedField);

		// Megjelenítjük a párbeszédablakot
		int result = JOptionPane.showConfirmDialog(frame, panel, "Speed", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		
		// Ha az OK gombra kattintott a felhasználó
	    if (result == JOptionPane.OK_OPTION) {
	    	String input = speedField.getText();
	    	// Ellenőrizzük, hogy az input egy érvényes egész szám
	        if (input.matches("\\d+")) { 
	            game.setSpeed(input);
	        } 
	        else {
	            // Ha nem szám, figyelmeztetjük a felhasználót
	            JOptionPane.showMessageDialog(frame, "Please enter a valid integer value for speed.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
	
	private void comboBoxItems(JComboBox<String> comboBox) {
		String selectedOption = (String) comboBox.getSelectedItem();
		// Választható opciók
        switch (selectedOption) {
            case "Classic":
            	bornStr = "3";
            	surviveStr = "23";
                break;
            case "Day and Night":
            	bornStr = "3678";
            	surviveStr = "34678";
                break;
            case "HighLife":
            	bornStr = "36";
            	surviveStr = "23";
                break;
            case "Seeds":
            	bornStr = "2";
            	surviveStr = "";
                break;
            case "Life without Death":
            	bornStr = "3";
            	surviveStr = "012345678";
                break;
            case "Gnarl":
            	bornStr = "1";
            	surviveStr = "1";
                break;
            case "Replicator":
            	bornStr = "1357";
            	surviveStr = "1357";
                break;
            case "Anneal":
            	bornStr = "4678";
            	surviveStr = "35678";
                break;
            case "Serviettes":
            	bornStr = "234";
            	surviveStr = "";
                break;
            case "Maze":
            	bornStr = "3";
            	surviveStr = "12345";
                break;
            case "Cave":
            	bornStr = "678";
            	surviveStr = "345678";
                break;
            default:
                System.out.println("Nincs ilyen opció.");
                break;
        }
	}
}
