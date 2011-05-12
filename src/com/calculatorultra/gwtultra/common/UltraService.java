package com.calculatorultra.gwtultra.common;

import java.util.List;
import java.util.Map;

import com.calculatorultra.gwtultra.shared.HumanPlayer;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ultraServer")
public interface UltraService extends RemoteService {
	
	Map<String, List<HumanPlayer>> getScores();
	
	HumanPlayer registerPlayer(String name, String password);
	
	HumanPlayer signIn(String name, String password);
	
	void setNewHighScore(HumanPlayer humanPlayer);

	void gamePlayed(String name, double timePlayed);
}