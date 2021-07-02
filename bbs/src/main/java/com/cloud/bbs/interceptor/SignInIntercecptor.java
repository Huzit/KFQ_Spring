package com.cloud.bbs.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SignInIntercecptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		//POST방식으로 왔을 때
		if(!request.getMethod().equalsIgnoreCase("GET")) {
			return true;
		}
		//GET방식으로 왔을 때
		else {
			//세션에 ID가 없을 시
			if(request.getSession().getAttribute("id")==null) {
				response.sendRedirect("login.bbs");
				return false;
			}
			return true;
		}
	}
}
