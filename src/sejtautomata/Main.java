package sejtautomata;
import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
		// Ne emeld 200 fölé!!
		int rows = 180;
		int cols = 100;
		
		Board gamePanel = new Board(rows, cols);
		Game game = new Game(); // 10x10 tábla
		
		
		JFrame frame = new JFrame("GameOfLife");
		frame.setSize(gamePanel.getCellSize() * rows, gamePanel.getCellSize() * cols);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Menu menu = new Menu(frame, game);
		
        frame.setResizable(false); // Az ablak méretének fixálása
		frame.add(gamePanel);
		frame.setVisible(true);
	}
}

