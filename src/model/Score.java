package model;
import java.io.Serializable;
public class Score implements Serializable{
	private static final long serialVersionUID = 1;
	private int score;
	private String name;
	private Score left;
	private Score right;
	public Score(int s, String n) {
		score = s;
		name = n;
	}
	public String toString() {
		return "Puntaje: "+score+"\nNombre: "+name;
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