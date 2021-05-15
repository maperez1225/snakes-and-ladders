package model;
import java.util.Random;
public class Game {
	private int rows;
	private int cols;
	private int snakes;
	private int ladders;
	private int moves;
	private String playerCharacters;
	private Player firstPlayer;
	private Player activePlayer;
	private Box firstBox;
	private Snake firstSnake;
	private Ladder firstLadder;
	public Game(int m, int n, int s, int l, String players) {
		rows = m;
		cols = n;
		snakes = s;
		ladders = l;
		moves = 1;
		playerCharacters = players;
		generateBoard();
		generatePlayers(players);
		generateSnakes(snakes);
		generateLadders(ladders);
		assignSnakeLetters('A', cols+1);
		assignLadderNumbers(1,2);
	}
	private void generateSnakes(int s) {
		firstSnake = new Snake();
		assignStart(firstSnake);
		assignEnd(firstSnake);
		if (s > 1)
			generateSnakes(firstSnake, s-1);
	}
	private void generateSnakes(Snake prev, int s) {
		Snake next = new Snake();
		prev.setNextSnake(next);
		assignStart(next);
		assignEnd(next);
		if (s > 1)
			generateSnakes(next,s-1);
	}
	private void assignStart(Snake snake) {
		Random r = new Random();
		int start = r.ints(cols+1,rows*cols).findFirst().getAsInt();
		if (snakeAtBox(firstSnake, start).equals(" ")) {
			snake.setBeginning(start);
		}else
			assignStart(snake);
	}
	private void assignEnd(Snake snake) {
		Random r = new Random();
		int end = r.ints(1,snakeEndLimit(snake.getBeginning(),(rows*cols))).findFirst().getAsInt();
		if (snakeAtBox(firstSnake, end).equals(" ")) {
			snake.setEnd(end);
		}else
			assignEnd(snake);
	}
	private int snakeEndLimit(int start, int maxRow) {
		if (start > maxRow-rows)
			return maxRow-rows;
		else
			return snakeEndLimit(start, maxRow-rows);
	}
	private void assignSnakeLetters(char c, int box) {
		if (box<rows*cols) {
			if (snakeStartAtBox(firstSnake, box)) {
				searchSnake(firstSnake,box).setId(c);
				assignSnakeLetters((char)(c+1),box+1);
			}else {
				assignSnakeLetters(c,box+1);
			}
		}
	}
	private Snake searchSnake(Snake snake, int start) {
		if (snake.getBeginning() == start)
			return snake;
		else
			return searchSnake(snake.getNextSnake(), start);
	}
	private boolean snakeStartAtBox(Snake snake, int box) {
		if (snake.getNextSnake() == null) {
			if (snake.getBeginning() == box)
				return true;
			else
				return false;
		}else {
			if (snake.getBeginning() == box)
				return true;
			else
				return snakeStartAtBox(snake.getNextSnake(), box);
		}
	}
	private void generateLadders(int l) {
		firstLadder = new Ladder();
		assignStart(firstLadder);
		assignEnd(firstLadder);
		if (l > 1)
			generateLadders(firstLadder, l-1);
	}
	private void generateLadders(Ladder prev, int l) {
		Ladder next = new Ladder();
		prev.setNextLadder(next);
		assignStart(next);
		assignEnd(next);
		if (l > 1)
			generateLadders(next,l-1);
	}
	private void assignStart(Ladder ladder) {
		Random r = new Random();
		int start = r.ints(2,(rows*cols)-cols).findFirst().getAsInt();
		if (snakeAtBox(firstSnake, start).equals(" ") && ladderAtBox(firstLadder, start).equals(" ")) {
			ladder.setBeginning(start);
		}else
			assignStart(ladder);
	}
	private void assignEnd(Ladder ladder) {
		Random r = new Random();
		int end = r.ints(ladderEndLimit(ladder.getBeginning(),rows*cols),rows*cols).findFirst().getAsInt();
		if (snakeAtBox(firstSnake, end).equals(" ") && ladderAtBox(firstLadder, end).equals(" ")) {
			ladder.setEnd(end);
		}else
			assignEnd(ladder);
	}
	private int ladderEndLimit(int start, int maxRow) {
		if (start > maxRow-cols)
			return maxRow + 1;
		else
			return ladderEndLimit(start, maxRow-cols);
	}
	private void assignLadderNumbers(int n, int box) {
		if (box<=(rows*cols)-cols) {
			if (ladderStartAtBox(firstLadder, box)) {
				searchLadder(firstLadder,box).setId(n);
				assignLadderNumbers(n+1,box+1);
			}else {
				assignLadderNumbers(n,box+1);
			}
		}
	}
	private Ladder searchLadder(Ladder ladder, int start) {
		if (ladder.getBeginning() == start)
			return ladder;
		else
			return searchLadder(ladder.getNextLadder(), start);
	}
	private boolean ladderStartAtBox(Ladder ladder, int box) {
		if (ladder.getNextLadder() == null) {
			if (ladder.getBeginning() == box)
				return true;
			else
				return false;
		}else {
			if (ladder.getBeginning() == box)
				return true;
			else
				return ladderStartAtBox(ladder.getNextLadder(), box);
		}
	}
	private void generatePlayers(String players) {
		firstPlayer = new Player(players.charAt(0));
		activePlayer = firstPlayer;
		if (players.length() > 1)
			generatePlayers(firstPlayer, players.substring(1));
	}
	private void generatePlayers(Player prev, String players) {
		Player next = new Player(players.charAt(0));
		prev.setNextPlayer(next);
		if (players.length() > 1)
			generatePlayers(next, players.substring(1));
	}
	private void generateBoard() {
		firstBox = new Box(1);
		generateBoard(firstBox, 2);
	}
	private void generateBoard(Box prev, int i) {
		Box next = new Box(i);
		prev.setNext(next);
		if (i <= rows*cols) {
			generateBoard(next, i+1);
		}
	}
	public int getFinalBox() {
		return rows*cols;
	}
	public Player getActivePlayer(){
		return activePlayer;
	}
	public int getMoves() {
		return moves;
	}
	public int getRows() {
		return rows;
	}
	public int getCols() {
		return cols;
	}
	public int getSnakes() {
		return snakes;
	}
	public int getLadders() {
		return ladders;
	}
	public String getPlayerCharacters() {
		return playerCharacters;
	}
	public void updateActivePlayer() {
		if (activePlayer.getNextPlayer()!=null)
			activePlayer = activePlayer.getNextPlayer();
		else {
			activePlayer = firstPlayer;
			moves++;
		}
	}
	public void moveActivePlayer(int n) {
		int newBox = activePlayer.getBox()+n;
		if (newBox > getFinalBox())
			activePlayer.setBox(getFinalBox());
		else
			activePlayer.setBox(newBox);
		if (snakeStartAtBox(firstSnake, newBox))
			activePlayer.setBox(searchSnake(firstSnake, newBox).getEnd());
		else if (ladderStartAtBox(firstLadder, newBox))
			activePlayer.setBox(searchLadder(firstLadder,newBox).getEnd());
	}
	public Box searchBox(int i) {
		if (i == 1)
			return firstBox;
		else
			return searchBox(firstBox.getNext(), i);
	}
	public Box searchBox(Box box, int i) {
		if (box.getId() == i)
			return box;
		else
			return searchBox(box.getNext(), i);
	}
	public String board() {
		return board(rows);
	}
	public String board(int rowLeft) {
		if (rowLeft > 1) {
			if ((rowLeft % 2) == 0)
				return evenRow(rowLeft, cols)+"\n"+board(rowLeft - 1);
			else
				return oddRow(rowLeft, cols)+"\n"+board(rowLeft - 1);
		}else
			return oddRow(rowLeft, cols);
	}
	public String evenRow(int row, int colLeft) {
		Box box = searchBox((row*cols)-(cols-colLeft));
		if (colLeft>1) 
			return "["+box.idBoard()+" "+snakeAtBox(firstSnake,box.getId())+ladderAtBox(firstLadder,box.getId())+"]"+evenRow(row, colLeft-1);
		else
			return "["+box.idBoard()+" "+snakeAtBox(firstSnake,box.getId())+ladderAtBox(firstLadder,box.getId())+"]";
	}
	public String oddRow(int row, int colLeft) {
		if (colLeft>1) {
			Box box = searchBox((row*cols)-colLeft+1);
			return "["+box.idBoard()+" "+snakeAtBox(firstSnake,box.getId())+ladderAtBox(firstLadder,box.getId())+"]"+oddRow(row, colLeft-1);
		}
		else {
			Box box = searchBox((row*cols));
			return "["+box.idBoard()+" "+snakeAtBox(firstSnake,box.getId())+ladderAtBox(firstLadder,box.getId())+"]";
		}
	}
	public String status() {
		return status(rows);
	}
	public String status(int rowLeft) {
		if (rowLeft > 1) {
			if ((rowLeft % 2) == 0)
				return "<< "+statusEvenRow(rowLeft, cols)+" <<\n"+status(rowLeft - 1);
			else
				return ">> "+statusOddRow(rowLeft, cols)+" >>\n"+status(rowLeft - 1);
		}else
			return ">> "+statusOddRow(rowLeft, cols)+" >>";
	}
	public String statusEvenRow(int row, int colLeft) {
		if (colLeft>1) 
			return infoAtBox((row*cols)-(cols-colLeft))+statusEvenRow(row, colLeft-1);
		else
			return infoAtBox((row*cols)-(cols-colLeft));
	}
	public String statusOddRow(int row, int colLeft) {
		if (colLeft>1)
			return infoAtBox((row*cols)-colLeft+1)+statusOddRow(row, colLeft-1);
		else
			return infoAtBox((row*cols));
	}
	public String infoAtBox(int box) {
		return "["+playerAtBox(firstPlayer, box)+snakeAtBox(firstSnake, box)+ladderAtBox(firstLadder, box)+"]";
	}
	public String playerAtBox(Player player, int box) {
		if (player.getNextPlayer() == null) {
			if (player.getBox() == box)
				return Character.toString(player.getSymbol());
			else
				return " ";
		}else {
			if (player.getBox() == box)
				return Character.toString(player.getSymbol())+playerAtBox(player.getNextPlayer(), box);
			else
				return " "+playerAtBox(player.getNextPlayer(), box);
		}
	}
	public String snakeAtBox(Snake snake, int box) {
		if (snake.getNextSnake() == null) {
			if (snake.getBeginning() == box || snake.getEnd() == box)
				return Character.toString(snake.getId());
			else
				return " ";
		}else {
			if (snake.getBeginning() == box || snake.getEnd() == box)
				return Character.toString(snake.getId());
			else
				return snakeAtBox(snake.getNextSnake(), box);
		}
	}
	public String ladderAtBox(Ladder ladder, int box) {
		if (ladder.getNextLadder() == null) {
			if (ladder.getBeginning() == box || ladder.getEnd() == box)
				return String.valueOf(ladder.getId());
			else
				return " ";
		}else {
			if (ladder.getBeginning() == box || ladder.getEnd() == box)
				return String.valueOf(ladder.getId());
			else
				return ladderAtBox(ladder.getNextLadder(), box);
		}
	}
}