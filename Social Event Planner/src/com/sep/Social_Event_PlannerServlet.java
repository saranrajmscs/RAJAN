package com.sep;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Social_Event_PlannerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		resp.getWriter().println("<h1><font face=\"Verdana\"> Social Event Planner </font></h1><br/>");
		// Test Message for DVCS
		resp.getWriter().println("<font face=\"Verdana\"> This is the first servlet. Our logic will be built on this</font><br/> ");
	}
}
