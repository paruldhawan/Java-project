package com.snap.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;

import com.snap.dao.AlarmDao;
import com.snap.dao.DatasourceDAO;
import com.snap.dao.NotificationDao;
import com.snap.kapacitor.KapacitorClient;
import com.snap.kapacitor.KapacitorException;
import com.snap.kapacitor.TaskType;
import com.snap.kapacitor.TickGenerator;
import com.snap.kapacitor.model.DbRp;
import com.snap.model.AlarmDefinition;
import com.snap.model.Notification;
import com.snap.properties.KapacitorConfig;

import ch.qos.logback.core.util.SystemInfo;

@Controller
public class AlarmContoller {

	@Autowired
	private AlarmDao alarmDao;
	KapacitorConfig kapconfig=new KapacitorConfig();
	private  Logger logger=LoggerFactory.getLogger(AlarmContoller.class);


	@Autowired
	private NotificationDao notificationDao;

	@Autowired
	private DatasourceDAO datasourceDao;
	String username;

	String kapacitorIP = kapconfig.getIP("kapacitor.IP");
	KapacitorClient kapacitorClient = new KapacitorClient(kapacitorIP);

	@RequestMapping(value = "alarm-definitions", method = RequestMethod.GET)
	public ModelAndView listAlarmDefintions() {
		
		
		List<AlarmDefinition> alarmDefinitions = alarmDao.listAlarmDefinitions();
		if (alarmDefinitions == null) {
			alarmDefinitions = new LinkedList<AlarmDefinition>();
		}
		for (AlarmDefinition ad : alarmDefinitions) {
			logger.info("alarmdefinitions   "+ad);
			
		}

		ModelAndView model = new ModelAndView("alarm-definitions");
		model.addObject("alarmdefinitions", alarmDefinitions);

		return model;
	}

	@RequestMapping(value = "create-alarmdefinition", method = RequestMethod.GET)
	public ModelAndView createAlarmDefinitionUI(HttpServletRequest req) {

		String loggerVar=null;
		HttpSession session=req.getSession();
		if(session!=null)
		{
			username=(String)session.getAttribute("username");
		}

		List<String> datasources = datasourceDao.getNameList();
		for(int i=0;i<datasources.size();i++)
			loggerVar=datasources.get(i);
			logger.info(loggerVar);
		List<String> notificaitonNames = notificationDao.getNameList(username);
		
		
		//System.out.println("User is " + username + " " + notificaitonNames);
		logger.info("username  "+username+"notifications"+loggerVar);
		ModelAndView model = new ModelAndView("create-alarmdefinition");
		model.addObject("datasources", datasources);

		model.addObject("notifications", notificaitonNames);
		
		
		return model;
	}

	@RequestMapping(value = "create-alarmdefinition", method = RequestMethod.POST)
	public ModelAndView createAlarmDefinition(@RequestParam String name, @RequestParam String description,
			@RequestParam String query, @RequestParam String notificationName, @RequestParam boolean enabled,
			HttpServletRequest req) throws KapacitorException {
		ModelAndView model=new ModelAndView();
		HttpSession session = req.getSession();
		if (session != null) {
			username = (String) session.getAttribute("username");
		}
		
		System.out.println(notificationName);
        if(username!=null)
        {
		List<String> notificationList = Arrays.asList(notificationName.split(","));

       logger.info("The query is    "+query);
		System.out.println("Query is " + query);
		List<Notification> notifications = notificationDao.getNotification(notificationList, username);

		TickGenerator tc = new TickGenerator(name, query, datasourceDao, notifications);
		String tick = tc.generateTick();

		
		logger.info("the tick is    "+tick);
		
		System.out.println("Tick is " + tick);

		List<DbRp> dbrps = tc.getDBRP();
		boolean success = kapacitorClient.createTask(name, TaskType.BATCH, dbrps, tick, enabled); // createTask(name,
																									// TaskType.BATCH,
																									// dbrps,
																									// tick,
																									// true);
		if (success) {
			alarmDao.addAlarmDefinition(name, description, query, enabled, 0);
		}
		return new ModelAndView("redirect:alarm-definitions");
        }
        else
        {
        	model.addObject("msg","Session not set");
        	model.setViewName("login");
        }
        
        return model;

	}

	
	
	@RequestMapping(value="/modify-alarmdefinition", method=RequestMethod.GET)
	public ModelAndView modifyAlarmUI()
	{
		ModelAndView mv=new ModelAndView();
		mv.setViewName("underConstruction");
		
		return mv;
		/*ModelAndView model=new ModelAndView("modify-alarmdefinition");
		AlarmDefinition alarmdef=alarmDao.getAlarmDefinition(id);
		model.addObject("alarms", alarmdef );
		return model;*/
		
	}

	@RequestMapping(value = "/modify-alarmdefinition", method = RequestMethod.POST)
	public ModelAndView modifyAlarm(
			/*@RequestParam int id,
			@RequestParam String alarmname,
			@RequestParam String desc,
			@RequestParam String query*/
			)
	{
		ModelAndView mv=new ModelAndView();
		mv.setViewName("underConstruction");
		return mv;
		/*alarmDao.modifyAlarmDefinition(alarmname, desc ,query, false, id);
		return "redirect: alarm-definitions" ;	*/	
	}

	@RequestMapping(value = "delete-alarmdefinition", method = RequestMethod.GET)
	public ModelAndView deleteAlarmUI(@RequestParam int id, HttpServletRequest request) throws KapacitorException {
		
			String name = alarmDao.getAlarmDefinition(id).getName();
			
			
			ModelAndView model=new ModelAndView();
			HttpSession session=request.getSession(false);
			if(session!=null)
			{
				username=(String) session.getAttribute("username");
			}
			if(username!=null)
			{
			alarmDao.deleteAlarmDefinition(id);
			kapacitorClient.deleteTask(name);
			return new ModelAndView("redirect:alarm-definitions");
			}
			else
			{
				model.addObject("msg","Session not set");
				model.setViewName("login");
			}

		return model;
	}
	
	
	@ExceptionHandler({KapacitorException.class})
	public ModelAndView onKapacitorError(Exception e){
		
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("msg",e.getMessage());
		return mv;
	}
}
