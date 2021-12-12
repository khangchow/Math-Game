package model;

public class Player {
	private String ingame;
	private int level;
	private int timeUsed;
	
	public  Player() {
		this.ingame = null;
		this.level = 0;
		this.timeUsed = 0;
	}
	
	public Player(String ingame, int level, int timeUsed) {
		this.ingame = ingame;
		this.level = level;
		this.timeUsed = timeUsed;
	}

	public int getLevel() {
		return level;
	}

	public String getIngame() {
		return ingame;
	}

	public int getTimeUsed() {
		return timeUsed;
	}
}

