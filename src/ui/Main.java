package ui;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Scanner;
import model.Game;
import model.Score;
public class Main {
	private static final String SAVE_PATH_FILE = "data/scores.ap2";
	public static Scanner sc;
	public static Game game;
	public static Score root;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		try {
			loadData();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		showMenu();
	}
	
	public static void showMenu() {
		System.out.println("Ingrese la opcion que desea realizar: "
				+ "\n1. Nuevo Juego"
				+ "\n2. Ver Resultados"
				+ "\n3. Salir");
		int option = sc.nextInt();
		sc.nextLine();
		switch(option) {
		case 1:
			newGame();
			break;
		case 2:
			showLeaderboard(root);
			showMenu();
			break;
		case 3:
			System.out.println("Fin de la ejecucion");
			break;
		default:
			System.out.println("Opcion invalida");
			showMenu();
		}
	}
	
	public static void newGame() {
		System.out.println("Ingrese los criterios para crear el juego separado por espacio: filas, columnas, serpientes, escaleras, simbolos de jugadores todos juntos");
		String[] gameArgs = sc.nextLine().split(" ");
		if (gameArgs.length == 5) {
			game = new Game(Integer.parseInt(gameArgs[0]),Integer.parseInt(gameArgs[1]),Integer.parseInt(gameArgs[2]),Integer.parseInt(gameArgs[3]),gameArgs[4]);
			System.out.println("El tablero se ha generado:");
			System.out.println(game.board());
			System.out.println();
			nextTurn();
		}
		else {
			System.out.println("Formato de criterios invalido.");
			newGame();
		}
	}
	
	public static void nextTurn() {
		System.out.println("Es el turno de "+game.getActivePlayer().getSymbol());
		System.out.println(game.status());
		System.out.println("Presione ENTER para lanzar el dado");
		executeAction(sc.nextLine());
	}

	public static void executeAction(String line) {
		if (line.equalsIgnoreCase("num")) {
			System.out.println("Vista inicial del tablero:");
			System.out.println(game.board());
			System.out.println("Presione ENTER para continuar con la partida");
			sc.nextLine();
			nextTurn();
		}else if(line.equalsIgnoreCase("simul")) {
			
		}else if (line.equalsIgnoreCase("menu"))
			showMenu();
		else {
			Random r = new Random();
			int roll = r.ints(1,6).findFirst().getAsInt();
			System.out.println("Se ha lanzado un "+roll);
			game.moveActivePlayer(roll);
			if (game.getActivePlayer().getBox() >= game.getFinalBox())
				endGame();
			else {
				game.updateActivePlayer();
				nextTurn();
			}
		}
	}

	public static void endGame() {
		System.out.println("El jugador "+game.getActivePlayer().getSymbol()+" ha ganado en "+game.getMoves()+" turnos.");
	}

	public static void showLeaderboard(Score highScore) {
		if (root == null)
			System.out.println("No hay puntajes");
		else if (highScore.getRight() == null) {
			System.out.println(highScore);
			if (highScore.getLeft() != null)
				showLeaderboard(highScore.getLeft());
		}else {
			showLeaderboard(highScore.getRight());
		}
	}
	
	public static void addScore(Score parent, Score newScore) throws IOException{
		if (parent == null) {
			parent = newScore;
			saveData();
		}else if (newScore.getScore() > parent.getScore())
			addScore(parent.getRight(), newScore);
		else
			addScore(parent.getLeft(), newScore);
	}
	
	public static void saveData() throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE));
		oos.writeObject(root);
		oos.close();
	}
	
	public static boolean loadData() throws IOException, ClassNotFoundException{
		File f = new File(SAVE_PATH_FILE);
		boolean loaded = false;
		if (f.exists()) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			root = (Score)ois.readObject();
			ois.close();
			loaded = true;
		}
		return loaded;
	}
}
