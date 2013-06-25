package com.sep;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
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
		}		
	}

}
