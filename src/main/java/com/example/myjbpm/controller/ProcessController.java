package com.example.myjbpm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.myjbpm.ProcessService;
import com.example.myjbpm.entity.service.EntityCService;


@Controller
public class ProcessController {
	
	@Autowired
	private ProcessService newProcessService;
	
	@Autowired EntityCService entityCService;
	
	@RequestMapping(value="/newSession", method= RequestMethod.GET)
	public @ResponseBody String createNewSession() {
		newProcessService.createNewSession();
		
		try {
			entityCService.save(1l);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping(value="/startProcess1", method= RequestMethod.GET)
	public @ResponseBody String startProcess1() {
		for (int i = 0; i < 1; i++) {
			newProcessService.startProcess();
		}
		return "";
	}
}
