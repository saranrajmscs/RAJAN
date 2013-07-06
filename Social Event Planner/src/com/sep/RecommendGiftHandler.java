package com.sep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

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
	        	String g = "https://graph.facebook.com/oauth/access_token?client_id=496480123766256&redirect_uri=http://soceveplnr.appspot.com/RecommendGiftHandler&client_secret=1887e3c3807cdcb5a2c81de6ef4feaed&code="+code;
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

	        String facebookId;
	        String firstName;
	        String middleNames;
	        String lastName;
	        String email;
	        int friendListize;
	       // Gender gender;
	        String gender;
	        try {
	        	
	           JSONObject json = new JSONObject(graphForFriendsList);
	           friendListize = json.getJSONArray("data").length();
	            System.out.println(friendListize);
	            for(int i=0;i<friendListize;i++) {
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
		            System.out.println("The likes of: "+json.getJSONArray("data").getJSONObject(i).getString("id"));
		            System.out.println("graph"+graph);
		            System.out.println("----------------------------------------------------------------------------------------------------------------------");
	            }
	            
	        } catch (Exception e) {
	            // an error occurred, handle this
	        	e.printStackTrace();
	        }

	    // ...
	    // Here you can use the request and response objects like:
	    // response.setContentType("application/pdf");
	    // response.getOutputStream().write(...);

	
	}
	
	
}
