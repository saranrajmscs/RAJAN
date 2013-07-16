package com.sep;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LinkedInHandler
 */

public class LinkedInHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LinkedInHandler() {
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
		storeInvitees(request, response);
	}
	
	protected void storeInvitees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(true);
		Integer event_id = (Integer) session.getAttribute("EVENT_ID");
		event_id = event_id == null ? new Integer(0) : event_id;
		PrintWriter    servletOutput = response.getWriter(); 
		servletOutput.print("Event_id from Session " + event_id.intValue());
		

		Vector<Properties> invVec = (Vector<Properties>) session.getAttribute("INVITEE_LIST"); 
		invVec = invVec == null ? new Vector<Properties>() : invVec;
		Enumeration qryStrEnum = request.getParameterNames();
		while(qryStrEnum.hasMoreElements()) {
			String qryStr = (String) qryStrEnum.nextElement();
			if(qryStr.startsWith("LI")) {
				String qryVal = request.getParameter(qryStr);
				System.out.println(qryVal);
				servletOutput.print(qryVal);
				StringTokenizer sToken = new StringTokenizer(qryVal, "$");
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
				    stmt.setString(3, "LinkedIn");
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
				    	prop.setProperty("INVITEE_SRC", "LinkedIn");
				    	invVec.addElement(prop);
				    }
				
					stmt.close();
					c.close();
				}
				catch(Exception e) {
					e.printStackTrace();
					response.sendRedirect("http://soceveplnr.appspot.com/LoginErrorPage.jsp");
				}
				finally {

				}
			} else {
				System.out.println(qryStr + " - " + request.getParameter(qryStr));
			}
		}
		session.setAttribute("INVITEE_LIST", invVec);
		String redirect = response.encodeRedirectURL(request.getContextPath() + "./invitation/InvitationConfirmation.jsp" );
		response.sendRedirect(redirect);		
	}

}
