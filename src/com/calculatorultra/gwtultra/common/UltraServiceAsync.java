package com.calculatorultra.gwtultra.common;

import java.util.List;
import java.util.Map;

import com.calculatorultra.gwtultra.shared.HumanPlayer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UltraServiceAsync {

	void getScores(AsyncCallback<Map<String, List<HumanPlayer>>> callback);

	void registerPlayer(String name, String password,
			AsyncCallback<HumanPlayer> callback);

	void setNewHighScore(HumanPlayer humanPlayer, AsyncCallback<Void> callback);

	void signIn(String name, String password,
			AsyncCallback<HumanPlayer> callback);

	void gamePlayed(String name, double timePlayed,
			AsyncCallback<Void> voidCallback);

	

}
