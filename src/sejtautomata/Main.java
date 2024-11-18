package sejtautomata;

import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
		// Ne emeld 200 fölé!!
		int rows = 180;
		int cols = 100;
		int cellSize = 7;
		
		Board gamePanel = new Board(rows, cols, cellSize);
		Game game = new Game(gamePanel); // 10x10 tábla
		
		
		JFrame frame = new JFrame("GameOfLife");
		int absoluteRows = gamePanel.getCellSize() * rows;
		int absoluteCols = gamePanel.getCellSize() * cols;
		frame.setSize(absoluteRows, absoluteCols);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Menu menu = new Menu(frame, game);
		
        frame.setResizable(false); // Az ablak méretének fixálása
		frame.add(gamePanel);
		frame.setVisible(true);
	}
}

