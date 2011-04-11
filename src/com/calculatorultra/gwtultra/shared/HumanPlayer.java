package com.calculatorultra.gwtultra.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class HumanPlayer implements IsSerializable{
	private String name;
	private String password;
	private Integer normalHighScore = 0;
	private Integer wrappingHighScore = 0;
	private Integer chaseHighScore = 0;
	
	public HumanPlayer() {
		
	}
	
	public HumanPlayer(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public HumanPlayer(String name, String password, int normalHighScore, int wrappingHighScore, int chaseHighScore) {
		this.name = name;
		this.password = password;
		this.normalHighScore = normalHighScore;
		this.wrappingHighScore = wrappingHighScore;
		this.chaseHighScore = chaseHighScore;
	}
	
	public int getNormalHighScore() {
		return normalHighScore;
	}
	
	public String getNormalHighScoreString() {
		return normalHighScore.toString();
	}

	public void setNormalHighScore(int normalHighScore) {
		this.normalHighScore = normalHighScore;
	}

	public int getWrappingHighScore() {
		return wrappingHighScore;
	}
	
	public String getWrappingHighScoreString() {
		return wrappingHighScore.toString();
	}

	public void setWrappingHighScore(int wrappingHighScore) {
		this.wrappingHighScore = wrappingHighScore;
	}

	public int getChaseHighScore() {
		return chaseHighScore;
	}

	public String getChaseHighScoreString() {
		return chaseHighScore.toString();
	}

	public void setChaseHighScore(int chaseHighScore) {
		this.chaseHighScore = chaseHighScore;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		
	}
}
