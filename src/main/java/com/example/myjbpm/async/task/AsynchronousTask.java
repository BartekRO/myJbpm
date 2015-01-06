package com.example.myjbpm.async.task;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.myjbpm.AsyncTaskService;
import com.example.myjbpm.ProcessService;
import com.example.myjbpm.entity.AsyncTask;

public abstract class AsynchronousTask {
	
	@Autowired
	private AsyncTaskService asyncTaskService;
	
	@Autowired
	private ProcessService processService;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor={RuntimeException.class})
	public void executeInternal(Long taskId) {
		AsyncTask asyncTask = asyncTaskService.getAsyncTask(taskId);
		
		Map<String, Object> result = execute();
		
		Object fact = getFactToUpdate();
		if (fact != null) {
			processService.completeWorkItemAndUpdateSession(asyncTask.getSessionId(), asyncTask.getWorkItemId(), fact);
		} else {
			processService.completeWorkItem(asyncTask.getSessionId(), asyncTask.getWorkItemId());
		}
		asyncTaskService.completeTask(asyncTask);
	}
	
	protected abstract Map<String, Object> execute();
	
	protected abstract Object getFactToUpdate();
}
