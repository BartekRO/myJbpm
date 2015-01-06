package com.example.myjbpm.async.task;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myjbpm.entity.service.EntityCService;

@Service
public class DummyAsyncTask2 extends AsynchronousTask {

	@Autowired
	private EntityCService entityCService;
	

	protected Map<String, Object> execute() {
		entityCService.save(0l);
		return null;
	}
	
	protected Object getFactToUpdate() {
		return null;
	}

}
