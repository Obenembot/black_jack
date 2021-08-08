package test;

public class Player extends PlayersHand {

	private int playerScore;
	private int playerTotalCards;
	private String playersName;
	private String playerStatus;
	private double wallet = 1000;
	private double chips;

	public Player() {
		super();
	}

	public Player(String playersName) {
		super();
		this.playersName = playersName;
	}

	public Player(int playerScore, int playerTotalCards, String playersName, String playerStatus) {
		super();
		this.playerScore = playerScore;
		this.playerTotalCards = playerTotalCards;
		this.playersName = playersName;
		this.playerStatus = playerStatus;
	}

	public Player(String playersName, double wallet, double chips) {
		super();
		this.playersName = playersName;
		this.wallet = wallet;
		this.chips = chips;
	}

	public Player(int playerScore, int playerTotalCards, String playersName, String playerStatus, double wallet,
			double chips) {
		super();
		this.playerScore = playerScore;
		this.playerTotalCards = playerTotalCards;
		this.playersName = playersName;
		this.playerStatus = playerStatus;
		this.wallet = wallet;
		this.chips = chips;
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	public int getPlayerTotalCards() {
		return playerTotalCards;
	}

	public void setPlayerTotalCards(int playerTotalCards) {
		this.playerTotalCards = playerTotalCards;
	}

	public String getPlayersName() {
		return playersName;
	}

	public void setPlayersName(String playersName) {
		this.playersName = playersName;
	}

	public String getPlayerStatus() {
		return playerStatus;
	}

	public void setPlayerStatus(String playerStatus) {
		this.playerStatus = playerStatus;
	}

	public double getWallet() {
		return wallet;
	}

	public void setWallet(double wallet) {
		this.wallet = wallet;
	}

	public double getChips() {
		return chips;
	}

	public void setChips(double chips) {
		this.chips = chips;
	}

	@Override
	public String toString() {
		return "Player [playerScore=" + playerScore + ", playerTotalCards=" + playerTotalCards + ", playersName="
				+ playersName + ", playerStatus=" + playerStatus + ", wallet=" + wallet + ", chips=" + chips
				+ super.toString() + "]";
	}

}
