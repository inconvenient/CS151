import java.awt.*;
import java.util.*;

public class Space implements Model {
	private boolean extraTurn;
	private int points;
	private Color color;
	private ArrayList<View> views;
	
	public Space(Color color, int points, boolean extraTurn) {
		this.color = color;
		this.points = points;
		this.extraTurn = extraTurn;
		views = new ArrayList<>();
	}
	
	public int getPoints() {
		return points;
	}
	
	public boolean getExtraTurn() {
		return extraTurn;
	}
	
	public Color getColor() {
		return color;
	}
	
	@Override
	public void attachView(View newView) {
		views.add(newView);
	}
	
	public void affect(Player p) {
		if (extraTurn) {
			p.addTurns(1);
		}
		p.addScore(points);
		if (points < 0) {
			notifyViews(Color.RED);
		}
		else if (points > 0) {
			notifyViews(Color.GREEN);
		}
	}

	@Override
	public void update(Object arg) {
		/* nothing needed */
	}

	@Override
	public void notifyViews(Object arg) {
		for (View v : views) {
			v.update(arg);
		}
	}
}
