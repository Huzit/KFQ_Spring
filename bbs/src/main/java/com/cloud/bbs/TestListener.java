package com.cloud.bbs;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TestListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//
		ServletContext slc = sce.getServletContext();
		System.out.println(slc.getInitParameter("contextConfigLocation"));
		System.out.println("동작하나요");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
}