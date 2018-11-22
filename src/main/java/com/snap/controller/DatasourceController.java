package com.snap.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.snap.dao.DatasourceDAO;
import com.snap.model.AlarmDefinition;
import com.snap.model.Datasource;

@Controller
public class DatasourceController {

	@Autowired
	DatasourceDAO datasourceDAO;
	String loginusername;
	private Logger logger=LoggerFactory.getLogger(DatasourceController.class);
	
	@RequestMapping(value="datasources" , method=RequestMethod.GET)
	public ModelAndView datasourceList(@ModelAttribute AlarmDefinition alarm) 
	{
		List<Datasource>  datasources = datasourceDAO.listDatasources();
		ModelAndView model=new ModelAndView("datasources");
		model.addObject("datasources",datasources);
		return model;
	}
	
	
	@RequestMapping(value="create-datasource", method=RequestMethod.GET)
	public String createDatasourceUI(){
		return "create-datasource";
	}
	

	
	@RequestMapping(value = "/create-datasource", method = RequestMethod.POST)
	public ModelAndView createDatasource(@RequestParam String name, 
			@RequestParam String url, 
			@RequestParam String database,
			@RequestParam String username,
			@RequestParam String password,
			HttpServletRequest request) {

		ModelAndView model=new ModelAndView();
		HttpSession session=request.getSession(false);
		if(session!=null)
		{
		loginusername=(String)session.getAttribute("username");
		}
		if(loginusername!=null)
		{
		datasourceDAO.addDatasource(name, url, database, username, password);
		logger.info("datasource created");
		return new ModelAndView("redirect:datasources"); 
		}
		else
		{
			model.addObject("msg","Session not set");
		    model.setViewName("login");
		}
		
		//return "redirect:datasources";
		return model;

	}

	
	@RequestMapping(value="modify-datasource", method=RequestMethod.GET)
	public ModelAndView modifyDatasourceUI(@RequestParam int id){
		ModelAndView mv = new ModelAndView("modify-datasource");
		Datasource datasource = datasourceDAO.getDatasource(id);
		mv.addObject("datasource", datasource);
		return mv;
	}
	

	
	@RequestMapping(value = "/modify-datasource", method = RequestMethod.POST)
	public ModelAndView modfiyDatasource(
			@RequestParam int id,
			@RequestParam String name, 
			@RequestParam String url, 
			@RequestParam String database,
			@RequestParam String username,
			@RequestParam String password,
			HttpServletRequest request) {

		ModelAndView model=new ModelAndView();
        HttpSession session=request.getSession(false);
        if(session!=null)
        {
        	loginusername=(String)session.getAttribute("username");
        }
        if(loginusername!=null)
        {
		datasourceDAO.modifyDatasource(id, name, url, database, username, password);
		return new ModelAndView("redirect:datasources");
        }
        else
        {
        	model.addObject("msg","Session not set");
        	model.setViewName("login");
        }
        return model;
		
		

	}
	
	@RequestMapping(value="delete-datasource", method=RequestMethod.GET)
	public ModelAndView deleteDatasourceUI(@RequestParam int id, HttpServletRequest request){
		ModelAndView model=new ModelAndView();
		try{
			HttpSession session=request.getSession(false);
			
			if(session!=null)
			{
				loginusername=(String)session.getAttribute("username");
			}
			if(loginusername!=null)
			{
		datasourceDAO.deleteDatasource(id);
		logger.info("datasource deleted");
		return new ModelAndView("redirect:datasources");
			}
			else{
				model.addObject("msg","Session not set");
				model.setViewName("login");
			}
		}catch (Exception e) {
		     
		}
		return model;
	}
	
}
