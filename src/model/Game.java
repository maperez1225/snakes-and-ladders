package model;

public class Game {
	private int rows;
	private int cols;
	private int snakes;
	private int ladders;
	private Player firstPlayer;
	private Box firstBox;
	public Game(int m, int n, int s, int l, String players) {
		rows = m;
		cols = n;
		snakes = s;
		ladders = l;
		generateBoard();
		generatePlayers(players);
	}
	private void generatePlayers(String players) {
		firstPlayer = new Player(players.charAt(0));
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
		if (colLeft>1) 
			return searchBox((row*cols)-(cols-colLeft)).board()+evenRow(row, colLeft-1);
		else
			return searchBox((row*cols)-(cols-colLeft)).board();
	}
	public String oddRow(int row, int colLeft) {
		if (colLeft>1)
			return searchBox((row*cols)-colLeft+1).board()+oddRow(row, colLeft-1);
		else
			return searchBox((row*cols)).board();
	}
	public String status(int rowLeft) {
		if (rowLeft > 1) {
			if ((rowLeft % 2) == 0)
				return statusEvenRow(rowLeft, cols)+"\n"+status(rowLeft - 1);
			else
				return statusOddRow(rowLeft, cols)+"\n"+status(rowLeft - 1);
		}else
			return statusOddRow(rowLeft, cols);
	}
	public String statusEvenRow(int row, int colLeft) {
		if (colLeft>1) 
			return playersAtBox((row*cols)-(cols-colLeft))+statusEvenRow(row, colLeft-1);
		else
			return playersAtBox((row*cols)-(cols-colLeft));
	}
	public String statusOddRow(int row, int colLeft) {
		if (colLeft>1)
			return playersAtBox((row*cols)-colLeft+1)+statusOddRow(row, colLeft-1);
		else
			return playersAtBox((row*cols));
	}
	public String playersAtBox(int box) {
		String text = "[";
		text += playerAtBox(firstPlayer, box);
		text += "]";
		return text;
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
}