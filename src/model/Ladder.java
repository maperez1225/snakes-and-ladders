package model;
public class Ladder {
	private int beginning;
	private int end;
	private Ladder nextLadder;
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int i) {
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
	public Ladder getNextLadder() {
		return nextLadder;
	}
	public void setNextLadder(Ladder l) {
		nextLadder = l;
	}
}