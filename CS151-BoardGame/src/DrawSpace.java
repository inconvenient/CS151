import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class DrawSpace extends JComponent implements View {
	Color color;
	double scale;

	public DrawSpace(Color color, double textSize) {
		this.color = color;
		if (textSize == 0) {
			scale = javafx.scene.text.Font.getDefault().getSize();
		} else {
			scale = textSize;
		}
		setSize((int) (10 * scale), (int) (10 * scale));
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.fillRect((int) (0.5 * scale), (int) (0.5 * scale),
				(int) (getWidth() - scale), (int) (getHeight() - scale));
		g.setColor(color);
		g.fillRect((int) scale, (int) scale, (int) (getWidth() - (scale * 2)),
				(int) (getHeight() * 0.2));
	}

	@Override
	public void update(Object arg) {
		if (arg instanceof Color) {
			Color color = (Color) arg;
			Blinker blinkThread = new Blinker(color);
			blinkThread.start();
		}
	}

	private class Blinker extends Thread {
		private Color newColor;
		private Graphics g;
		private boolean blinking;

		public Blinker(Color c) {
			newColor = c;
			g = getGraphics();
			blinking = false;
		}

		@Override
		public void run() {

			long deltaTime = 0;
			while (deltaTime < 500) {
				if (blinking) {
					repaint();
					blinking = false;
				} else {
					g.setColor(newColor);
					g.fillRect((int) scale, (int) scale,
							(int) (getWidth() - (scale * 2)),
							(int) (getHeight() * 0.2));
					blinking = true;
				}
				try {
					sleep(285);
					deltaTime += 50;
				} catch (Exception e) {
					System.err.println("WHY CAN'T I SLEEP?!");
				}
			}
		}
	}

	// @Override
	// public void updateView(Object... args) {
	// for (Object o : args) {
	// if (o instanceof Color) {
	// color = (Color) o;
	// }
	// }
	// repaint();
	// }
}
