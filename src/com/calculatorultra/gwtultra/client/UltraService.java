package com.calculatorultra.gwtultra.client;

import java.util.List;
import java.util.Map;

import com.calculatorultra.gwtultra.shared.HumanPlayer;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ultraServer")
public interface UltraService extends RemoteService {
	
	Map<String, List<HumanPlayer>> getTop10HighScores();
	
	HumanPlayer registerPlayer(String name, String password);
	
	HumanPlayer signIn(String name, String password);
	
	void setNewHighScore(HumanPlayer humanPlayer);
}
