package com.example.myjbpm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity

public class AsyncTask {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "WORK_ITEM_ID")
	private Long workItemId;

	@Column(name = "SESSION_ID")
	private Long sessionId;

	@Column(name = "SERVICE_NAME")
	private String serviceName;
	
	@Column(name = "STATE")
	@Enumerated(EnumType.STRING)
	private AsyncTaskState state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWorkItemId() {
		return workItemId;
	}

	public void setWorkItemId(Long workItemId) {
		this.workItemId = workItemId;
	}

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public AsyncTaskState getState() {
		return state;
	}

	public void setState(AsyncTaskState state) {
		this.state = state;
	}

	
}
