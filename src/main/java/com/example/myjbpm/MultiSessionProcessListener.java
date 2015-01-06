package com.example.myjbpm;

import org.drools.event.process.DefaultProcessEventListener;
import org.drools.event.process.ProcessCompletedEvent;
import org.drools.event.process.ProcessStartedEvent;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.ruleflow.instance.RuleFlowProcessInstance;

public class MultiSessionProcessListener extends DefaultProcessEventListener {
	
	private ProcessService processService;
	
	public MultiSessionProcessListener(ProcessService processService) {
		this.processService = processService;
	}
	
	@Override
	public void beforeProcessStarted(ProcessStartedEvent event) {
		RuleFlowProcessInstance pi = (RuleFlowProcessInstance) event.getProcessInstance();
		if (pi.getVariable("sessionId") == null) {
			StatefulKnowledgeSession session = processService.createNewSession();
			pi.setVariable("sessionId", Long.valueOf(session.getId()));
		}
	}
	
	
	@Override
	public void afterProcessCompleted(ProcessCompletedEvent event) {
		RuleFlowProcessInstance pi = (RuleFlowProcessInstance) event.getProcessInstance();
		Long sessionId = (Long) pi.getVariable("sessionId");
		processService.deleteSession(sessionId);
	}

}
