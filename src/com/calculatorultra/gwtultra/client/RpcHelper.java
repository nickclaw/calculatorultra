package com.calculatorultra.gwtultra.client;

import java.util.List;
import java.util.Map;

import com.calculatorultra.gwtultra.shared.HumanPlayer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class RpcHelper {
	private GwtUltra rpcCaller = null;
	private UltraServiceAsync service = null;
	
	RpcHelper() {
		service = GWT.create(UltraService.class);
	}
	
	public AsyncCallback<Map<String, List<HumanPlayer>>> updateLeaderboardsCallback = new AsyncCallback<Map<String, List<HumanPlayer>>>() {
    	@Override
		public void onFailure(Throwable caught) {
    		Window.alert("system failed");
    		GWT.log("failure on server", caught);
    	}

		@Override
		public void onSuccess(Map<String, List<HumanPlayer>> top10HighScores) {
			GWT.log("success on leaderboard server");
			rpcCaller.setTop10HighScores(top10HighScores);
		}
    };
    
    public AsyncCallback<HumanPlayer> signInCallback = new AsyncCallback<HumanPlayer>() {
    	@Override
		public void onFailure(Throwable caught) {
    		GWT.log("failure on server");
    	}

		@Override
		public void onSuccess(HumanPlayer humanPlayer) {
			GWT.log("success on sign in server");
			if (humanPlayer.getName() == null) rpcCaller.getGraphicsEngine().incorrectName();
			else if (humanPlayer.getPassword() == null) rpcCaller.getGraphicsEngine().incorrectPassword();
			else rpcCaller.successfulSignIn(humanPlayer);
		}
    };
    
    public AsyncCallback<HumanPlayer> registerPlayerCallback = new AsyncCallback<HumanPlayer>() {
    	@Override
		public void onFailure(Throwable caught) {
    		GWT.log("failure on server");
    	}

		@Override
		public void onSuccess(HumanPlayer humanPlayer) {
			GWT.log("success on register server");
			GWT.log(humanPlayer.getName());
			if (humanPlayer.getName() == null) rpcCaller.getGraphicsEngine().notUniqueName();
			else rpcCaller.successfulSignIn(humanPlayer);
		}
    };
	
    public AsyncCallback<Void> voidCallback = new AsyncCallback<Void>() {
    	@Override
		public void onFailure(Throwable caught) {
        Window.alert("system failed");
    	}

      	@Override
		public void onSuccess(Void result) {
			GWT.log("success on void server");
      	}
    };

	public void registerPlayer(String name, String password, GwtUltra gwtUltra) {
		rpcCaller = gwtUltra;
		service.registerPlayer(name, password, registerPlayerCallback);
	}

	public void signIn(String name, String password, GwtUltra gwtUltra) {
		rpcCaller = gwtUltra;
		service.signIn(name, password, signInCallback);	
	}

	public void setNewHighScore(HumanPlayer humanPlayer, GwtUltra gwtUltra) {
		rpcCaller = gwtUltra;
		service.setNewHighScore(humanPlayer, voidCallback);	
		
	}

	public void getTop10HighScores(GwtUltra gwtUltra) {
		rpcCaller = gwtUltra;
		service.getScores(updateLeaderboardsCallback);	
	}
	
	
}
