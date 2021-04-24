package model;

public class Player {
	private char symbol;
	private Player nextPlayer;
	private int box;
	public Player(char s) {
		symbol = s;
	}
	public char getSymbol() {
		return symbol;
	}
	public Player getNextPlayer() {
		return nextPlayer;
	}
	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}
	public int getBox() {
		return box;
	}
	public void setBox(int box) {
		this.box = box;
	}

}
