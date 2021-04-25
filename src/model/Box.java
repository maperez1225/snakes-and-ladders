package model;

public class Box {
	private int id;
	private Box ladderTo;
	private Box snakeTo;
	private Box next;
	private Box prev;
	public Box(int i) {
		id = i;
	}
	public String board() {
		return "["+id+"]";
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
	public Box getNext() {
		return next;
	}
	public void setNext(Box right) {
		this.next = right;
	}
	public Box getPrev() {
		return prev;
	}
}