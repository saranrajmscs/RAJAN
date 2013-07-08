<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.net.URLConnection"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="org.json.JSONObject"%>
<%@ page import="org.apache.commons.io.IOUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello</title>
</head>
<body>
	<%
		if ("SECRET12345".equals(request.getParameter("state"))) {
			String URLString = "https://www.linkedin.com/uas/oauth2/accessToken?grant_type=authorization_code"
					+ "&code="
					+ request.getParameter("code")
					+ "&redirect_uri=http://soceveplnr.appspot.com/ConnectLinkedin.jsp"
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
				urlConn1.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				urlConn1.connect();
				String resp1 = IOUtils.toString(urlConn1.getInputStream());
				System.out.println(resp1);
	%><%=resp1%>
	<%
		}
		}
	%>

</body>
</html>