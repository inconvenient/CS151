import java.util.ArrayList;

public class Player implements Model {
	private String name;
	private int score;
	private int position;
	private GameBoard gameBoard;
	private ArrayList<View> views;
	private int turns;
	
	public Player(String name) {
		this.name = name;
//		this.gameBoard = gameBoard;
		score = 0;
		position = 0;
		views = new ArrayList<>();
		turns = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getPosition() {
		return position;
	}
	
	public int getTurns() {
		return turns;
	}
	
	public void addScore(int points) {
		score += points;
		notifyViews(toString());
	}
	
	public void addTurns(int numTurns) {
		turns += numTurns;
	}
	
	@Override
	public void attachView(View newView) {
		views.add(newView);
	}
	
	@Override
	public void update(Object arg) {
		if (arg instanceof Integer) {
			position = (int) arg;
			notifyViews(position);
		}
	}
	
	@Override
	public void notifyViews(Object arg) {
		for (View v : views) {
			v.update(arg);
		}
	}
	
	@Override
	public String toString() {
		return name + ": " + score + " points";
	}
}
