package com.calculatorultra.gwtultra.server;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.calculatorultra.gwtultra.client.UltraService;
import com.calculatorultra.gwtultra.shared.HumanPlayer;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UltraServiceImpl extends RemoteServiceServlet implements UltraService {
	HumanPlayer calvin = new HumanPlayer("Calvin", "test", 314, 300, 326);
	HumanPlayer nick = new HumanPlayer("Nick", "test", 270, 200, 506);
	HumanPlayer matt = new HumanPlayer("Matt", "test", 30, 5, 350);
	HumanPlayer mitch = new HumanPlayer("Mitch", "test", 10, 10, 10);

	private static final long serialVersionUID = 1L;
	
	

	@Override
	public Map<String, List<HumanPlayer>> getTop10HighScores() {
		List<HumanPlayer> normalHighSores = new ArrayList<HumanPlayer>();
		normalHighSores.add(calvin);
		normalHighSores.add(nick);
		normalHighSores.add(matt);
		normalHighSores.add(mitch);
		List<HumanPlayer> wrappingHighSores = new ArrayList<HumanPlayer>();
		wrappingHighSores.add(calvin);
		wrappingHighSores.add(nick);
		wrappingHighSores.add(mitch);
		wrappingHighSores.add(matt);
		List<HumanPlayer> chaseHighSores = new ArrayList<HumanPlayer>();
		chaseHighSores.add(nick);
		chaseHighSores.add(matt);
		chaseHighSores.add(calvin);
		chaseHighSores.add(mitch);
		Map<String, List<HumanPlayer>> top10HighScores = new HashMap<String, List<HumanPlayer>>();
		top10HighScores.put("normal", normalHighSores);
		top10HighScores.put("wrapping", wrappingHighSores);
		top10HighScores.put("chase", chaseHighSores);
		return top10HighScores;
	}

	

	@Override
	public HumanPlayer registerPlayer(String name, String password) {
		ArrayList<HumanPlayer> registeredPlayers = getRegisteredPlayers();
		for (HumanPlayer registeredPlayer : registeredPlayers) {
			if (registeredPlayer.getName().equals(name)) {
				return new HumanPlayer(null, null);
			}
		}
		registeredPlayers.add(new HumanPlayer(name, password));
		return new HumanPlayer(name, password);
	}

	@Override
	public HumanPlayer signIn(String name, String password) {
		ArrayList<HumanPlayer> registeredPlayers = getRegisteredPlayers();
		for (HumanPlayer registeredPlayer : registeredPlayers) {
			if (registeredPlayer.getName().equals(name)) {
				if (registeredPlayer.getPassword().equals(password)) {
					return registeredPlayer;
				} else return new HumanPlayer(name, null);
			}
		}
		return new HumanPlayer(null, null);
	}

	@Override
	public void setNewHighScore(HumanPlayer humanPlayer) {
		ArrayList<HumanPlayer> registeredPlayers = getRegisteredPlayers();
		for (HumanPlayer registeredPlayer : registeredPlayers) {
			if (registeredPlayer.getName().equals(humanPlayer.getName())) {
				registeredPlayer.setNormalHighScore(humanPlayer.getNormalHighScore());
				registeredPlayer.setWrappingHighScore(humanPlayer.getWrappingHighScore());
				registeredPlayer.setChaseHighScore(humanPlayer.getChaseHighScore());
			}
		}
	}
	
	private ArrayList<HumanPlayer> getRegisteredPlayers() {
		ArrayList<HumanPlayer> registeredPlayers = new ArrayList<HumanPlayer>();
		registeredPlayers.add(nick);
		registeredPlayers.add(matt);
		registeredPlayers.add(calvin);
		registeredPlayers.add(mitch);
		return registeredPlayers;
	}

}
