package com.example.myjbpm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myjbpm.entity.AsyncTask;
import com.example.myjbpm.entity.AsyncTaskState;
import com.example.myjbpm.entity.repository.AsyncTaskRepository;

@Service
@Transactional
public class AsyncTaskService {
	
	@Autowired
	private AsyncTaskRepository asyncTaskRepository;

	public void addAsyncTask(String serviceName, Long sessionId, Long workItemId) {
		AsyncTask asyncTask = new AsyncTask();
		asyncTask.setServiceName(serviceName);
		asyncTask.setSessionId(sessionId);
		asyncTask.setWorkItemId(workItemId);
		asyncTask.setState(AsyncTaskState.CREATED);
		asyncTaskRepository.save(asyncTask);
	}
	
	public List<AsyncTask> getAsyncTasksToExecute(int divisor, int reminder) {
		List<AsyncTask> list = asyncTaskRepository.findByState(AsyncTaskState.CREATED, divisor, reminder, new PageRequest(0, 10));
		return list;
	}
	
	public AsyncTask getAsyncTask(Long taskId) {
		return asyncTaskRepository.findOne(taskId);
	}
	
	public void completeTask(AsyncTask asyncTask) {
		asyncTask.setState(AsyncTaskState.COMPLETED);
		asyncTaskRepository.save(asyncTask);
	}
}
