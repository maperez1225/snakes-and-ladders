package model;

public class Ladder {

	private int beginning;
	private int end;
	private Ladder nextLadder;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public Ladder getNextLadder() {
		return nextLadder;
	}
	public void setNextLadder(Ladder nextLadder) {
		this.nextLadder = nextLadder;
	}
	public Ladder() {
		
	}

}
