<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.net.URLConnection"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.PrintStream"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="org.json.JSONObject"%>
<%@ page import="org.apache.commons.io.IOUtils"%>
<%@ page import="javax.xml.parsers.DocumentBuilderFactory"%>
<%@ page import="javax.xml.parsers.DocumentBuilder"%>
<%@ page import="org.w3c.dom.Document"%>
<%@ page import="org.w3c.dom.NodeList"%>
<%@ page import="org.w3c.dom.Node"%>
<%@ page import="org.w3c.dom.Element"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello</title>
</head>
<body>
	<%
		try {

		if ("SECRET12345".equals(request.getParameter("state"))) {
			String URLString = "https://www.linkedin.com/uas/oauth2/accessToken?grant_type=authorization_code"
					+ "&code="
					+ request.getParameter("code")
					+ "&redirect_uri=http://soceveplnr.appspot.com/linkedInConn/ConnectLinkedin.jsp"
					//+ "&redirect_uri=http://localhost:8888/linkedInConn/ConnectLinkedin.jsp"
					+ "&client_id=w76aej9ln16a"
					+ "&client_secret=Oc4bC7SQ7vXhjfJI";
			System.out.println(URLString);
			URL url = new URL(URLString);
			URLConnection urlConn = url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			urlConn.connect();
			String resp = IOUtils.toString(urlConn.getInputStream());
			System.out.println(resp);
			JSONObject accessResp = new JSONObject(resp);
			String accessToken = (String) accessResp.get("access_token");
			System.out.println(accessToken);
			if (accessToken != null) {
				String pplURL = "https://api.linkedin.com/v1/people/~/connections?oauth2_access_token="

+ accessToken;
				URL url1 = new URL(pplURL);
				URLConnection urlConn1 = url1.openConnection();
				urlConn1.setDoInput(true);
				urlConn1.setDoOutput(true);
				urlConn1.setReadTimeout(10000);
				urlConn1.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				urlConn1.connect();
				InputStream is = urlConn1.getInputStream();
				//String resp1 = IOUtils.toString(urlConn1.getInputStream());
				//System.out.println(resp1);
				
				Vector<Properties> vec = new Vector<Properties>();
				
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(is);
			 
				doc.getDocumentElement().normalize();
				System.out.println("Root element :"	+ doc.getDocumentElement().getNodeName());
				System.out.println("No of connections: " + doc.getDocumentElement().getAttribute("total"));
				
				NodeList nList = doc.getElementsByTagName("person");
				
				for (int temp = 0; temp < nList.getLength(); temp++) {
					 
					Node nNode = nList.item(temp);
			 
					System.out.println("\nCurrent Element :" + nNode.getNodeName());
			 
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			 			
						Element eElement = (Element) nNode;
			 
						System.out.println("Linked id : " + eElement.getElementsByTagName("id").item(0).getTextContent());
						System.out.println("First Name : " + eElement.getElementsByTagName("first-name").item(0).getTextContent());
						System.out.println("Last Name : " + eElement.getElementsByTagName("last-name").item(0).getTextContent());
						Properties prop = new Properties();
						prop.setProperty("ID", eElement.getElementsByTagName("id").item(0).getTextContent());
						prop.setProperty("FIRST_NAME", eElement.getElementsByTagName("first-name").item(0).getTextContent());
						prop.setProperty("LAST_NAME", eElement.getElementsByTagName("last-name").item(0).getTextContent());
						vec.addElement(prop);
					}
				}
				session.setAttribute("LINKEDIN", vec);
				String redirect = response.encodeRedirectURL(request.getContextPath() + "./LinkedInConnections.jsp" );
				response.sendRedirect(redirect);
	%>
	<%
		} else {
			
		}
		}
		} catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect("http://soceveplnr.appspot.com/LoginErrorPage.jsp");
		}
	%>

</body>
</html>