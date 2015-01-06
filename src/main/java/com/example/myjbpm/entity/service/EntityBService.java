package com.example.myjbpm.entity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myjbpm.entity.EntityB;
import com.example.myjbpm.entity.repository.EntityBRepository;

@Service
@Transactional
public class EntityBService {
	
	@Autowired
	EntityBRepository entityBRepository;
	
	public void save(Long workItemId) {
		EntityB entitya = new EntityB();
		entitya.setWorkItemId(workItemId);
		entityBRepository.save(entitya);
	}
}
