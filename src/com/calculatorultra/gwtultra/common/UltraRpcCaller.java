package com.calculatorultra.gwtultra.common;

import java.util.List;
import java.util.Map;

import com.calculatorultra.gwtultra.common.exception.IncorrectNameException;
import com.calculatorultra.gwtultra.common.exception.IncorrectPasswordException;
import com.calculatorultra.gwtultra.common.exception.NotUniqueNameException;
import com.calculatorultra.gwtultra.common.exception.SignInException;
import com.calculatorultra.gwtultra.shared.HumanPlayer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;


public abstract class UltraRpcCaller {
	protected UltraServiceAsync service = null;
	
	UltraRpcCaller() {
		service = GWT.create(UltraService.class);
	}
	
	private final AsyncCallback<Map<String, List<HumanPlayer>>> updateLeaderboardsCallback = new AsyncCallback<Map<String, List<HumanPlayer>>>() {
    	@Override
		public void onFailure(Throwable caught) {
    		Window.alert("system failed");
    		GWT.log("failure on server", caught);
    	}

		@Override
		public void onSuccess(Map<String, List<HumanPlayer>> top10HighScores) {
			GWT.log("success on leaderboard server");
			setTop10HighScores(top10HighScores);
		}
    };
    
    private final AsyncCallback<HumanPlayer> signInCallback = new AsyncCallback<HumanPlayer>() {
    	@Override
		public void onFailure(Throwable caught) {
    		GWT.log("failure on server");
    	}

		@Override
		public void onSuccess(HumanPlayer humanPlayer) {
			GWT.log("success on sign in server");
			if (humanPlayer.getName() == null) {
				unsuccessfulSignIn(new IncorrectNameException());
			} else if (humanPlayer.getPassword() == null) {
				unsuccessfulSignIn(new IncorrectPasswordException());
			} else successfulSignIn(humanPlayer);
		}
    };
    
    private final AsyncCallback<HumanPlayer> registerPlayerCallback = new AsyncCallback<HumanPlayer>() {
    	@Override
		public void onFailure(Throwable caught) {
    		GWT.log("failure on server");
    	}

		@Override
		public void onSuccess(HumanPlayer humanPlayer) {
			GWT.log("success on register server");
			GWT.log(humanPlayer.getName());
			if (humanPlayer.getName() == null) {
				unsuccessfulSignIn(new NotUniqueNameException());
			} else {
				successfulSignIn(humanPlayer);
			}
		}
    };
	
    private final AsyncCallback<Void> voidCallback = new AsyncCallback<Void>() {
    	@Override
		public void onFailure(Throwable caught) {
        Window.alert("system failed");
    	}

      	@Override
		public void onSuccess(Void result) {
			GWT.log("success on void server");
      	}
    };

    public void registerPlayer(String name, String password) {
		service.registerPlayer(name, password, registerPlayerCallback);
	}

    protected abstract void successfulSignIn(HumanPlayer humanPlayer);
	
	protected abstract void unsuccessfulSignIn(SignInException signInException);

	protected abstract void setTop10HighScores(Map<String, List<HumanPlayer>> top10HighScores);

	public void signIn(String name, String password) {
		service.signIn(name, password, signInCallback);	
	}

	public void setNewHighScore(HumanPlayer humanPlayer) {
		service.setNewHighScore(humanPlayer, voidCallback);	
		
	}

	protected void getTop10HighScores() {
		service.getScores(updateLeaderboardsCallback);	
	}
	
	protected void gamePlayed(String name, double timePlayed) {
		service.gamePlayed(name, timePlayed, voidCallback);
	}
	
}
