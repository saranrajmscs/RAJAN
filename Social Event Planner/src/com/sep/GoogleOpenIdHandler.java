package com.sep;

import java.io.BufferedReader;

import com.socio.sep.openid.model.UserDO;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class GoogleOpenIdHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	//@Override 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("method")!= null && "signInMethod".equals(request.getParameter("method"))) {
			signInMethod(request, response);
		} else {
		processResponse(request, response);
		}
	}

	private void signInMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	String uri = "";
	try {
		final URL url = new URL("https://www.google.com/accounts/o8/id");

		final InputStream in = new BufferedInputStream(url.openStream());

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		Document doc = db.parse(in);
		NodeList nodeList = doc.getElementsByTagName("URI");

			for (int i = 0; nodeList != null && i < nodeList.getLength(); i++) {
				Node checkNode = nodeList.item(i);
				if (checkNode.getNodeName().equalsIgnoreCase("URI")) {
					if(checkNode.getFirstChild()!=null){
					 uri = checkNode.getFirstChild().getNodeValue();
//						System.out.println("URI :::: : " + checkNode.getFirstChild().getNodeValue());
					}
				}
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	String googleFirstURL = uri+
		"?openid.ns=http://specs.openid.net/auth/2.0"+
			"&openid.ns.pape=http://specs.openid.net/extensions/pape/1.0"+
			"&openid.ns.max_auth_age=300"+
			"&openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select"+
			"&openid.identity=http://specs.openid.net/auth/2.0/identifier_select"+
			"&openid.return_to=http://soceveplnr.appspot.com/GoogleOpenIdHandler"+
			//"&openid.return_to=http://localhost:8888/GoogleOpenIdHandler"+
			"&openid.realm=http://soceveplnr.appspot.com/GoogleOpenIdHandler"+
			//"&openid.realm=http://localhost:8888/GoogleOpenIdHandler"+
			"&openid.assoc_handle=ABSmpf6DNMw"+
			"&openid.mode=checkid_setup"+
			"&openid.ui.ns=http://specs.openid.net/extensions/ui/1.0"+
			"&openid.ui.mode=popup"+
			"&openid.ui.icon=true"+
			"&openid.ns.ax=http://openid.net/srv/ax/1.0"+
			"&openid.ax.mode=fetch_request"+
			"&openid.ax.type.email=http://axschema.org/contact/email"+
			"&openid.ax.type.language=http://axschema.org/pref/language"+
			"&openid.ax.type.country=http://axschema.org/contact/country/home"+
			"&openid.ax.type.firstname=http://axschema.org/namePerson/first"+
			"&openid.ax.type.lastname=http://axschema.org/namePerson/last"+
			"&openid.ax.required=email,language,firstname,lastname,country";
		response.sendRedirect(googleFirstURL);
}

	protected void processResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String userCountry = (String) request.getParameter("openid.ext1.value.country");
		String userEmail = (String) request.getParameter("openid.ext1.value.email");
		String userFirstName = (String) request.getParameter("openid.ext1.value.firstname");
		String userLastName = (String) request.getParameter("openid.ext1.value.lastname");
		UserDO userDO = new UserDO();
		userDO.setUserCountry(userCountry);
		userDO.setUserEmail(userEmail);
		userDO.setUserFirstName(userFirstName);
		userDO.setUserLastName(userLastName);
		Connection c = null;
		PreparedStatement stmt =  null, qrySt = null;
		try {
			SEP_DB_Manager dbMgr = new SEP_DB_Manager();
			c = SEP_DB_Manager.getConnection();
			System.out.println("CONNECTION : " + c);
			String sqlQry = "SELECT ROW_ID, EMAIL_ID,FIRST_NAME,LAST_NAME FROM USER_MASTER WHERE EMAIL_ID = ?";
			qrySt = c.prepareStatement(sqlQry);
			qrySt.setString(1, userEmail);
			
			ResultSet userRS = qrySt.executeQuery();
			if(userRS.next()) 
			{
				int usrId = userRS.getInt("ROW_ID");
				String emailId = userRS.getString("EMAIL_ID");
				String statement = "UPDATE USER_MASTER SET EMAIL_ID = ?,FIRST_NAME = ?, LAST_NAME = ?, COUNTRY = ? WHERE ROW_ID = ?";
			    stmt = c.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
			    stmt.setString(1, userDO.getUserEmail());
			    stmt.setString(2, userDO.getUserFirstName());
			    stmt.setString(3, userDO.getUserLastName());
			    stmt.setString(4, userDO.getUserCountry());
			    stmt.setInt(5, usrId);
			    
			    int retVal = stmt.executeUpdate();
		    	session.setAttribute("USER_ID", usrId);
			    session.setAttribute("userFirstName", userDO.getUserFirstName());
			} else {
				String statement = "INSERT INTO USER_MASTER (EMAIL_ID,FIRST_NAME,LAST_NAME,COUNTRY) VALUES(?, ?, ?, ?)";
			    stmt = c.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
			    stmt.setString(1, userDO.getUserEmail());
			    stmt.setString(2, userDO.getUserFirstName());
			    stmt.setString(3, userDO.getUserLastName());
			    stmt.setString(4, userDO.getUserCountry());
			    
			    int retVal = stmt.executeUpdate();
			    ResultSet rs = stmt.getGeneratedKeys();
			    if(rs.next()) {
			    	int user_id = rs.getInt(1);
			    	System.out.println("Inserted User Id is " + user_id);
			    	session.setAttribute("USER_ID", user_id);
				    session.setAttribute("userFirstName", userFirstName);
			    }				
			}
			
			

		    
		    response.sendRedirect("./index_login.jsp");
//		    System.out.println("DB Update " + retVal);
		    stmt.close();
			c.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {

		}
		System.out.println(userDO.getUserEmail());
		System.out.println(userDO.getUserFirstName() + ", " + userDO.getUserLastName());
	}

	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
