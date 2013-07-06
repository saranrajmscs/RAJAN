package com.sep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.socio.recomgift.model.*;
public class RecommendGiftHandler extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Process FB response, if it is HTTP GET
		processResponse(request, response);
	}
	
	protected void processResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		
		 String code = (String) request.getParameter("code");
	        if (code == null || code.equals("")) {
	            // an error occurred, handle this
	        }

	        String token = null;
	        try {
	        	String g = "https://graph.facebook.com/oauth/access_token?client_id=322861041181267&redirect_uri=" + URLEncoder.encode("http://localhost:8080/FacebookTestOauth/testfb.htm", "UTF-8") + "&client_secret=e6fb605e445367d5711b16f8c1c93274&code=" + code;
	            URL u = new URL(g);
	            URLConnection c = u.openConnection();
	            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
	            String inputLine;
	            StringBuffer b = new StringBuffer();
	            while ((inputLine = in.readLine()) != null)
	                b.append(inputLine + "\n");            
	            in.close();
	            token = b.toString();
	            if (token.startsWith("{"))
	                throw new Exception("error on requesting token: " + token + " with code: " + code);
	        } catch (Exception e) {
	                // an error occurred, handle this
	        	System.out.println("Exception has occured in token generation"+e);
	        	e.printStackTrace();
	        }

	        String graphForFriendsList = null;
	        String graph = null;
	        try {
	            String friendsListGraph = "https://graph.facebook.com/me/friends?" + token;
	            URL friendsListURL = new URL(friendsListGraph);
	            URLConnection friendsListURLConnection = friendsListURL.openConnection();
	            BufferedReader friendsListBufferInput = new BufferedReader(new InputStreamReader(friendsListURLConnection.getInputStream()));
	            String inputLineForFriendsList;
	            StringBuffer srtingBufferForFriendsList = new StringBuffer();
	            while ((inputLineForFriendsList = friendsListBufferInput.readLine()) != null)
	            	srtingBufferForFriendsList.append(inputLineForFriendsList + "\n");            
	            friendsListBufferInput.close();
	            graphForFriendsList = srtingBufferForFriendsList.toString();
	            
	            System.out.println("graph"+graphForFriendsList);
	            System.out.println("graph"+graphForFriendsList);
	            
	        } catch (Exception e) {
	                // an error occurred, handle this
	        	System.out.println("exception has occured during get call of profile"+e);
	        	e.printStackTrace();
	        }

	        List<Friend> friendList = new ArrayList<Friend>();
	        List<FriendLike> friendLikes = new ArrayList<FriendLike>();
	        List<FriendInterest> friendInterestsList = new ArrayList<FriendInterest>();
	        int friendListize;
	        Friend friend = null;
	        FriendLike friendLike = null;
	        FriendInterest friendInterest = null;
	        try {
	        	JSONObject json = new JSONObject(graphForFriendsList);
	        	friendListize = json.getJSONArray("data").length();
	            System.out.println(friendListize);
	            for(int i=0;i<friendListize;i++) {
	            	friend = new Friend();
	            	friend.setFriendId(json.getJSONArray("data").getJSONObject(i).getString("id"));
	            	friend.setFriendName(json.getJSONArray("data").getJSONObject(i).getString("name"));
	            	String g = "https://graph.facebook.com/"+json.getJSONArray("data").getJSONObject(i).getString("id")+"/likes?" + token;
		            URL u = new URL(g);
		            URLConnection c = u.openConnection();
		            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
		            String inputLine;
		            StringBuffer b = new StringBuffer();
		            while ((inputLine = in.readLine()) != null)
		                b.append(inputLine + "\n");            
		            in.close();
		            graph = b.toString();
		            JSONObject jsonLikes = new JSONObject(graph);
		            for(int k=0;k<jsonLikes.getJSONArray("data").length();k++) {
		            	friendLike = new FriendLike();
		            	friendLike.setLikeCategory(jsonLikes.getJSONArray("data").getJSONObject(k).getString("category"));
		            	friendLike.setLikeName(jsonLikes.getJSONArray("data").getJSONObject(k).getString("name"));
		            	friendLike.setLikeId(jsonLikes.getJSONArray("data").getJSONObject(k).getString("id"));
		            	friendLikes.add(friendLike);
		            	friend.setFriendLikes(friendLikes);
		            	}
		            String userInterestsGraph = "https://graph.facebook.com/"+json.getJSONArray("data").getJSONObject(i).getString("id")+"/interests?" + token;
		            URL userInterestsUrl = new URL(userInterestsGraph);
		            URLConnection userInterestsConn = userInterestsUrl.openConnection();
		            BufferedReader userInterestsInput = new BufferedReader(new InputStreamReader(userInterestsConn.getInputStream()));
		            String userInterestInputLine;
		            StringBuffer userInterestBuffer = new StringBuffer();
		            while ((userInterestInputLine = userInterestsInput.readLine()) != null)
		            	userInterestBuffer.append(userInterestInputLine + "\n");            
		            userInterestsInput.close();
		            String userInterestsGraphUse = userInterestBuffer.toString();
		            JSONObject jsonInterests = new JSONObject(userInterestsGraphUse);
		            for(int k=0;k<jsonInterests.getJSONArray("data").length();k++) {
		            	friendInterest = new FriendInterest();
		            	friendInterest.setInterestCategory(jsonInterests.getJSONArray("data").getJSONObject(k).getString("category"));
		            	friendInterest.setInterestName(jsonInterests.getJSONArray("data").getJSONObject(k).getString("name"));
		            	friendInterest.setInterestId(jsonInterests.getJSONArray("data").getJSONObject(k).getString("id"));
		            	friendInterestsList.add(friendInterest);
		            	friend.setFriendInterestsList(friendInterestsList);
		            	System.out.println(jsonLikes.getJSONArray("data").getJSONObject(k).getString("category"));
		            	}
		            friendList.add(friend);
		            //System.out.println(userInterestsGraphUse);
	            }
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    

	}
	
	
}
