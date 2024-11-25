package sejtautomata;

import javax.swing.JFrame;


public class Main {
	
	public static void main(String[] args) {
		// Tömb és Cella méret beállítása
		int rows = 400;
		int cols = 225;
		int cellSize = 5;
		
		Board board = new Board(rows, cols, cellSize);
		Game game = new Game();
		game.setBoard(board);
		
		
		JFrame frame = new JFrame("GameOfLife");
		int absoluteRows = board.getCellSize() * rows;
		int absoluteCols = board.getCellSize() * cols;
		frame.setSize(absoluteRows, absoluteCols);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Menu menu = new Menu(game, frame);
		
		frame.setJMenuBar(menu.makeMenu());
        frame.setResizable(false); // Az ablak méretének fixálása
		frame.add(board);
		frame.setVisible(true);
	}
}
