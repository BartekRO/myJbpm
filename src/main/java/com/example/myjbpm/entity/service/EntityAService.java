package com.example.myjbpm.entity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myjbpm.entity.EntityA;
import com.example.myjbpm.entity.repository.EntityARepository;

@Service
@Transactional
public class EntityAService {
	
	@Autowired
	EntityARepository entityARepository;
	
	public void save(Long workItemId) {
		EntityA entitya = new EntityA();
		entitya.setWorkItemId(workItemId);
		entityARepository.save(entitya);
	}
}
