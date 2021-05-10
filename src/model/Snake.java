package model;
public class Snake {
	private int beginning;
	private int end;
	private Snake nextSnake;
	private char id;
	public char getId() {
		return id;
	}
	public void setId(char i) {
		id = i;
	}
	public int getBeginning() {
		return beginning;
	}
	public void setBeginning(int b) {
		beginning = b;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int e) {
		end = e;
	}
	public Snake getNextSnake() {
		return nextSnake;
	}
	public void setNextSnake(Snake s) {
		nextSnake = s;
	}
}