package com.hackathon.Events;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomInterceptor implements HandlerInterceptor {
	
	private final String[] arr = {
			"/loginPage",
			"/logout",
			"/timeout",
			"/login"
	};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			if(request.getSession(false)==null && !omitSession(request.getRequestURI())) {
				String url = request.getScheme() + "://" + request.getServerName() +  
			             ":" + request.getServerPort()+"/timeout";
				response.sendRedirect(url);
				return false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean omitSession(String url) {
		for(String a: arr) {
			if(a.equals(url)) {
				return true;
			}
		}
		return false;
	}
}