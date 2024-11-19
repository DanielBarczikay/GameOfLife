package sejtautomata;

import javax.swing.JFrame;


public class Main {
	
	public static void main(String[] args) {
		// Tömb méretének beállítása
		int rows = 400;
		int cols = 225;
		int cellSize = 5;
		
		Board gamePanel = new Board(rows, cols, cellSize);
		Game game = new Game();
		game.setBoard(gamePanel);
		
		
		JFrame frame = new JFrame("GameOfLife");
		int absoluteRows = gamePanel.getCellSize() * rows;
		int absoluteCols = gamePanel.getCellSize() * cols;
		frame.setSize(absoluteRows, absoluteCols);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Menu menu = new Menu(game, frame);
		
		frame.setJMenuBar(menu.makeMenu());
        frame.setResizable(false); // Az ablak méretének fixálása
		frame.add(gamePanel);
		frame.setVisible(true);
	}

}

