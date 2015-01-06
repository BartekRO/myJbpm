package com.example.myjbpm.entity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myjbpm.entity.EntityC;
import com.example.myjbpm.entity.repository.EntityCRepository;

@Service
@Transactional
public class EntityCService {
	
	@Autowired
	EntityCRepository entityCRepository;
	
	public void save(Long workItemId) throws Exception {
		EntityC entitya = new EntityC();
		entitya.setWorkItemId(workItemId);
		entityCRepository.save(entitya);
	}
}
