package sejtautomata;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class GameOfLifeTest {
	private Game game;
	private Board board;
	
	@BeforeEach
	public void setUp() throws Exception {
		game = new Game();
	    board = new Board(3, 3, 5); // 3x3-as pálya, 5-ös cellaméret
	    game.setBoard(board);

	    // Kezdeti állapot beállítása
	    board.getCells()[1][1].setAlive(true);
	    board.getCells()[1][1].setNextAlive(true); // Középső cella él
	    board.getCells()[0][1].setAlive(true);
	    board.getCells()[0][1].setNextAlive(true); // Egy szomszéd él
	    board.getCells()[2][1].setAlive(true);
	    board.getCells()[2][1].setNextAlive(true); // Egy másik szomszéd él
	}
	
	
	@Test
    public void testDefaultRules() {
        assertEquals(new ArrayList<>(List.of(0, 1, 4, 5, 6, 7, 8)), game.getDeathList());
    }

	
	@Test
	public void testSpeed() {
		String str = "123";
		game.setSpeed(str);
		assertEquals(123, game.getSpeed());
	}
	
	
	@Test
	public void testDeathList() {
		game.setSurvive("a1234,567");
		ArrayList<Integer> lista = new ArrayList<>();
		lista.add(0);
		lista.add(8);
		assertEquals(lista, game.getDeathList());
	}
	
	
	@Test
	public void testRefreshCells() {
	    game.refreshCells();
	    assertTrue(board.getCells()[1][1].getNextAlive(), "A középső cellának életben kell maradnia.");
	    assertTrue(board.getCells()[1][0].getNextAlive(), "A bal szomszédnak életben kell maradnia.");
	    assertFalse(board.getCells()[0][0].getNextAlive(), "A bal felső szomdszédnak halottnak kell lennie.");
	}
	
	
	@Test
	public void testGameFlow() throws InterruptedException {
	    game.startGame();
	    game.startGame(); // Ha fut akkor nem indít újat
	    // Várakozás egy ciklus lefutásáig
	    Thread.sleep(60);

	    assertFalse(board.getCells()[2][2].isAlive(), "A cellának halottnak kell lennie.");
	    assertFalse(board.getCells()[0][0].isAlive(), "A cellának halottnak kell lennie.");
	    assertTrue(board.getCells()[1][1].isAlive(), "A középső cellának életben kell maradnia.");

	    game.stopGame(); 
	}
	
	
	@Test 
	public void testResetGame(){
		game.resetGame();
		for (int i = 0; i < board.getRows(); i++){
			for (int k = 0; k < board.getCols(); k++){
				assertFalse(board.getCells()[i][k].isAlive());				
			}
		}
	}
	
	
	@Test
	public void testRestartGame() throws InterruptedException {
		game.startGame();
		game.restartGame();
		assertTrue(board.getCells()[1][1].isAlive());
		assertTrue(board.getCells()[0][1].isAlive());
		assertTrue(board.getCells()[2][1].isAlive());
		assertFalse(board.getCells()[0][0].isAlive());
		assertFalse(board.getCells()[1][2].isAlive());
		assertFalse(board.getCells()[1][0].isAlive());
	}
	
	
	@Test
	public void testSaveAndLoadGameState() throws ClassNotFoundException {
	    
	    // Ideiglenes fájl neve
        String tempFileName = "tempFile";
        FileHandler.saveGameState(tempFileName, board);
        
        // Betöltés
        Board loadedBoard = FileHandler.loadGameState(tempFileName);
	    // Ellenőrzés
        assertNotNull(loadedBoard, "A betöltött tábla nem lehet null.");
        assertEquals(board.getRows(), loadedBoard.getRows(), "A sorok száma nem egyezik.");
        assertEquals(board.getCols(), loadedBoard.getCols(), "Az oszlopok száma nem egyezik.");
        assertEquals(board.getCells()[1][1].isAlive(), loadedBoard.getCells()[1][1].isAlive(),
                "A cellák állapota nem egyezik.");
        assertEquals(board.getCells()[0][1].isAlive(), loadedBoard.getCells()[0][1].isAlive(),
                "A cellák állapota nem egyezik.");
        assertEquals(board.getCells()[0][0].isAlive(), loadedBoard.getCells()[0][0].isAlive(),
                "A cellák állapota nem egyezik.");
        
	    // Ideiglenes fájl törlése
        File tempFile = new File(tempFileName + ".ser");
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }
	 
	
	@Test
	public void testFadeCounter() {
		board.getCells()[1][1].setAlive(false);
		assertEquals(3, board.getCells()[1][1].getFade());
	}
	
	
	@Test
	public void testDecreaseFadeCounter() {
		board.getCells()[1][1].setAlive(false);
		board.getCells()[1][1].decreaseFade();
		assertEquals(2, board.getCells()[1][1].getFade());
	}
}
