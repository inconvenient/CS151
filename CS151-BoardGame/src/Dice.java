import java.util.*;
import java.awt.*;

public class Dice implements Model
{
	private int dice1;
	private int dice2;
	private ArrayList<View> views;
	private Random randomizer;
	
	public Dice() {
		randomizer = new Random();
		dice1 = randomizer.nextInt(6) + 1;
		dice2 = randomizer.nextInt(6) + 1;
		views = new ArrayList<>();
	}
	
	public int getValue() {
		return dice1 + dice2;
	}
	
	@Override
	public void attachView(View newView) {
		views.add(newView);
	}

	@Override
	public void update(Object arg) {
		dice1 = randomizer.nextInt(6) + 1;
		dice2 = randomizer.nextInt(6) + 1;
		notifyViews(new Point(dice1, dice2));
	}

	@Override
	public void notifyViews(Object arg) {
		for (View v : views) {
			v.update(arg);
		}
	}
}