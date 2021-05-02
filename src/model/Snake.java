package model;

public class Snake {
	private int beginning;
	private int end;
	private Snake nextSnake;
	private char id;
	public Snake() {
	}
	
	public char getId() {
		return id;
	}
	
	public void setId(char id) {
		this.id = id;
	}
	
	public int getBeginning() {
		return beginning;
	}
	
	public void setBeginning(int beginning) {
		this.beginning = beginning;
	}
	
	public int getEnd() {
		return end;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}
	
	public Snake getNextSnake() {
		return nextSnake;
	}
	
	public void setNextSnake(Snake nextSnake) {
		this.nextSnake = nextSnake;
	}
	
}
