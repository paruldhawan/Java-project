package com.snap.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.snap.dao.NotificationDao;
import com.snap.dao.NotificationDaoImpl;
import com.snap.model.Notification;

@Controller

public class NotificationController {
	String viewName;
	@Autowired
	NotificationDao notificationDao;
	String username;
	private Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = { "/GOTONOTIFICATION**" })
	public ModelAndView notificationControl(HttpServletRequest request) {
		// ModelAndView model=new ModelAndView();
		List<Notification> notifications = notificationDao.getNotificationList();
		// model.setViewName("notification");
		ModelAndView model = new ModelAndView("notification");
		model.addObject("notifications", notifications);
		return model;
	}

	@RequestMapping(value = { "/CreateNotification" })
	public ModelAndView notificationCreation(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.setViewName("CreateNotification");
		return model;
	}

	@RequestMapping(value = "notifications", method = RequestMethod.GET)
	public ModelAndView notificationList() {
		List<Notification> notifications = notificationDao.getNotificationList();
		ModelAndView model = new ModelAndView("notification");
		model.addObject("notifications", notifications);
		return model;
	}

	@RequestMapping(value = "/save")
	public ModelAndView createNotification(@RequestParam String name, @RequestParam String type,
			@RequestParam List<String> address, HttpServletRequest request)

	{
		ModelAndView model=new ModelAndView();
		HttpSession session = request.getSession(false);
		if (session != null) {
			username = (String) session.getAttribute("username");
		}
		if(username!=null)
		{
		notificationDao.addNotification(new Notification(0, name, type, address, username));
		logger.info("notifications created");
		return new ModelAndView("redirect:notifications");
		}
		else
		{
			model.addObject("msg", "Session not set");
			logger.error("session not set");
			model.setViewName("login");
		}
		
		//return new ModelAndView("redirect:notifications");
		return model;
	}

	@RequestMapping(value = "delete-notification", method = RequestMethod.GET)
	public ModelAndView deleteNotificationUI(@RequestParam int id, HttpServletRequest request) {
		ModelAndView model=new ModelAndView();	
		try {
			
			HttpSession session=request.getSession(false);
			if(session!=null)
			{
				username=(String)session.getAttribute("username");
			}
			if(username!=null)
			{
			notificationDao.deleteNotification(id);
			logger.info("notifications are deleted");
			return new ModelAndView("redirect:notifications") ;
			}
			else
			{
				model.addObject("msg","Session not set");
				model.setViewName("login");
			}
		} catch (Exception e) {
		}
		//return "redirect:notifications";
		return model;
	}

	@RequestMapping(value = "modify-notification", method = RequestMethod.GET)
	public ModelAndView modifyNotificationUI(@RequestParam int id) {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("underConstruction");
		return mv;
		/*ModelAndView model = new ModelAndView("modify-notification");
		Notification notification = notificationDao.getNotification(id);
		model.addObject("notification", notification);
		return model;*/

	}

	@RequestMapping(value = "/modify-notification", method = RequestMethod.POST)
	public ModelAndView modifyNotification(@RequestParam int id, @RequestParam String name, @RequestParam String type,
			@RequestParam List<String> address) {
		/*if (notificationDao == null)
			System.out.println("Dao is null");
		notificationDao.modifyNotification(id, name, type, address);
		return "redirect:notifications";*/
		ModelAndView mv=new ModelAndView();
		mv.setViewName("underConstruction");
		return mv;
	}

}
