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
		processResponse(request, response);
	}

	protected void processResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userCountry = (String) request.getParameter("openid.ext1.value.country");
		String userEmail = (String) request.getParameter("openid.ext1.value.email");
		String userFirstName = (String) request.getParameter("openid.ext1.value.firstname");
		String userLastName = (String) request.getParameter("openid.ext1.value.lastname");
		UserDO userDO = new UserDO();
		userDO.setUserCountry(userCountry);
		userDO.setUserEmail(userEmail);
		userDO.setUserFirstName(userFirstName);
		userDO.setUserLastName(userLastName);
		System.out.println(userDO.getUserEmail());
		System.out.println(userDO.getUserFirstName() + ", " + userDO.getUserLastName());
	}

	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			/*String googleFirstURL = "https://www.google.com/accounts/o8/ud"+
					"?openid.ns=http://specs.openid.net/auth/2.0"+
					"&openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select"+
					"&openid.identity=http://specs.openid.net/auth/2.0/identifier_select"+
					"&openid.return_to=http://soceveplnr.appspot.com"+
					"&openid.realm=http://soceveplnr.appspot.com"+
					"&openid.assoc_handle=ABSmpf6DNMw"+
					"&openid.mode=checkid_setup";*/
		
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
//							System.out.println("URI :::: : " + checkNode.getFirstChild().getNodeValue());
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
				"&openid.return_to=http://localhost:8080/OpenIdSample/hello"+
				"&openid.realm=http://localhost:8080/OpenIdSample/hello"+
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
}
