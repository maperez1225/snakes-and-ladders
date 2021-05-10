package ui;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
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
			showMenu();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Excepcion al leer los datos de los puntajes.");
		}
	}
	public static void showMenu(){
		System.out.println("Ingrese la opcion que desea realizar: "
				+ "\n1. Nuevo Juego"
				+ "\n2. Ver Puntajes"
				+ "\n3. Salir");
		int option = sc.nextInt();
		sc.nextLine();
		switch(option) {
		case 1:
			newGame();
			System.out.println("Presione ENTER para regresar al menu.");
			sc.nextLine();
			showMenu();
			break;
		case 2:
			showLeaderboard();
			System.out.println("Presione ENTER para regresar al menu.");
			sc.nextLine();
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
	public static void newGame(){
		System.out.println("Ingrese los criterios para crear el juego separado por espacio: filas, columnas, serpientes, escaleras, simbolos de jugadores todos juntos");
		String[] gameArgs = sc.nextLine().split(" ");
		if (gameArgs.length == 5) {
			try {
				game = new Game(Integer.parseInt(gameArgs[0]),Integer.parseInt(gameArgs[1]),Integer.parseInt(gameArgs[2]),Integer.parseInt(gameArgs[3]),gameArgs[4]);
				System.out.println("El tablero se ha generado:");
				System.out.println(game.board());
				System.out.println("Presione ENTER para comenzar el juego");
				sc.nextLine();
				nextTurn();
			} catch (StackOverflowError e) {
				System.out.println("Error de generacion de juego. Intente generar el juego con menos escaleras o serpientes, o generar un tablero mas grande.");
				newGame();
			}
		}
		else {
			System.out.println("Formato de criterios invalido.");
			newGame();
		}
	}
	public static void nextTurn(){
		System.out.println("Es el turno de "+game.getActivePlayer().getSymbol());
		System.out.println("Turno: "+game.getMoves());
		System.out.println(game.status());
		System.out.println("Presione ENTER para lanzar el dado");
		executeAction(sc.nextLine());
	}
	public static void executeAction(String line){
		if (line.equalsIgnoreCase("num")) {
			System.out.println("Vista inicial del tablero:");
			System.out.println(game.board());
			System.out.println("Presione ENTER para continuar con la partida");
			sc.nextLine();
			nextTurn();
		}else if(line.equalsIgnoreCase("simul")) {
			Random r = new Random();
			int roll = r.ints(1,7).findFirst().getAsInt();
			System.out.println("Se ha lanzado un "+roll);
			game.moveActivePlayer(roll);
			if (game.getActivePlayer().getBox() == game.getFinalBox())
				endGame();
			else {
				try {
					TimeUnit.SECONDS.sleep(1);
					game.updateActivePlayer();
					System.out.println("\nEs el turno de "+game.getActivePlayer().getSymbol());
					System.out.println("Turno: "+game.getMoves());
					System.out.println(game.status());
					TimeUnit.SECONDS.sleep(1);
					executeAction("simul");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}else if (line.equalsIgnoreCase("menu"))
			System.out.println("Partida cancelada");
		else {
			Random r = new Random();
			int roll = r.ints(1,7).findFirst().getAsInt();
			System.out.println("Se ha lanzado un "+roll);
			game.moveActivePlayer(roll);
			if (game.getActivePlayer().getBox() == game.getFinalBox())
				endGame();
			else {
				game.updateActivePlayer();
				nextTurn();
			}
		}
	}
	public static void endGame() {
		System.out.println("El jugador "+game.getActivePlayer().getSymbol()+" ha ganado en "+game.getMoves()+" turnos.");
		System.out.println(game.status());
		int score = game.getMoves()*game.getFinalBox();
		System.out.println("Puntaje: "+score);
		System.out.println("Ingrese nombre del jugador");
		String name=sc.nextLine();
		try {
			Score newScore = new Score(score,name);
			addScore(newScore);
			saveData();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el puntaje");
		}
	}
	public static void addScore(Score newScore) {
		if (root == null)
			root = newScore;
		else
			addScore(root, newScore);
	}
	public static void addScore(Score parent, Score newScore){
		if (newScore.getScore() > parent.getScore()) {
			if (parent.getRight() == null)
				parent.setRight(newScore);
			else
				addScore(parent.getRight(), newScore);
		}else {
			if (parent.getLeft() == null)
				parent.setLeft(newScore);
			else
				addScore(parent.getLeft(), newScore);
		}
	}
	public static void showLeaderboard() {
		if(root==null)
			System.out.println("No hay puntajes");
		else
			showLeaderboard(root);
	}
	public static void showLeaderboard(Score r) {
		if (r.getRight() != null)
			showLeaderboard(r.getRight());
		System.out.println(r);
		System.out.println();
		if (r.getLeft() != null)
			showLeaderboard(r.getLeft());
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