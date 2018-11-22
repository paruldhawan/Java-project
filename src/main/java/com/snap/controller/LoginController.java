package com.snap.controller;

import java.net.ConnectException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	String viewName;
	private Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = "checkUser", method = RequestMethod.GET)
	public ModelAndView verifyUserGet(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(false);
		if(session!=null)
		{
			String username = (String) session.getAttribute("username");
			model.addObject("username",username);
			if (username != null) {
				viewName = "home";
			} else {
				viewName = "InvalidCreds";
			}
		}
		else{
			model.addObject("msg", "Session not set.");
			viewName="error";
		}

		model.setViewName(viewName);
		return model;

	}

	@RequestMapping(value = "checkUser", method = RequestMethod.POST)
	public ModelAndView verifyUser(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		LdapAuth ldapAuth = new LdapAuth();
		HttpSession session = null;
		int responseCode = 0;
		try {
			responseCode = ldapAuth.verifyUser(username, password);
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (responseCode == 1) {
			session = request.getSession();
			viewName = "home";
			session.setAttribute("username", username);
			model.addObject("username",username);
			logger.info("welcome to alert engine     "+username);
		} else {
			logger.error("Your Credentials are wrong.verify it");
			viewName = "InvalidCreds";
		}
		model.setViewName(viewName);
		return model;
	}

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession();
		String username=(String) session.getAttribute("username");
		if (session.getAttribute("username") != null) {
			model.addObject("username",username);
			model.setViewName("home");
		}

		else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = { "/GOTOHOME**" })
	public ModelAndView homePage(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(false);
		if(session!=null)
		{

			String username=(String) session.getAttribute("username");
			//if(role.equals("user"))
				model.addObject("username",username);
		model.setViewName("home");
		}
		else{
			model.setViewName("login"); 
		}
		return model;
	}

	@RequestMapping(value = { "/GOTOLOGIN**" })
	public ModelAndView loginPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		return model;
	}

	@RequestMapping(value = { "/GOTOLOGOUT**" })
	public ModelAndView logoutControl(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession();
		session.setAttribute("username", null);
		session.setAttribute("role", null);
		session.invalidate();
		model.setViewName("login");
		return model;
	}
	
	

}
