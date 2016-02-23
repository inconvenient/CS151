import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

	private static Model game;
	private static JFrame window;

	public static void main(String[] args) {

		// JFrame Setup
		window = new JFrame("151 Board Game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());
		window.setPreferredSize(new Dimension(800, 700));

		// Model and Game Setup
		game = new Game();
		double textSize = 0;
		GameBoard gb = new GameBoard(textSize);
		game.attachView(gb);

		game.update(new Space(Color.RED, 10, false));
		game.update(new Space(Color.GREEN, -5, false));
		game.update(new Space(Color.BLUE, 60, true));
		game.update(new Space(Color.BLACK, -20, false));
		game.update(new Space(Color.RED, 10, false));
		game.update(new Space(Color.GREEN, -5, false));
		game.update(new Space(Color.BLUE, 60, true));
		game.update(new Space(Color.BLACK, -20, false));
		game.update(new Space(Color.RED, 10, false));
		game.update(new Space(Color.GREEN, -5, false));
		game.update(new Space(Color.BLUE, 60, false));
		game.update(new Space(Color.BLACK, -20, false));
		game.update(new Space(Color.BLACK, -20, false));
		game.update(new Space(Color.RED, 30, false));
		game.update(new Space(Color.GREEN, 60, false));
		game.update(new Space(Color.BLUE, 40, true));
		game.update(new Dice());
		game.update(new Player("Kenny"));
		game.update(new Player("Dylan"));
		game.update(new Player("Benton"));

		// Controller and Buttons
		JPanel buttonBar = new JPanel();
		buttonBar.setLayout(new FlowLayout());
		JButton roll = new JButton("Roll");
		roll.setPreferredSize(new Dimension(300, 20));
		roll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.update(Game.ROLL);
				if (checkWinner()) {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					// String gotta be fixed
					int dialogResult = JOptionPane.showConfirmDialog(window,
							((Game) game).getWinner() + " wins! Restart?",
							"Game End", dialogButton);
					if (dialogResult == 0) {
						game.update(Game.RESTART);
					} else {
						System.exit(0);
					}
				}
			}

		});

		JButton quit = new JButton("Quit");
		quit.setPreferredSize(new Dimension(300, 20));
		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

		buttonBar.add(roll);
		buttonBar.add(quit);

		// Adding everything to the window
		window.add(gb, BorderLayout.CENTER);
		window.add(buttonBar, BorderLayout.SOUTH);
		window.pack();
		window.setVisible(true);

	}

	// HELPER METHODS
	public static boolean checkWinner() {
		for (Player p : ((Game) game).getPlayerList()) {
			if (p.getScore() >= 150) {
				((Game) game).setWinner(p);
				return true;
			}
		}
		return false;
	}
}
