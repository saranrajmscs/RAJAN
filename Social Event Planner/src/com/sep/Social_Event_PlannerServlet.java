package com.sep;

import java.io.IOException;
import java.sql.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Social_Event_PlannerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		resp.getWriter().println("<h1><font face=\"Verdana\"> Social Event Planner </font></h1><br/>");
		// Test Message for DVCS
		resp.getWriter().println("<font face=\"Verdana\"> This is the first servlet. Our logic will be built on this</font><br/> ");
		try {
			SEP_DB_Manager sepDB = new SEP_DB_Manager();
		Connection c = SEP_DB_Manager.getConnection();
		System.out.println("CONNECTION : " + c);
		String statement ="select * from USER_MASTER";
	    PreparedStatement stmt = c.prepareStatement(statement);
	      ResultSet rs = stmt.executeQuery();
	      resp.getWriter().println(rs.findColumn("ROW_ID"));
	      resp.getWriter().println(req.getParameter("inputEmail"));
	      
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
