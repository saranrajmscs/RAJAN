<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.http.HttpSession" %>    
<%@ page import="java.util.*" %>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Social Event Planner</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="css/bootstrap.css" rel="stylesheet">
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
      <script src="js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="ico/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="ico/apple-touch-icon-72-precomposed.png">
                    <link rel="apple-touch-icon-precomposed" href="ico/apple-touch-icon-57-precomposed.png">
                                   
  </head>
<%
HttpSession sess = request.getSession();
    		String userFirstName = (String) sess.getAttribute("userFirstName");
%>
  <body>

    <div class="container">
    <li>Hello: <%=userFirstName %> (if not, <a href="/LogoutServlet">Logout</a>)</li>
      <div class="masthead">
        <h2 class="muted">Social Event Planner</h2>
        <div class="navbar">
          <div class="navbar-inner">
            <div class="container">
              <ul class="nav">
                <li ><a href="#">Home</a></li>
                <li class="disabled"><a href="/GoogleOpenIdHandler?method=signInMethod">Sign In</a></li>
                <li><a href="./invitation/CreateInvitationMain.jsp">Create Invitation</a></li>
                <!-- li><a href="#">Photo Album</a></li-->
                <li><a href="./recommendGift/loginFb.jsp">Recommend Gift</a></li>
                <!-- li><a href="#">Contact</a></li-->
              </ul>
            </div>
          </div>
        </div><!-- /.navbar -->
      </div>

      <!-- Jumbotron -->
      <div class="jumbotron">
        <h2>Got to plan an Event with your Social Friends?</h2>
        <p class="lead">Enables user to login using their Google account (using OpenID)  to Create Invitation for an Event, send invitation (using OAuth) also provide recommendation for return gift (using OAuth) for the Invitees !!! </p>
        <a class="btn btn-large btn-success" href="/GoogleOpenIdHandler?method=signInMethod">Get started today</a>
      </div>

      <hr>

      <!-- Example row of columns -->
      <div class="row-fluid">
        <div class="span4">
          <h3>Registration using OpenID</h3>
          <p>Using Google OpenID methodology, the user's google account will be authenticated and based on the response from google with user details, an account should be created in Social Event Planner (SEP) Website and can be used for subsequent logins.</p>
          <!--  <a class="btn" href="#">View details &raquo;</a></p-->
        </div>
        <div class="span4">
          <h3>Create Invitation</h3>
          <p>Invitation Creation using OAuth with Google+, FB and LinkedIn. This facilitates a user with the screen to select the type of event like Birthday Party, Marriage Party, and Friends Get together, Business Meet or Corporate Workshops. Based on the selected Party Type, the page should be listed with required fields which will collect required data for Events. The friends list will be loaded from their respective social services using OAuth. Also, user should be able to provide email ids for the invitees who are not in above social sites.  </p>
          <!--  p><a class="btn" href="#">View details &raquo;</a></p-->
       </div>
     <!--   <div class="span4">
          <h3>Photo Album</h3>
          <p>Photo Album integration - The photos of the created event either before or after the completion of the event, can be uploaded into our site, user will have an option to integrate with Flickr using OAuth.</p>
          <a class="btn" href="#">View details &raquo;</a>
        </div> -->
        <div class="span4">
          <h3>Recommend Gift</h3>
          <p>Recommendation for Return Gift - The Event organizer should be listed with recommendation for each invitee's return gift based on their interest in social sites. This also uses OAuth service from Social sites to get their interest.</p>
          <!-- p><a class="btn" href="#">View details &raquo;</a></p-->
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
