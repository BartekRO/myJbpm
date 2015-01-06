package com.example.myjbpm.async.task;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myjbpm.ProcessService;
import com.example.myjbpm.entity.service.EntityAService;
import com.example.myjbpm.entity.service.EntityBService;
import com.example.myjbpm.fact.SomeFact;

@Service
public class DummyAsyncSleepTask extends AsynchronousTask {

	
	
	@Autowired
	private ProcessService processService;
	
	@Autowired
	private EntityAService entityAService;
	
	@Autowired
	private EntityBService entityBService;
	
	SecureRandom random = new SecureRandom();
	
	
	protected Map<String, Object> execute() {
		
		entityAService.save(random.nextLong());
		try {
			TimeUnit.MILLISECONDS.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entityBService.save(random.nextLong());
		return null;
	}
	
	protected Object getFactToUpdate() {
		SomeFact fact = new SomeFact();
		fact.setNum(random.nextBoolean());
		return fact;
	}
	
	

}
