import java.awt.*;
import javax.swing.*;

public class DrawDice extends JComponent implements View {
	private Integer dice1;
	private Integer dice2;
	private double scale;

	public DrawDice(double textSize) {
		if (textSize == 0) {
			scale = javafx.scene.text.Font.getDefault().getSize();
		} else {
			scale = textSize;
		}
		dice1 = 1;
		dice2 = 1;
		setSize((int) (11 * scale), (int) (5 * scale));
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(getWidth(), getHeight());
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(44, 127, 65));
		double scaleX = getWidth() / 23;
		double scaleY = getHeight() / 12;
		int width = (int) (23 * scaleX);
		int height = (int) (12 * scaleY);
		int x = (getWidth() - width) / 2;
		int y = (getHeight() - height) / 2;
		g.fillRect(x, y, (int) (23 * scaleX), (int) (12 * scaleY));
		drawDice(g, (int) (x + 1 * scaleX), (int) (y + 1 * scaleY),
				(int) (10 * scaleX), (int) (10 * scaleY), dice1);
		drawDice(g, (int) (x + 12 * scaleX), (int) (y + 1 * scaleY),
				(int) (10 * scaleX), (int) (10 * scaleY), dice2);
	}

	private void drawDice(Graphics g, int x, int y, int width, int height,
			int value) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		switch (value) {
		case 1:
			draw1(g, x, y, width, height);
			break;
		case 2:
			draw2(g, x, y, width, height);
			break;
		case 3:
			draw3(g, x, y, width, height);
			break;
		case 4:
			draw4(g, x, y, width, height);
			break;
		case 5:
			draw5(g, x, y, width, height);
			break;
		case 6:
			draw6(g, x, y, width, height);
			break;
		}
	}

	private void draw1(Graphics g, int x, int y, int width, int height) {
		double scaleX = width / 10.0;
		double scaleY = height / 10.0;
		drawOval(g, x + width / 2, y + height / 2, (int) (2 * scaleX),
				(int) (2 * scaleY));
	}

	private void draw2(Graphics g, int x, int y, int width, int height) {
		double scaleX = width / 10.0;
		double scaleY = height / 10.0;
		drawOval(g, x + width / 4, y + height / 4, (int) (2 * scaleX),
				(int) (2 * scaleY));
		drawOval(g, x + width - width / 4, y + height - height / 4,
				(int) (2 * scaleX), (int) (2 * scaleY));
	}

	private void draw3(Graphics g, int x, int y, int width, int height) {
		draw1(g, x, y, width, height);
		draw2(g, x, y, width, height);
	}

	private void draw4(Graphics g, int x, int y, int width, int height) {
		double scaleX = width / 10.0;
		double scaleY = height / 10.0;
		draw2(g, x, y, width, height);
		drawOval(g, x + width - width / 4, y + height / 4, (int) (2 * scaleX),
				(int) (2 * scaleY));
		drawOval(g, x + width / 4, y + height - height / 4, (int) (2 * scaleX),
				(int) (2 * scaleY));
	}

	private void draw5(Graphics g, int x, int y, int width, int height) {
		draw4(g, x, y, width, height);
		draw1(g, x, y, width, height);
	}

	private void draw6(Graphics g, int x, int y, int width, int height) {
		double scaleX = width / 10.0;
		double scaleY = height / 10.0;
		draw4(g, x, y, width, height);
		drawOval(g, x + width / 4, y + height / 2, (int) (2 * scaleX),
				(int) (2 * scaleY));
		drawOval(g, x + width - width / 4, y + height / 2, (int) (2 * scaleX),
				(int) (2 * scaleY));
	}

	private void drawOval(Graphics g, int x, int y, int width, int height) {
		x -= width / 2;
		y -= height / 2;
		g.fillOval(x, y, width, height);
	}

	@Override
	public void update(Object arg) {
		if (arg instanceof Point) {
			dice1 = ((Point) arg).x;
			dice2 = ((Point) arg).y;
		}
	}
}
