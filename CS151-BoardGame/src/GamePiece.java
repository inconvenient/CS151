import java.awt.*;

import javax.swing.*;

public class GamePiece extends JComponent implements View
{
	private Pieces piece;
	private double scale;
	private int position;
	private GameBoard board;
	
	public GamePiece(Pieces piece, double textSize) {
		if (textSize == 0) {
			scale = javafx.scene.text.Font.getDefault().getSize();
		}
		else {
			scale = textSize;
		}
		this.piece = piece;
		position = 0;
		setSize((int) (6 * scale), (int) (6 * scale));
	}
	
	@Override
	public void update(Object arg) {
		if (arg instanceof Integer) {
			int newPosition = (int) arg;
			new Mover(position, newPosition).start();
			position = newPosition;
		}
	}
	
	public int getPosition() {
		return position;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(getWidth(), getHeight());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		switch (piece) {
			case DOG:
				drawDog(g);
				break;
			case THIMBLE:
				drawThimble(g);
				break;
			case WHEELBARROW:
				drawWheelbarrow(g);
				break;
		}
	}
	
	private void drawDog(Graphics g) {
		double scaleX = getWidth() / 100.0;
		double scaleY = getHeight() / 100.0;
		g.setColor(new Color(139, 69, 19));
		drawOval(g, getWidth() / 2, getHeight() / 2, (int) (80 * scaleX),
			(int) (85 * scaleY));
		g.setColor(Color.WHITE);
		drawOval(g, (int) (38 * scaleX), (int) (45 * scaleY), getWidth() / 5,
			getHeight() / 5);
		drawOval(g, (int) (61 * scaleX), (int) (45 * scaleY), getWidth() / 5,
			getHeight() / 5);
		g.setColor(Color.BLACK);
		drawOval(g, (int) (42 * scaleX), (int) (45 * scaleY), getWidth() / 8,
			getHeight() / 8);
		drawOval(g, (int) (58 * scaleX), (int) (45 * scaleY), getWidth() / 8,
			getHeight() / 8);
		drawOval(g, getWidth() / 2, (int) (67 * scaleY),
			(int) (getWidth() / 4.5), (int) (getHeight() / 4.5));
		g.setColor(new Color(102, 52, 0));
		int[] earYPoints = {(int) (20 * scaleY), (int) (52 * scaleY),
			(int) (62 * scaleY), (int) (54 * scaleY), (int) (30 * scaleY),
			(int) (20 * scaleY)};
		g.fillPolygon(
			new int[] {(int) (28 * scaleX), (int) (15 * scaleX),
				(int) (15 * scaleX), 0, (int) (12 * scaleX),
				(int) (28 * scaleX)},
			earYPoints, 6);
		g.fillPolygon(
			new int[] {(int) (70 * scaleX), (int) (85 * scaleX),
				(int) (85 * scaleX), getWidth(), (int) (88 * scaleX),
				(int) (70 * scaleX)},
			earYPoints, 6);
	}
	
	private void drawOval(Graphics g, int x, int y, int width, int height) {
		x -= (width / 2);
		y -= (height / 2);
		g.fillOval(x, y, width, height);
	}
	
	private void drawThimble(Graphics g) {
		double scaleX = getWidth() / 100.0;
		double scaleY = getHeight() / 100.0;
		g.setColor(Color.DARK_GRAY);
		g.fillPolygon(
			new int[] {(int) (19 * scaleX), (int) (24 * scaleX),
				(int) (29 * scaleX), (int) (35 * scaleX), (int) (41 * scaleX),
				(int) (57 * scaleX), (int) (63 * scaleX), (int) (69 * scaleX),
				(int) (73 * scaleX), (int) (78 * scaleX), (int) (70 * scaleX),
				(int) (62 * scaleX), (int) (42 * scaleX), (int) (29 * scaleX),
				(int) (19 * scaleX)},
			new int[] {(int) (58 * scaleY), (int) (10 * scaleY),
				(int) (5 * scaleY), (int) scaleY, 0, 0, (int) scaleY,
				(int) (6 * scaleY), (int) (11 * scaleY), (int) (58 * scaleY),
				(int) (63 * scaleY), (int) (64 * scaleY), (int) (64 * scaleY),
				(int) (63 * scaleY), (int) (58 * scaleY)}, 15);
		g.setColor(Color.GRAY);
		g.fillPolygon(
			new int[] {(int) (78 * scaleX), (int) (70 * scaleX),
				(int) (62 * scaleX), (int) (42 * scaleX), (int) (29 * scaleX),
				(int) (19 * scaleX), (int) (17 * scaleX), (int) (21 * scaleX),
				(int) (31 * scaleX), (int) (44 * scaleX), (int) (56 * scaleX),
				(int) (70 * scaleX), (int) (78 * scaleX), (int) (81 * scaleX),
				(int) (78 * scaleX)},
			new int[] {(int) (58 * scaleY), (int) (63 * scaleY),
				(int) (64 * scaleY), (int) (64 * scaleY), (int) (63 * scaleY),
				(int) (58 * scaleY), (int) (77 * scaleY), (int) (85 * scaleY),
				(int) (89 * scaleY), (int) (91 * scaleY), (int) (91 * scaleY),
				(int) (88 * scaleY), (int) (83 * scaleY), (int) (79 * scaleY),
				(int) (58 * scaleY)}, 15);
	}

	private void drawWheelbarrow(Graphics g) {
		g.setColor(Color.BLACK);
		int wheelWidth = (int) (getWidth() / 4.0);
		int wheelHeight = (int) (getHeight() / 4.0);
		g.fillOval(getWidth() / 6, getHeight() - wheelHeight, wheelWidth,
			wheelHeight);
		g.fillOval(getWidth() - wheelWidth - getWidth() / 6,
			getHeight() - wheelHeight, wheelWidth, wheelHeight);
		g.setColor(Color.DARK_GRAY);
		int handle = (int) (getWidth() / 4.0);
		int handleThickness = (int) (getHeight() / 7.0);
		g.fillRect(getWidth() - handle, 0, handle, handleThickness);
		g.fillRect(getWidth() - handle / 2 - handleThickness / 2, 0,
			getHeight() / 7, (int) (getHeight() / 1.5));
		g.setColor(Color.RED);
		g.fillRect(0, getHeight() / 2, getWidth(),
			getHeight() / 2 - wheelHeight / 2);
	}
	
	public void associate(GameBoard board) {
		this.board = board;
	}
	
	private class Mover extends Thread {
		private int oldPos;
		private int newPos;
		private double moveTime;
		
		public Mover(int oldPos, int newPos) {
			this.oldPos = oldPos;
			this.newPos = newPos;
			moveTime = 0.10;
		}
		
		@Override
		public void run() {
			while (oldPos != newPos) {
				oldPos = (oldPos + 1) % board.getNumberOfSpaces();
				Point temp = board.getSpaceLocation(oldPos);
				int x = temp.x + getWidth() / 2;
				int y = temp.y + getHeight() / 2;
				moveToX(x);
				moveToY(y);
			}
		}
		
		private void moveToX(int x) {
			double velocity = (x - getX()) / (moveTime);
			long startTime = System.currentTimeMillis();
			while (getX() != x) {
				long currentTime = System.currentTimeMillis();
				long deltaTime = currentTime - startTime;
				startTime = currentTime;
				setLocation((int) (getX() + velocity * (deltaTime / 1000.0)), getY());
				if (velocity < 0 && getX() < x) {
					setLocation(x, getY());
				}
				else if (velocity > 0 && getX() > x) {
					setLocation(x, getY());
				}
			}
		}
		
		private void moveToY(int y) {
			double velocity = (y - getY()) / (moveTime);
			long startTime = System.currentTimeMillis();
			while (getY() != y) {
				long currentTime = System.currentTimeMillis();
				long deltaTime = currentTime - startTime;
				startTime = currentTime;
				setLocation(getX(), (int) (getY() + velocity * (deltaTime / 1000.0)));
				if (velocity < 0 && getY() < y) {
					setLocation(getX(), y);
				}
				else if (velocity > 0 && getY() > y) {
					setLocation(getX(), y);
				}
			}
		}
	}
}
