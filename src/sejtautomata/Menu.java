package sejtautomata;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class Menu {
	private final JFrame frame;
	private final Game game;

	
	public Menu(Game game, JFrame frame) {
		this.frame = frame;
		this.game = game;
	}
	
	
	// Menü beállítása
	public JMenuBar makeMenu() {
		JMenuBar menuBar = new JMenuBar();
		ActionListenerFunctions alf = new ActionListenerFunctions(game, frame);
		
		// Főmenü létrehozása
		JMenu fileMenu = new JMenu("File");
		JMenu gameMenu = new JMenu("Game");
		JMenu settingsMenu = new JMenu("Settings");
		menuBar.add(fileMenu);
		menuBar.add(gameMenu);
		menuBar.add(settingsMenu);
		
		// Almenü létrehozása
		JMenuItem importItem = new JMenuItem("Import");
		JMenuItem exportItem = new JMenuItem("Export"); 
		JMenuItem exitItem = new JMenuItem("Exit");
		JMenuItem startItem = new JMenuItem("Start"); 
		JMenuItem stopItem = new JMenuItem("Stop"); 
		JMenuItem resetItem = new JMenuItem("Reset"); 
		JMenuItem restartItem = new JMenuItem("Restart"); 
		JMenuItem bsItem = new JMenuItem("Born/Survive"); 
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
		
		// Listenerek az almenükhöz
		importItem.addActionListener(e -> alf.importItem());
		exportItem.addActionListener(e -> alf.exportItem());
		exitItem.addActionListener(e -> {System.exit(0);});
		startItem.addActionListener(e -> {try {
			game.startGame();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}});
		stopItem.addActionListener(e -> {game.stopGame();});
		resetItem.addActionListener(e -> {game.resetGame();});
		restartItem.addActionListener(e -> {game.restartGame();});
		bsItem.addActionListener(e -> alf.bsItem());
		speedItem.addActionListener(e -> alf.speedItem());

		return menuBar;
	}
}
