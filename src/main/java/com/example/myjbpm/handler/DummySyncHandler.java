package com.example.myjbpm.handler;

import java.util.concurrent.TimeUnit;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myjbpm.entity.service.EntityAService;
import com.example.myjbpm.entity.service.EntityBService;

@Service
public class DummySyncHandler implements MyWorkItemHandler {

	@Autowired
	private EntityAService entityAService;
	
	@Autowired
	private EntityBService entityBService;
	
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		
		entityAService.save(workItem.getId());
		try {
			TimeUnit.MILLISECONDS.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entityBService.save(workItem.getId());
		
		manager.completeWorkItem(workItem.getId(), null);
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		manager.abortWorkItem(workItem.getId());
	}

}
