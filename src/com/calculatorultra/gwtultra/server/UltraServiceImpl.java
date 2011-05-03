package com.calculatorultra.gwtultra.server;



import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.calculatorultra.gwtultra.client.UltraService;
import com.calculatorultra.gwtultra.shared.HumanPlayer;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UltraServiceImpl extends RemoteServiceServlet implements UltraService {
	private static final long serialVersionUID = 1L;
	
	AwsSimpleDBConnector awsSimpleDB = null;

	@Override
	public Map<String, List<HumanPlayer>> getScores() {
		AwsSimpleDBConnector awsSimpleDB = createAwsConnector();
		Map<String, List<HumanPlayer>> top10HighScores = new HashMap<String, List<HumanPlayer>>();
		if (awsSimpleDB != null) {
			List<HumanPlayer> normalHighScores = awsSimpleDB.getAllHumanPlayers();
			Collections.sort(normalHighScores, new Comparator<HumanPlayer>() {
				@Override
				public int compare(HumanPlayer arg0, HumanPlayer arg1) {
					return arg1.getNormalHighScore() - arg0.getNormalHighScore();
				}
			});
			while (normalHighScores.size() > 10) {
				normalHighScores.remove(10);
			}
			top10HighScores.put("normal", normalHighScores);
			
			List<HumanPlayer> wrappingHighScores = awsSimpleDB.getAllHumanPlayers();
			Collections.sort(wrappingHighScores, new Comparator<HumanPlayer>() {
				@Override
				public int compare(HumanPlayer arg0, HumanPlayer arg1) {
					return arg1.getWrappingHighScore() - arg0.getWrappingHighScore();
				}
			});
			while (wrappingHighScores.size() > 10) {
				wrappingHighScores.remove(10);
			}
			top10HighScores.put("wrapping", wrappingHighScores);
			
			List<HumanPlayer> chaseHighScores = awsSimpleDB.getAllHumanPlayers();
			Collections.sort(chaseHighScores, new Comparator<HumanPlayer>() {
				@Override
				public int compare(HumanPlayer arg0, HumanPlayer arg1) {
					return arg1.getChaseHighScore() - arg0.getChaseHighScore();
				}
			});
			while (chaseHighScores.size() > 10) {
				chaseHighScores.remove(10);
			}
			top10HighScores.put("chase", chaseHighScores);
			return top10HighScores;
		}
		return null;
	}

	

	@Override
	public HumanPlayer registerPlayer(String name, String password) {
		AwsSimpleDBConnector awsSimpleDB = createAwsConnector();
		if (awsSimpleDB != null) {
			List<HumanPlayer> humanPlayers = awsSimpleDB.getPlayer(name);
			if (humanPlayers.size() == 0) {
				HumanPlayer humanPlayer = new HumanPlayer(name, password);
				awsSimpleDB.registerPlayer(humanPlayer);
				return humanPlayer;
			}
		}
		return new HumanPlayer(null, null);
	}

	@Override
	public HumanPlayer signIn(String name, String password) {
		HumanPlayer humanPlayer;
		AwsSimpleDBConnector awsSimpleDB = createAwsConnector();
		if (awsSimpleDB != null) {
			List<HumanPlayer> humanPlayers = awsSimpleDB.getPlayer(name);
			if (humanPlayers.size() > 0) {
				humanPlayer = awsSimpleDB.getPlayer(name).get(0);
				if (humanPlayer.getPassword().equals(password)) {
					return humanPlayer;
				} else return new HumanPlayer(name, null);
			}
		}
		return new HumanPlayer(null, null);
	}

	@Override
	public void setNewHighScore(HumanPlayer humanPlayer) {
		AwsSimpleDBConnector awsSimpleDB = createAwsConnector();
		if (awsSimpleDB != null) {
			awsSimpleDB.setHighScore(humanPlayer);
		}
	}
	
	private AwsSimpleDBConnector createAwsConnector() {
		if (awsSimpleDB == null) {
			try {
				awsSimpleDB = new AwsSimpleDBConnector();
			} catch (AmazonServiceException ase) {
	            System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon SimpleDB, but was rejected with an error response for some reason.");
	            System.out.println("Error Message:    " + ase.getMessage());
	            System.out.println("HTTP Status Code: " + ase.getStatusCode());
	            System.out.println("AWS Error Code:   " + ase.getErrorCode());
	            System.out.println("Error Type:       " + ase.getErrorType());
	            System.out.println("Request ID:       " + ase.getRequestId());
	        } catch (AmazonClientException ace) {
	            System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with SimpleDB, "
	                    + "such as not being able to access the network.");
	            System.out.println("Error Message: " + ace.getMessage());
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} return awsSimpleDB;
		
	}



	@Override
	public void gamePlayed(String name, double timePlayed) {
		AwsSimpleDBConnector awsSimpleDB = createAwsConnector();
		if (awsSimpleDB != null) {
			awsSimpleDB.gamePlayed(name, timePlayed);
		}
	}

}
