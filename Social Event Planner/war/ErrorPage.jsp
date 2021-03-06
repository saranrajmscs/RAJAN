<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.http.HttpSession" %>    
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="java.util.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Social Event Planner</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="../css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 20px;
        padding-bottom: 60px;
      }

      /* Custom container */
      .container {
        margin: 0 auto;
        max-width: 1000px;
      }
      .container > hr {
        margin: 60px 0;
      }

      /* Main marketing message and sign up button */
      .jumbotron {
        margin: 80px 0;
        text-align: center;
      }
      .jumbotron h1 {
        font-size: 100px;
        line-height: 1;
      }
      .jumbotron .lead {
        font-size: 24px;
        line-height: 1.25;
      }
      .jumbotron .btn {
        font-size: 21px;
        padding: 14px 24px;
      }

      /* Supporting marketing content */
      .marketing {
        margin: 60px 0;
      }
      .marketing p + h4 {
        margin-top: 28px;
      }


      /* Customize the navbar links to be fill the entire space of the .navbar */
      .navbar .navbar-inner {
        padding: 0;
      }
      .navbar .nav {
        margin: 0;
        display: table;
        width: 100%;
      }
      .navbar .nav li {
        display: table-cell;
        width: 1%;
        float: none;
      }
      .navbar .nav li a {
        font-weight: bold;
        text-align: center;
        border-left: 1px solid rgba(255,255,255,.75);
        border-right: 1px solid rgba(0,0,0,.1);
      }
      .navbar .nav li:first-child a {
        border-left: 0;
        border-radius: 3px 0 0 3px;
      }
      .navbar .nav li:last-child a {
        border-right: 0;
        border-radius: 0 3px 3px 0;
      }
    </style>
    

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../ico/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../ico/apple-touch-icon-72-precomposed.png">
                    <link rel="apple-touch-icon-precomposed" href="../ico/apple-touch-icon-57-precomposed.png">
                                   
  </head>

  <body>

    <div class="container">

      <div class="masthead">
        <h2 class="muted">Social Event Planner</h2>
        <div class="navbar">
          <div class="navbar-inner">
            <div class="container">
              <ul class="nav">
                <li ><a href="../index.html">Home</a></li>
                <li><a href="/GoogleOpenIdHandler?method=signInMethod">Sign In</a></li>
                <li class="active"><a href="./CreateInvitationMain.jsp">Create Invitation</a></li>
                <!--  li><a href="#">Photo Album</a></li-->
                <li><a href="/RecommendGiftHandler?method=getEventsAndInvitees">Recommend Gift</a></li>
                <!-- li><a href="#">Contact</a></li-->
              </ul>
            </div>
          </div>
        </div><!-- /.navbar -->
      </div>
		

 <div class="row-fluid">
 	<div class="span12"><strong class="text-error">User denied Access to his / her Social Site !!!</strong></div>
 </div>

 <%

	session.setAttribute("EVENT_ID", "");
	session.setAttribute("EVENT_NAME", "");
	session.setAttribute("EVENT_DESC", "");
	session.setAttribute("EVENT_DT", "");
	session.setAttribute("EVENT_TIME", "");
	session.setAttribute("EVENT_LOC", "");
	session.setAttribute("EVENT_TYPE", "");
	session.setAttribute("EVENT_HOST", "");
	session.setAttribute("EVENT_HSCON", "");	
	session.setAttribute("INVITEE_LIST", new Vector());
 %>
 


 
  <div class="control-group">
    <div class="controls" align=center>
      <a href="./invitation/CreateInvitationMain.jsp" >Need to plan more events?</a>
    </div>
  </div>
  </div>
			


      

      <hr>

      <div class="footer">
        <p>&copy; Summer 2013 - Social Computing Science</p>
      </div>

    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    
    
    

  </body>
</html>
