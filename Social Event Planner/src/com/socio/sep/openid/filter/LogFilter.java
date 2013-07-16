package com.socio.sep.openid.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		if(req.getParameter("method")!= null && "signInMethod".equals(req.getParameter("method"))) {
		
		} else {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		if (session != null && session.getAttribute("USER_ID") != null) {
			
			} else {
				response.sendRedirect("http://soceveplnr.appspot.com/GoogleOpenIdHandler?method=signInMethod");
				//response.sendRedirect("http://localhost:8888/GoogleOpenIdHandler?method=signInMethod");
			}
		}
		 chain.doFilter(req, res);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
