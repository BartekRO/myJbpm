package com.example.myjbpm;

import javax.persistence.EntityManagerFactory;

import org.drools.KnowledgeBaseFactory;
import org.drools.container.spring.beans.persistence.DroolsSpringTransactionManager;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentHolder implements InitializingBean {

	private Environment env;
	
	@Autowired
	EntityManagerFactory entityManagerFactory;
	

	@Autowired
	JpaTransactionManager transactionManager;
	
	public void afterPropertiesSet() throws Exception {
		env = KnowledgeBaseFactory.newEnvironment();
		env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, entityManagerFactory);
		env.set(EnvironmentName.TRANSACTION_MANAGER, new DroolsSpringTransactionManager(transactionManager));
		env.set(EnvironmentName.PERSISTENCE_CONTEXT_MANAGER, new MyDroolsSpringJpaManager(env));
	}
	
	public Environment getEnvironment() {
		return env;
	}

	
}
