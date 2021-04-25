package ui;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
