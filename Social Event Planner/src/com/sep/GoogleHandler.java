package com.sep;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
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
import java.util.Properties;
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

public class GoogleHandler extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Process FB response, if it is HTTP GET
		initialProcess(request, response);
	}
	
	protected void processResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try{
			HttpSession session = request.getSession(true);
			PrintWriter    servletOutput = response.getWriter();
			
			Integer userId = (Integer) session.getAttribute("USER_ID");
			userId = userId == null ? new Integer(0) : userId;	
			System.out.println("userId "+userId);
			
			// Retrieve the State and auth code FB response 
			String stStr = request.getParameter("state");
			stStr = stStr == null ? "" : stStr;
			//servletOutput.println("State Jesus " + stStr);
			//servletOutput.println("<br/>");
			
			String authCd = request.getParameter("code");
			authCd = authCd == null ? "" : authCd;
			//servletOutput.println("Auth Token " + authCd);
			//servletOutput.println("<br/>");
			
			// Exchange Auth token for Access Token
			//String gpUrl = "https://accounts.google.com/o/oauth2/auth?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile&state=%2Fprofile&redirect_uri=http://soceveplnr.appspot.com/InvitationHandler&response_type=code&client_id=57827475490.apps.googleusercontent.com&client_secret=zT6PmfvpNm5EqhgBMGxkq26d&approval_prompt=force"+authCd;
			String gpUrl = "https://accounts.google.com/o/oauth2/token";
			String postVal = "code="+ authCd +"&client_id=57827475490.apps.googleusercontent.com&client_secret=zT6PmfvpNm5EqhgBMGxkq26d&redirect_uri=http%3A%2F%2Fsoceveplnr.appspot.com%2FGoogleHandler&grant_type=authorization_code";
			//String postVal = "code="+ authCd +"&client_id=57827475490.apps.googleusercontent.com&client_secret=zT6PmfvpNm5EqhgBMGxkq26d&redirect_uri=http%3A%2F%2Flocalhost:8888%2FGoogleHandler&grant_type=authorization_code";
			//servletOutput.println("URL " + gpUrl);
			//servletOutput.println("<br/>");	
			
			String respVec = submitHTTPRequest(gpUrl, postVal);
			//servletOutput.println("Vector " + respVec);
			//servletOutput.println("<br/>");		
			
			JSONObject jobj = new JSONObject(respVec);
			//servletOutput.println("JSON " + jobj);
			//servletOutput.println("<br/>");	
			if(jobj.has("access_token")) {
				String accessToken = (String) jobj.get("access_token");
				String idToken = (String) jobj.get("id_token");
				Integer expiry = (Integer) jobj.get("expires_in");
				String tokenType = (String) jobj.get("token_type");
				//servletOutput.println("accessToken " + accessToken);
				//servletOutput.println("idToken " + idToken);
				//servletOutput.println("expiry " + expiry);
				//servletOutput.println("tokenType " + tokenType);
				
				SEP_DB_Manager dbMgr = new SEP_DB_Manager();
				Connection c = SEP_DB_Manager.getConnection();
				System.out.println("CONNECTION : " + c);
				String statement = "UPDATE USER_MASTER SET GGL_TOKEN = ? WHERE ROW_ID = " + userId.intValue();
			    PreparedStatement stmt = c.prepareStatement(statement);
			    stmt.setString(1, accessToken);
			    int retVal = stmt.executeUpdate();
			    System.out.println("Access Token Updated "+ retVal);
			    stmt.close();
			    c.close();
				
				String gpUrl1 = "https://www.googleapis.com/plus/v1/people/me/people/visible?access_token=" + accessToken;
				String accTok = tokenType + " " + accessToken;
				String retJson = submitHTTPRequestGet(gpUrl1, accTok);
				//servletOutput.println("Return JSON " + retJson);
				session.setAttribute("GPLUSFRNDS", retJson);
				String redirect = response.encodeRedirectURL(request.getContextPath() + "./invitation/GPlusFriendsList.jsp" );
				response.sendRedirect(redirect);			
			}
			else {
				String redirect = response.encodeRedirectURL(request.getContextPath() + "./ErrorPage.jsp" );
				response.sendRedirect(redirect);
			}
			
		}catch (Exception e) {
			e.printStackTrace();	
		}
		
		
		
		/*Enumeration<String> respEnum = respVec.elements();
		while(respEnum.hasMoreElements()){
			String str = respEnum.nextElement();
			str = str == null ? "" : str;
			servletOutput.println("Str " + str);
			servletOutput.println("<br/>");			
			if(str.indexOf("access_token") != -1 || str.indexOf("expires_in") != -1) {
				StringTokenizer sToken = new StringTokenizer(str, ":");
				String aToken = sToken.nextToken();
				String expStr = sToken.nextToken();
				
				servletOutput.println("Token1 : " + aToken);
				servletOutput.println("<br/>");
				servletOutput.println("Token2 : " + expStr);
				servletOutput.println("<br/>");
				
				/*StringTokenizer accToken = new StringTokenizer(aToken, "=");
				String acTok1 = accToken.nextToken();
				String acTok2 = accToken.nextToken();
				
				servletOutput.println("Access Token : " + acTok2);
				servletOutput.println("<br/>");
				servletOutput.println("Expires : " + expStr);
				servletOutput.println("<br/>");
				

			}
		}*/
		
		//servletOutput.print("</body></html>");
		//servletOutput.close();
	}
	
	protected String submitHTTPRequest(String urlStr, String val) 
	{
		Vector<String> resp = new Vector<String>();
		String jsonStr = "";
		try {
			
			URL url = new URL(urlStr);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			
			httpConn.setRequestMethod("POST");
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			//httpConn.setRequestProperty("User-Agent", "Sociel Event Planner");
			httpConn.connect();
			/*URLConnection urlConn = url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConn.connect();*/
			
			PrintStream ps = new PrintStream(httpConn.getOutputStream());
			ps.print(val);
			
			// reads the FB response and will be used for the API calls
			BufferedReader buffRdr = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
	
			String line = "";
			
			while (null != (line = buffRdr.readLine())){
				System.out.println("Line " + line);
				jsonStr = jsonStr + line;
			    resp.add(line);
			}
			buffRdr.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
		}
		return jsonStr;
		
	}
	
	protected String submitHTTPRequestGet(String urlStr, String acToken) 
	{
		Vector<String> resp = new Vector<String>();
		String jsonStr = "";
		try {
			
			URL url = new URL(urlStr);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			
			httpConn.setRequestMethod("GET");
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			//httpConn.setRequestProperty("Authorization", acToken);
			httpConn.connect();
			/*URLConnection urlConn = url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConn.connect();*/
			
			//PrintStream ps = new PrintStream(httpConn.getOutputStream());
			//ps.print(val);
			
			// reads the FB response and will be used for the API calls
			BufferedReader buffRdr = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
	
			String line = "";
			
			while (null != (line = buffRdr.readLine())){
				System.out.println("Line " + line);
				jsonStr = jsonStr + line;
			    resp.add(line);
			}
			buffRdr.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
		}
		return jsonStr;
		
	}

	protected void storeInvitees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(true);
		Integer event_id = (Integer) session.getAttribute("EVENT_ID");
		event_id = event_id == null ? new Integer(0) : event_id;
		PrintWriter    servletOutput = response.getWriter(); 
		servletOutput.print("Event_id from Session " + event_id.intValue());
		
		String redirectCtrl = request.getParameter("navigationControl");
		System.out.println("redirectCtrl 1 " + redirectCtrl);
		redirectCtrl = redirectCtrl == null ? "" : redirectCtrl.trim();
		System.out.println("redirectCtrl 2 " + redirectCtrl);
		
		Vector<Properties> invVec = (Vector<Properties>) session.getAttribute("INVITEE_LIST"); 
		invVec = invVec == null ? new Vector<Properties>() : invVec;
		Enumeration qryStrEnum = request.getParameterNames();
		while(qryStrEnum.hasMoreElements()) {
			String qryStr = (String) qryStrEnum.nextElement();
			if(qryStr.startsWith("GP")) {
				String qryVal = request.getParameter(qryStr);
				System.out.println(qryVal);
				servletOutput.print(qryVal);
				StringTokenizer sToken = new StringTokenizer(qryVal, "-");
				String fbId = sToken.nextToken();
				String inviteeName = sToken.nextToken();
				Connection c = null;
				PreparedStatement stmt =  null;
				try {
					SEP_DB_Manager dbMgr = new SEP_DB_Manager();
					c = SEP_DB_Manager.getConnection();
					System.out.println("CONNECTION : " + c);
					String statement = "INSERT INTO INVITEE_LIST (INVITEE_NAME, INVITEE_EMAIL_ID, INVITEE_SOURCE_TYPE, EVENT_ID) VALUES(?, ?, ?, ?)";
				    stmt = c.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
				    stmt.setString(1, inviteeName);
				    stmt.setString(2, fbId);
				    stmt.setString(3, "Google+");
				    stmt.setInt(4, event_id.intValue());
				    
				    int retVal = stmt.executeUpdate();
				    System.out.println("DB Update " + retVal);
				    
				    ResultSet rs = stmt.getGeneratedKeys();
				    Properties prop = new Properties();
				    while(rs.next()) {
				    	int invitee_id = rs.getInt(1);
				    	System.out.println("Inserted Invitee Id is " + invitee_id);
				    	prop.setProperty("INVITEE_ID", ""+invitee_id);
				    	prop.setProperty("INVITEE_NAME", inviteeName);
				    	prop.setProperty("INVITEE_FBID", fbId);
				    	prop.setProperty("INVITEE_SRC", "Google+");
				    	invVec.addElement(prop);
				    }
				
					stmt.close();
					c.close();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				finally {

				}
			} else {
				System.out.println(qryStr + " - " + request.getParameter(qryStr));
			}
		}
		session.setAttribute("INVITEE_LIST", invVec);
		String redirect = "";
		if(redirectCtrl.equals("RedirectToFBFriends")) {
			redirect = response.encodeRedirectURL(request.getContextPath() + "/InvitationHandler?HiddenControl=Step2&SocialType=Facebook&DisableButton=True" );
		} else {
			redirect = response.encodeRedirectURL(request.getContextPath() + "./invitation/InvitationConfirmation.jsp" );
		}

		response.sendRedirect(redirect);		
	}
	
	protected void initialProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String hidCtrl = request.getParameter("HiddenControl");
		hidCtrl = hidCtrl == null? "" : hidCtrl; 
		if(hidCtrl.equals("FriendSelect")) {
			// Store Selected Invitees into DB.
			storeInvitees(request, response);
		}
		else {
			processResponse(request, response);
		}	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Process FB response, if it is HTTP GET
		//String googleURL = "https://accounts.google.com/o/oauth2/auth?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile&state=%2Fprofile&redirect_uri=http://soceveplnr.appspot.com/InvitationHandler&response_type=code&client_id=57827475490.apps.googleusercontent.com&client_secret=zT6PmfvpNm5EqhgBMGxkq26d&approval_prompt=force";
		initialProcess(request, response);
		
	}

}
