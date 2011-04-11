package com.calculatorultra.gwtultra.server;
/*
 * Copyright 2010-2011 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.BatchPutAttributesRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.calculatorultra.gwtultra.shared.HumanPlayer;

public class AwsSimpleDBConnector {
	
    String myDomain = "UltraPlayerInfo";
    AmazonSimpleDB sdb;

	public AwsSimpleDBConnector() throws Exception {
        /*
         * Important: Be sure to fill in your AWS access credentials in the
         *            AwsCredentials.properties file before you try to run this
         *            sample.
         * http://aws.amazon.com/security-credentials
         */
        sdb = new AmazonSimpleDBClient(new PropertiesCredentials(
                AwsSimpleDBConnector.class.getResourceAsStream("AwsCredentials.properties")));

        System.out.println("Starting a new connection with AWS\n");
        /**
        try {
            // Create a domain
            System.out.println("Creating domain called " + myDomain + ".\n");
            sdb.createDomain(new CreateDomainRequest(myDomain));
            // List domains
            System.out.println("Listing all domains in your account:\n");
            for (String domainName : sdb.listDomains().getDomainNames()) {
                System.out.println("  " + domainName);
            }
            System.out.println();

            // Put data into a domain
            System.out.println("Putting data into " + myDomain + " domain.\n");
            sdb.batchPutAttributes(new BatchPutAttributesRequest(myDomain, createSampleData()));

            // Select data from a domain
            // Notice the use of backticks around the domain name in our select expression.
            String selectExpression = "select * from `" + myDomain + "`";
            System.out.println("Selecting: " + selectExpression + "\n");
            SelectRequest selectRequest = new SelectRequest(selectExpression);
            for (Item item : sdb.select(selectRequest).getItems()) {
                System.out.println("  Item");
                System.out.println("    Name: " + item.getName());
                for (Attribute attribute : item.getAttributes()) {
                    System.out.println("      Attribute");
                    System.out.println("        Name:  " + attribute.getName());
                    System.out.println("        Value: " + attribute.getValue());
                }
            }
            System.out.println();

            /**
            // Delete values from an attribute
            System.out.println("Deleting Blue attributes in Item_O3.\n");
            Attribute deleteValueAttribute = new Attribute("Color", "Blue");
            sdb.deleteAttributes(new DeleteAttributesRequest(myDomain, "Item_03")
                    .withAttributes(deleteValueAttribute));

            // Delete an attribute and all of its values
            System.out.println("Deleting attribute Year in Item_O3.\n");
            sdb.deleteAttributes(new DeleteAttributesRequest(myDomain, "Item_03")
                    .withAttributes(new Attribute().withName("Year")));

            // Replace an attribute
            System.out.println("Replacing Size of Item_03 with Medium.\n");
            List<ReplaceableAttribute> replaceableAttributes = new ArrayList<ReplaceableAttribute>();
            replaceableAttributes.add(new ReplaceableAttribute("Size", "Medium", true));
            sdb.putAttributes(new PutAttributesRequest(myDomain, "Item_03", replaceableAttributes));

            // Delete an item and all of its attributes
            System.out.println("Deleting Item_03.\n");
            sdb.deleteAttributes(new DeleteAttributesRequest(myDomain, "Item_03"));
            // Delete a domain
            System.out.println("Deleting " + myDomain + " domain.\n");
            //sdb.deleteDomain(new DeleteDomainRequest(myDomain));
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
        }
        **/
    }

    /**
     * Creates an array of SimpleDB ReplaceableItems populated with sample data.
     *
     * @return An array of sample item data.
     */
    private static List<ReplaceableItem> createSampleData() {
        List<ReplaceableItem> sampleData = new ArrayList<ReplaceableItem>();

        sampleData.add(new ReplaceableItem("Item_01").withAttributes(
                new ReplaceableAttribute("Name", "Mitch", true),
                new ReplaceableAttribute("Password", "test", true),
                new ReplaceableAttribute("NormalHighScore", "10", true),
                new ReplaceableAttribute("WrappingHighScore", "10", true),
                new ReplaceableAttribute("ChaseHighScore", "10", true)));

        sampleData.add(new ReplaceableItem("Item_02").withAttributes(
                new ReplaceableAttribute("Name", "Calvin", true),
                new ReplaceableAttribute("Password", "test", true),
                new ReplaceableAttribute("NormalHighScore", "20", true),
                new ReplaceableAttribute("WrappingHighScore", "20", true),
                new ReplaceableAttribute("ChaseHighScore", "20", true)));

        sampleData.add(new ReplaceableItem("Item_03").withAttributes(
                new ReplaceableAttribute("Name", "Matt", true),
                new ReplaceableAttribute("Password", "test", true),
                new ReplaceableAttribute("NormalHighScore", "300", true),
                new ReplaceableAttribute("WrappingHighScore", "300", true),
                new ReplaceableAttribute("ChaseHighScore", "300", true)));

        sampleData.add(new ReplaceableItem("Item_04").withAttributes(
                new ReplaceableAttribute("Name", "Nick", true),
                new ReplaceableAttribute("Password", "test", true),
                new ReplaceableAttribute("NormalHighScore", "40", true),
                new ReplaceableAttribute("WrappingHighScore", "40", true),
                new ReplaceableAttribute("ChaseHighScore", "40", true)));

        sampleData.add(new ReplaceableItem("Item_05").withAttributes(
                new ReplaceableAttribute("Name", "Bob", true),
                new ReplaceableAttribute("Password", "test", true),
                new ReplaceableAttribute("NormalHighScore", "50", true),
                new ReplaceableAttribute("WrappingHighScore", "50", true),
                new ReplaceableAttribute("ChaseHighScore", "50", true)));

        return sampleData;
    }
    
    public void registerPlayer(HumanPlayer humanPlayer) {
        System.out.println("START Registering new Player: " + humanPlayer.getName() + "\n");
        List<ReplaceableItem> newPlayers = new ArrayList<ReplaceableItem>();
        newPlayers.add(new ReplaceableItem(humanPlayer.getName()).withAttributes(
                new ReplaceableAttribute("Name", humanPlayer.getName(), true),
                new ReplaceableAttribute("Password", humanPlayer.getPassword(), true),
                new ReplaceableAttribute("NormalHighScore", "0", true),
                new ReplaceableAttribute("WrappingHighScore", "0", true),
                new ReplaceableAttribute("ChaseHighScore", "0", true)));
        sdb.batchPutAttributes(new BatchPutAttributesRequest(myDomain, newPlayers));
        System.out.println("	" + humanPlayer.getName());
        System.out.println("	" + humanPlayer.getPassword());
        System.out.println("DONE Registering new Player: " + humanPlayer.getName() + "\n");
    }
    
    public List<HumanPlayer> getAllHumanPlayers() {
        System.out.println("START Gettting Scores\n");
    	List<HumanPlayer> top10HighScores = new ArrayList<HumanPlayer>();
    	String selectExpression = "select * from `" + myDomain + "`";
        SelectRequest selectRequest = new SelectRequest(selectExpression);
        for (Item item : sdb.select(selectRequest).getItems()) {
        	HumanPlayer newHumanPlayer = new HumanPlayer();
        	System.out.println("	Player From Database");
            for (Attribute attribute : item.getAttributes()) {
            	if (attribute.getName().equals("Name")) {
            		newHumanPlayer.setName(attribute.getValue());
            	} else if (attribute.getName().equals("Password")) {
            		newHumanPlayer.setPassword(attribute.getValue());
            	} else if (attribute.getName().equals("NormalHighScore")) {
            		newHumanPlayer.setNormalHighScore(new Integer(attribute.getValue()));
            	} else if (attribute.getName().equals("WrappingHighScore")) {
            		newHumanPlayer.setWrappingHighScore(new Integer(attribute.getValue()));
            	} else if (attribute.getName().equals("ChaseHighScore")) {
            		newHumanPlayer.setChaseHighScore(new Integer(attribute.getValue()));
            	} 
            	System.out.println("	" + attribute.getValue());
            }
        	System.out.println();
        	top10HighScores.add(newHumanPlayer);
        }
        System.out.println("DONE Gettting Scores\n");
        return top10HighScores;
    	
    }
    
    public void setHighScore(HumanPlayer humanPlayer) {
    	System.out.println("START setting High Score\n");
    	String selectExpression = "select * from `" + myDomain + "` where Name = '" + humanPlayer.getName() + "'";
        SelectRequest selectRequest = new SelectRequest(selectExpression);
        for (Item item : sdb.select(selectRequest).getItems()) {
        	System.out.println("Updating High Scores on " + item.getName());
        	List<ReplaceableAttribute> replaceableAttributes = new ArrayList<ReplaceableAttribute>();
            replaceableAttributes.add(new ReplaceableAttribute("NormalHighScore", humanPlayer.getNormalHighScoreString(), true));
        	System.out.println(humanPlayer.getNormalHighScoreString());
            replaceableAttributes.add(new ReplaceableAttribute("WrappingHighScore", humanPlayer.getWrappingHighScoreString(), true));
        	System.out.println(humanPlayer.getWrappingHighScoreString());
            replaceableAttributes.add(new ReplaceableAttribute("ChaseHighScore", humanPlayer.getChaseHighScoreString(), true));
        	System.out.println(humanPlayer.getChaseHighScoreString());
            sdb.putAttributes(new PutAttributesRequest(myDomain, item.getName(), replaceableAttributes));
        	System.out.println();
        }
    	System.out.println("DONE setting High Score\n");   	
    }
    
    public List<HumanPlayer> getPlayer(String name) {
    	System.out.println("Getting player named: " + name);
    	List<HumanPlayer> matchingPlayers = new ArrayList<HumanPlayer>();
    	String selectExpression = "select * from `" + myDomain + "` where Name = '" + name + "'";
    	SelectRequest selectRequest = new SelectRequest(selectExpression);
        for (Item item : sdb.select(selectRequest).getItems()) {
        	System.out.println("     Found a player named: " + name);
        	HumanPlayer newHumanPlayer = new HumanPlayer();
            for (Attribute attribute : item.getAttributes()) {
            	if (attribute.getName().equals("Name")) {
            		newHumanPlayer.setName(attribute.getValue());
            	} else if (attribute.getName().equals("Password")) {
            		newHumanPlayer.setPassword(attribute.getValue());
            	} else if (attribute.getName().equals("NormalHighScore")) {
            		newHumanPlayer.setNormalHighScore(new Integer(attribute.getValue()));
            	} else if (attribute.getName().equals("WrappingHighScore")) {
            		newHumanPlayer.setWrappingHighScore(new Integer(attribute.getValue()));
            	} else if (attribute.getName().equals("ChaseHighScore")) {
            		newHumanPlayer.setChaseHighScore(new Integer(attribute.getValue()));
            	} 
            	System.out.println(attribute.getValue());
            }
        	System.out.println();
        	matchingPlayers.add(newHumanPlayer);
        }
        return matchingPlayers;
    }
}
