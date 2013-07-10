package com.sep;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class InvitationHandler
 */

public class InvitationHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvitationHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Process FB response, if it is HTTP GET
		processResponse(request, response);
	}
	
	protected void storeInvitees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(true);
		String event_id = (String) session.getAttribute("EVENT_ID");
		PrintWriter    servletOutput = response.getWriter(); 
		
		Enumeration qryStrEnum = request.getParameterNames();
		while(qryStrEnum.hasMoreElements()) {
			String qryStr = (String) qryStrEnum.nextElement();
			if(qryStr.startsWith("FB")) {
				String qryVal = request.getParameter(qryStr);
				System.out.println(qryVal);
				servletOutput.print(qryVal);
			}
		}
	
	}
	
	protected void processResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter    servletOutput = response.getWriter(); 
		//servletOutput.print("<html><body><h1>The Facebook Friends are: </h1><p />");
		
		HttpSession session = request.getSession(true);
		String userId = (String) session.getAttribute("USER_ID");
		userId = userId == null ? "12345" : userId.trim();		
		
		// Retrieve the State and auth code FB response 
		String stStr = request.getParameter("state");
		stStr = stStr == null ? "" : stStr;
		
		String authCd = request.getParameter("code");
		authCd = authCd == null ? "" : authCd;
		
		// Exchange Auth token for Access Token
		String fbUrl = "https://graph.facebook.com/oauth/access_token?client_id=496480123766256&redirect_uri=http://soceveplnr.appspot.com/InvitationHandler&client_secret=1887e3c3807cdcb5a2c81de6ef4feaed&code="+authCd;
		Vector<String> respVec = submitHTTPRequest(fbUrl);
		Enumeration<String> respEnum = respVec.elements();
		while(respEnum.hasMoreElements()){
			String str = respEnum.nextElement();
			str = str == null ? "" : str;
			if(str.indexOf("access_token") != -1 && str.indexOf("expires") != -1) {
				StringTokenizer sToken = new StringTokenizer(str, "&");
				String aToken = sToken.nextToken();
				String expStr = sToken.nextToken();
				
				//servletOutput.println("Token1 : " + aToken);
				//servletOutput.println("Token2 : " + expStr);
				
				StringTokenizer accToken = new StringTokenizer(aToken, "=");
				String acTok1 = accToken.nextToken();
				String acTok2 = accToken.nextToken();
				
				//servletOutput.println("Access Token : " + acTok2);
				//servletOutput.println("Expires : " + expStr);
				
				//String fbInsToken = "https://graph.facebook.com/debug_token?input_token=" + acTok2 + "&access_token=1887e3c3807cdcb5a2c81de6ef4feaed";

				// Inspect Access Token
				String fbInsToken = "https://graph.facebook.com/debug_token?input_token=" + acTok2 + "&access_token=" + acTok2;
				Vector<String> respVec1 = submitHTTPRequest(fbInsToken);
				//servletOutput.println("Vector : " + respVec1);
				String jsonStr = respVec1.elementAt(0);
				//servletOutput.println("JSON Str: " + jsonStr);
				//JSONObject jobj = new JSONObject(jsonStr);
				//JSONObject jobj2 = jobj.getJSONObject("data");
				//servletOutput.println("<br/>");
				//servletOutput.println("App Id: " + jobj2.get("app_id"));
				//servletOutput.println("Scopes: " + jobj2.get("scopes"));
				//servletOutput.println("Issued At: " + jobj2.get("issued_at"));
				//servletOutput.println("Expires At: " + jobj2.get("expires_at"));
				//servletOutput.println("Application: " + jobj2.get("application"));
				
				// Call FB API to get Friends list.
				String fbAPICall = "https://graph.facebook.com/me?fields=id,name,friends&access_token=" + acTok2;
				//String fbAPICall = "https://graph.facebook.com/me?fields=id,name,friends";
				try {
					SEP_DB_Manager dbMgr = new SEP_DB_Manager();
					Connection c = SEP_DB_Manager.getConnection();
					System.out.println("CONNECTION : " + c);
					String statement = "UPDATE USER_MASTER SET FB_TOKEN = ? WHERE ROW_ID = '" + userId + "'";
				    PreparedStatement stmt = c.prepareStatement(statement);
				    stmt.setString(1, acTok2);
				    int retVal = stmt.executeUpdate();
				    System.out.println("Access Token Updated "+ retVal);
				    stmt.close();
				    c.close();
				    
					
					URL url = new URL(fbAPICall);
					HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
					
					httpConn.setRequestMethod("GET");
					httpConn.connect();
					
					// reads the FB response and retrieve the friends list
					BufferedReader buffRdr = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			
					String line = "";
					String respLine = "";
					while (null != (line = buffRdr.readLine())){
						respLine = respLine + line;
					}
					//servletOutput.println("Response " +respLine);
					
					JSONObject frnd = new JSONObject(respLine);
			
					JSONObject j2 = (JSONObject) frnd.get("friends");
					JSONArray j3 = (JSONArray) j2.get("data");
								
					
					try {
						session.setAttribute("FBFRNDJSON", j3.toString());
						//System.out.println("SESSION "+session);
					}
					catch(Exception e) {
						e.printStackTrace(servletOutput);
					}
					/*for(int i = 0; i < j3.length() ; i++ ) {
						JSONObject jo = j3.getJSONObject(i);
						servletOutput.println("<br/>" + (i+1) + " - FB ID " + jo.get("id") + " - " + jo.get("name"));
					}*/
					buffRdr.close();
					String redirect = response.encodeRedirectURL(request.getContextPath() + "./invitation/FriendsList.jsp" );
					response.sendRedirect(redirect);
					//response.sendRedirect("./invitation/FriendsList.jsp");
					//this.getServletConfig().getServletContext().getRequestDispatcher("./invitation/FriendsList.jsp");
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				finally {
					
				}
			}
		}
		
		//servletOutput.print("</body></html>");
		//servletOutput.close();
	}

	
	// This method submits HTTP request for the given URL and return the response as vector Strings
	protected Vector<String> submitHTTPRequest(String urlStr) 
	{
		Vector<String> resp = new Vector<String>();
		try {
			
			URL url = new URL(urlStr);
			URLConnection urlConn = url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConn.connect();
			
			// reads the FB response and will be used for the API calls
			BufferedReader buffRdr = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
	
			String line = null;
			while (null != (line = buffRdr.readLine())){
			    resp.add(line);
			}
			buffRdr.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
		}
		return resp;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String hidCtrl = request.getParameter("HiddenControl");
		hidCtrl = hidCtrl == null? "" : hidCtrl; 
		HttpSession session = request.getSession(true);
		String userId = (String) session.getAttribute("USER_ID");
		userId = userId == null ? "12345" : userId.trim();
		
		if(hidCtrl.equals("Step1")) 
		{
			String eveName = request.getParameter("EventName");
			eveName = eveName == null ? "" : eveName.trim();

			String eveType = request.getParameter("EventType");
			eveType = eveType == null ? "" : eveType.trim();
			
			String eveDt = request.getParameter("EventDate");
			eveDt = eveDt == null ? "" : eveDt.trim();
			
			String eveTm = request.getParameter("EventTime");
			eveTm = eveTm == null ? "" : eveTm.trim();
			
			String eveLoc = request.getParameter("EventLocation");
			eveLoc = eveLoc == null ? "" : eveLoc.trim();
			
			String eveHost = request.getParameter("EventHost");
			eveHost = eveHost == null ? "" : eveHost.trim();
			
			String eveHsCon = request.getParameter("HostContactDetails");
			eveHsCon = eveHsCon == null ? "" : eveHsCon.trim();
			
			String eveDesc = request.getParameter("EventDesc");
			eveDesc = eveDesc == null ? "" : eveDesc.trim();
			Connection c = null;
			PreparedStatement stmt = null;
			try {
				SEP_DB_Manager dbMgr = new SEP_DB_Manager();
				c = SEP_DB_Manager.getConnection();
				System.out.println("CONNECTION : " + c);
				String statement = "INSERT INTO EVENT_MASTER (EVENT_NAME, EVENT_DESC, EVENT_DATE, EVENT_TIME, EVENT_LOCATION, EVENT_TYPE, EVENT_HOST, EVENT_HOST_CONTACT, USER_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			    stmt = c.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
			    stmt.setString(1, eveName);
			    stmt.setString(2, eveDesc);
			    stmt.setString(3, eveDt);
			    stmt.setString(4, eveTm);
			    stmt.setString(5, eveLoc);
			    stmt.setString(6, eveType);
			    stmt.setString(7, eveHost);
			    stmt.setString(8, eveHsCon);
			    stmt.setString(9, userId);
			    
			    int retVal = stmt.executeUpdate();
			    System.out.println("DB Update " + retVal);
			    
			    ResultSet rs = stmt.getGeneratedKeys();
			    if(rs.next()) {
			    	int event_id = rs.getInt(1);
			    	System.out.println("Inserted Event Id is " + event_id);
			    	session.setAttribute("EVENT_ID", event_id);
			    }
			    
			    
			    
			    if(retVal != 0){
			    	if(eveType.equals("Personal")) {
						// Redirects to confirmation page to initiate Facebook connection
						response.sendRedirect("./invitation/SocialConfirmation.jsp");			    		
			    	}
			    	else {
						// Redirects to confirmation page to initiate Facebook connection
						response.sendRedirect("./linkedInConn/ProfessionalSocialConfirmation.jsp");			    					    		
			    	}
			    }
			    else {
			    	
			    }
				stmt.close();
				c.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally {

			}
		} else if(hidCtrl.equals("Step2")){
			
			String socType = request.getParameter("SocialType");
			socType = socType == null ? "": socType.trim();
			if(socType.equals("Facebook")) {
				// Redirects to FB Login / Authorization page
				String fbUrl = "https://www.facebook.com/dialog/oauth?client_id=496480123766256&redirect_uri=http://soceveplnr.appspot.com/InvitationHandler&state=FBResponse&scope=email, read_friendlists, friends_about_me, user_about_me";
				response.sendRedirect(fbUrl);				
			}

			if(socType.equals("GPlus")) {
				// Redirects to G+ Login / Authorization page
								
			}
			/*response.setContentType("text/html");
			response.getWriter().println("<h1><font face=\"Verdana\"> Social Event Planner </font></h1><br/>");
			// Test Message for DVCS
			response.getWriter().println("<font face=\"Verdana\"> This is the first servlet. Our logic will be built on this</font><br/> ");
			try {
				SEP_DB_Manager sepDB = new SEP_DB_Manager();
			Connection c = SEP_DB_Manager.getConnection();
			System.out.println("CONNECTION : " + c);
			String statement ="select * from USER_MASTER";
		    PreparedStatement stmt = c.prepareStatement(statement);
		      ResultSet rs = stmt.executeQuery();
		      response.getWriter().println(rs.findColumn("ROW_ID"));
		      response.getWriter().println(request.getParameter("inputEmail"));
		      response.getWriter().println(request.getParameter("EventType"));
		      
			}
			catch(Exception e) {
				e.printStackTrace();
			}*/					
		} else if(hidCtrl.equals("FriendSelect")) {
			// Store Selected Invitees into DB.
			storeInvitees(request, response);
		}
		else {
			// Process FB responses
			processResponse(request, response);
		}
	}
}
