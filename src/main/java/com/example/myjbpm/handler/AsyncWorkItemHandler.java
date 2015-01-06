package com.example.myjbpm.handler;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myjbpm.AsyncTaskService;

@Service
public class AsyncWorkItemHandler implements MyWorkItemHandler {

	@Autowired
	private AsyncTaskService asyncTaskService;
	
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		
		Long sessionId = (Long) workItem.getParameter("SessionId");
		String asyncHandlerName = (String) workItem.getParameter("AsyncHandlerName");
		
		asyncTaskService.addAsyncTask(asyncHandlerName, sessionId, workItem.getId());
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		manager.abortWorkItem(workItem.getId());
	}
	
	

}
