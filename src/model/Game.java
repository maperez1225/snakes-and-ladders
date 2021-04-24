package model;

public class Game {
	private int rows;
	private int cols;
	private int snakes;
	private int ladders;
	private Player firstPlayer;
	private Box firstBox;
	public Game(int m, int n, int s, int l) {
		rows = m;
		cols = n;
		snakes = s;
		ladders = l;
		generateGame();
	}
	public Box getFirstBox() {
		return firstBox;
	}
	private void generateGame() {
		firstBox = new Box(rows,1);
		createRow(rows,1,firstBox);
		setBoxIds();
	}
	private void createRow(int i, int j, Box first) {
		createCol(i,j,first);
		if (j <= cols) {
			Box rightFirst = new Box(i, j+1);
			rightFirst.setLeft(first);
			first.setRight(rightFirst);
			createRow(i, j+1, rightFirst);
		}
	}
	private void createCol(int i, int j, Box prev) {
		if (i > 0) {
			Box upPrev = new Box(i-1,j);
			upPrev.setDown(prev);
			prev.setUp(upPrev);
			createCol(i-1,j,upPrev);
		}
	}
	private void setBoxIds() {
		
	}
}
