package com.example.myjbpm.async.task.executor;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.myjbpm.AsyncTaskService;
import com.example.myjbpm.async.task.AsynchronousTask;
import com.example.myjbpm.entity.AsyncTask;

public class AsyncTaskExecutor implements BeanFactoryAware {
	
	Logger logger = Logger.getLogger(AsyncTaskExecutor.class);
	
	@Autowired
	private AsyncTaskService asyncTaskService;
	
	private int divisor;
	private int reminder;
	private BeanFactory factory;
	
	public AsyncTaskExecutor(int divisor, int reminder) {
		this.divisor = divisor;
		this.reminder = reminder;
	}
	
	public void doExecute() {
		for (AsyncTask asyncTask : asyncTaskService.getAsyncTasksToExecute(divisor, reminder)) {
			AsynchronousTask task = (AsynchronousTask) factory.getBean(asyncTask.getServiceName());
			
			try {
				task.executeInternal(asyncTask.getId());
			} catch (Throwable t) {
				logger.error("Error ", t);
			}
		}
	}

	public void setBeanFactory(BeanFactory factory) throws BeansException {
		this.factory = factory;
	}

}
