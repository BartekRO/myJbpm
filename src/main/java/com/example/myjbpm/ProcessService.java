package com.example.myjbpm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.CMTDisposeCommand;
import com.example.myjbpm.entity.repository.SessionInfoRepository;
import com.example.myjbpm.fact.SomeFact;
import com.example.myjbpm.handler.MyWorkItemHandler;

@Service
public class ProcessService {

	@Autowired
	KnowledgeBase kbase;

	@Autowired
	EnvironmentHolder environmentHolder;

	@Autowired
	SessionInfoRepository sessionInfoRepository;
	
	@Autowired(required = false)
	List<MyWorkItemHandler> handlers;
	
	@Transactional()
	public StatefulKnowledgeSession createNewSession() {
		StatefulKnowledgeSession session = JPAKnowledgeService
				.newStatefulKnowledgeSession(kbase, null, environmentHolder.getEnvironment());
			
		addHandlersAndListener(session);
		disposeSessionAfterTransaction(session);
		return session;
	}
	
	@Transactional
	public void startProcess() {
		StatefulKnowledgeSession session = createNewSession();
		Map<String, Object> params = new HashMap<String, Object>();
		List<Long> list = new ArrayList<Long>();
		for (long i = 0; i < 1000; i++) {
			list.add(i);
		}
		params.put("ids", list);
		params.put("sessionId", (long) session.getId());
		session.startProcess("testProcess", params);
	}
	
	@Transactional
	public void completeWorkItemAndUpdateSession(Long sessionId, Long workItemId, Object fact) {
		StatefulKnowledgeSession session = getSession(sessionId);
		
		for (FactHandle handle : session.getFactHandles()) {
			session.retract(handle);
		}
		session.insert(fact);
		
		session.getWorkItemManager().completeWorkItem(workItemId, null);
	}
	
	@Transactional
	public void completeWorkItem(Long sessionId, Long workItemId) {
		StatefulKnowledgeSession session = getSession(sessionId);
		session.getWorkItemManager().completeWorkItem(workItemId, null);
	}
	
	@Transactional
	public void updateSession(Long sessionId, SomeFact fact) {
		StatefulKnowledgeSession session = getSession(sessionId);
		
		for (FactHandle handle : session.getFactHandles()) {
			session.retract(handle);
		}
		session.insert(fact);
	}
	
	@Transactional
	public void deleteSession(Long sessionId) {
		sessionInfoRepository.delete(sessionId.intValue());
	}

	private StatefulKnowledgeSession getSession(Long sessionId) {
		StatefulKnowledgeSession session = JPAKnowledgeService.loadStatefulKnowledgeSession(sessionId.intValue(), kbase,
				null, environmentHolder.getEnvironment());
		addHandlersAndListener(session);
		disposeSessionAfterTransaction(session);
		return session;
	}
	
	private void addHandlersAndListener(StatefulKnowledgeSession session) {
		session.addEventListener(new MultiSessionProcessListener(this));
		if (handlers != null) {
			for (MyWorkItemHandler handler : handlers) {
				session.getWorkItemManager().registerWorkItemHandler(handler.getClass().getName(), handler);
			}
		}
	}
	
	private void disposeSessionAfterTransaction(StatefulKnowledgeSession session) {
		session.execute(new CMTDisposeCommand());
	}
	
}
