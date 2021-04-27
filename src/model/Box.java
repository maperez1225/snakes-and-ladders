package model;

public class Box {
	private int id;
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