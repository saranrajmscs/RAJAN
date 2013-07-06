<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.net.URLEncoder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Try the FB login</title>
</head>
<body>
<form method="post" action="/RecommendGiftHandler">
<%
    String fbURL = "http://www.facebook.com/dialog/oauth?client_id=496480123766256&redirect_uri=" + URLEncoder.encode("http://soceveplnr.appspot.com/RecommendGiftHandler") + "&scope=email, read_stream, user_likes,friends_likes";
%>

<a href="<%= fbURL %>"><img src="../img/facebook.png" border="0" /></a>

</form>
</body>
</html>