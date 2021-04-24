package model;

public class Box {
	private int id;
	private int row;
	private int col;
	private Box ladderTo;
	private Box snakeTo;
	private Box up;
	private Box down;
	private Box left;
	private Box right;
	public Box(int m, int n) {
		row = m;
		col = n;
	}
	public int getId() {
		return id;
	}
	public void setId(int i) {
		id = i;
	}
	public Box getLadderTo() {
		return ladderTo;
	}
	public void setLadderTo(Box ladderTo) {
		this.ladderTo = ladderTo;
	}
	public Box getSnakeTo() {
		return snakeTo;
	}
	public void setSnakeTo(Box snakeTo) {
		this.snakeTo = snakeTo;
	}
	public Box getUp() {
		return up;
	}
	public void setUp(Box up) {
		this.up = up;
	}
	public Box getDown() {
		return down;
	}
	public void setDown(Box down) {
		this.down = down;
	}
	public Box getLeft() {
		return left;
	}
	public void setLeft(Box left) {
		this.left = left;
	}
	public Box getRight() {
		return right;
	}
	public void setRight(Box right) {
		this.right = right;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
}
