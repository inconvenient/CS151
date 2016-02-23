import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GameBoard extends JLayeredPane implements View {
	private ArrayList<DrawSpace> spaces = new ArrayList<>();
	private ArrayList<GamePiece> pieces = new ArrayList<>();
	private JPanel scores;
	private DrawDice dice;
	double scale;

	public GameBoard(double textSize) {
		super();
		if (textSize == 0) {
			scale = javafx.scene.text.Font.getDefault().getSize();
		}
		else {
			scale = textSize;
		}
		setLayout(null);
//		setSize(12 * 10, 8 * 12);
		spaces = new ArrayList<>();
		pieces = new ArrayList<>();
		dice = new DrawDice(0);
		scores = new JPanel();
		add(scores);
	}
	
	@Override
	public void update(Object arg) {
		if (arg instanceof Component) {
			Component c = (Component) arg; 
			if (arg instanceof DrawSpace) {
				add(c, 1, 0);
				spaces.add((DrawSpace) arg);
			}
			else if (arg instanceof GamePiece) {
				add(c, 3, 0);
				pieces.add((GamePiece) arg);
				((GamePiece) arg).associate(this);
			}
			else if (arg instanceof DrawDice) {
				add(c, 2, 0);
				dice = (DrawDice) arg;
			}
			else if (arg instanceof DrawPlayer) {
				scores.add(c, 3, 0);
			}
		}
		
		if (spaces.size() > 0) {
			DrawSpace reference = spaces.get(0);
			int spaceWidth = reference.getWidth();
			int spaceHeight = reference.getHeight();
			setSize(spaceWidth * (spaces.size() / 4 + 1), spaceHeight * (spaces.size() / 4 + 1));
			adjustLocations();
//			for (GamePiece gp : pieces) {
//				int pos = gp.getPosition();
//				Point corner = spaces.get(pos).getLocation();
//				int x = corner.x + spaceWidth / 2 - gp.getWidth() / 2;
//				int y = corner.y + spaceHeight / 2 - gp.getHeight() / 2;
//				gp.setLocation(x, y);
//			}
			scores.setSize((int) (scale * 12), getHeight());
			scores.setLocation((int) (getWidth() + scale), 0);
			setSize(getWidth() + scores.getWidth(), getHeight());
		}
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void adjustLocations() {
		int centerX = getWidth() / 2;
		int centerY = getHeight() / 2;
		int diceX = centerX - dice.getWidth() / 2;
		int diceY = centerY - dice.getHeight() / 2;
		dice.setLocation(diceX, diceY);
		
		for (int i = 0; i < spaces.size(); ++i) {
			DrawSpace s = spaces.get(i);
			int spaceWidth = s.getWidth();
			int spaceHeight = s.getHeight();
			if (i < spaces.size() / 4) {
				s.setLocation(getWidth() - spaceWidth * (i + 1), getHeight() - spaceHeight);
			}
			else if (i < spaces.size() / 2) {
				int count = i - spaces.size() / 4;
				s.setLocation(0, getHeight() - (count + 1) * spaceHeight);
			}
			else if (i < spaces.size() - spaces.size() / 4) {
				int count = i - spaces.size() / 2;
				s.setLocation(spaceWidth * count, 0);
			}
			else {
				int count = i - (spaces.size() - spaces.size() / 4);
				s.setLocation(getWidth() - spaceWidth, spaceHeight * count);
			}
		}
	}
	
	public Point getSpaceLocation(int position) {
		return spaces.get(position).getLocation();
	}
	
	public int getNumberOfSpaces() {
		return spaces.size();
	}

	public DrawDice getDice() {
		return dice;
	}
}
