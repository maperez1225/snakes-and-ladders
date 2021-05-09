package model;
import java.io.Serializable;
public class Score implements Serializable{
	private static final long serialVersionUID = 1;
	private int score;
	private String name;
	private Score parent;
	private Score left;
	private Score right;
	public Score(int s, String n) {
		score = s;
		name = n;
	}
	public Score getParent() {
		return parent;
	}
	public void setParent(Score p) {
		parent = p;
	}
	public Score getLeft() {
		return left;
	}
	public void setLeft(Score l) {
		left = l;
	}
	public Score getRight() {
		return right;
	}
	public void setRight(Score r) {
		right = r;
	}
	public int getScore() {
		return score;
	}
	public String getName() {
		return name;
	}
}