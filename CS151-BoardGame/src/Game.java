import java.util.*;

public class Game implements Model {
	private ArrayList<View> views;
	private ArrayList<Player> players;
	private ArrayList<Space> spaces;
	private Dice dice;
	private int currentPlayer;
	public static final int ROLL = 1;
	public static final int RESTART = 2;
	private Player winner;

	public Game() {
		views = new ArrayList<>();
		players = new ArrayList<>();
		spaces = new ArrayList<>();
		dice = new Dice();
		currentPlayer = 0;
		winner = null;
	}

	public ArrayList<Player> getPlayerList() {
		return this.players;
	}

	public void addSpace(Space s) {
		DrawSpace ds = new DrawSpace(s.getColor(), 0);
		s.attachView(ds);
		notifyViews(ds);
		spaces.add(s);
	}

	public void addPlayer(Player p) {
		players.add(p);
		Pieces[] pieces = Pieces.values();
		GamePiece gp = new GamePiece(pieces[players.size() - 1], 0);
		DrawPlayer dp = new DrawPlayer(0);
		p.attachView(gp);
		p.attachView(dp);
		notifyViews(gp);
		notifyViews(dp);
		p.update(0);
	}

	@Override
	public void update(Object arg) {
		if (arg instanceof Integer) {
			if ((int) arg == ROLL) {
				Player activePlayer = players.get(currentPlayer);
				activePlayer.addTurns(1);
				int currentPos = activePlayer.getPosition();
				dice.update(arg);
				activePlayer.update((currentPos + dice.getValue())
						% spaces.size());
				Space landed = spaces.get(activePlayer.getPosition());
				landed.affect(activePlayer);
				activePlayer.addTurns(-1);
				if (activePlayer.getTurns() <= 0) {
					currentPlayer = (currentPlayer + 1) % players.size();
				} else {
					// Consume turn if player didn't land on extra turn
					activePlayer.addTurns(-1);
				}
			} else if ((int) arg == RESTART) {
				for (Player p : players) {
					p.addScore(-1 * p.getScore());
					p.addTurns(-1 * p.getTurns());
					p.update(p.getPosition() - p.getPosition());
					currentPlayer = 0;
				}
			}
			notifyViews(null);
		} else if (arg instanceof Space) {
			addSpace((Space) arg);
		} else if (arg instanceof Player) {
			addPlayer((Player) arg);
		} else if (arg instanceof Dice) {
			dice = (Dice) arg;
			DrawDice dd = new DrawDice(0);
			dice.attachView(dd);
			notifyViews(dd);
		}
	}
	
	public void setWinner(Player p) {
		winner = p;
	}
	
	public String getWinner() {
		return winner.getName();
	}

	@Override
	public void notifyViews(Object arg) {
		for (View v : views) {
			v.update(arg);
		}
	}

	@Override
	public void attachView(View arg) {
		views.add(arg);
	}
}
