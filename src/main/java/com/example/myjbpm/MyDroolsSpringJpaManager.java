package com.example.myjbpm;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.drools.persistence.PersistenceContext;
import org.drools.persistence.jpa.JpaPersistenceContext;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.jbpm.persistence.JpaProcessPersistenceContext;
import org.jbpm.persistence.ProcessPersistenceContext;
import org.jbpm.persistence.ProcessPersistenceContextManager;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class MyDroolsSpringJpaManager  implements
		ProcessPersistenceContextManager {
	
	private EntityManagerFactory emf;
		
	public MyDroolsSpringJpaManager(Environment env) {
	    this.emf = (EntityManagerFactory) env.get( EnvironmentName.ENTITY_MANAGER_FACTORY );
	}
	
	public PersistenceContext getApplicationScopedPersistenceContext() {
		
		EntityManager appScopedEntityManager = getEntityManager(true);
	    if ( TransactionSynchronizationManager.isActualTransactionActive() ) {
	        appScopedEntityManager.joinTransaction();
	    }
	    return new JpaPersistenceContext( appScopedEntityManager );
	}

	private EntityManager getEntityManager(boolean createIfNull) {
		EntityManager appScopedEntityManager = null;
		EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager.getResource( this.emf );
        if ( emHolder == null && createIfNull) {
            appScopedEntityManager = this.emf.createEntityManager();
            emHolder = new EntityManagerHolder( appScopedEntityManager );
            TransactionSynchronizationManager.bindResource( this.emf,
                                                            emHolder );
        } else {
            appScopedEntityManager = emHolder.getEntityManager();
        }
		return appScopedEntityManager;
	}
	
	public PersistenceContext getCommandScopedPersistenceContext() {
	    return new JpaPersistenceContext( getEntityManager(true));
	}
	
	public void beginCommandScopedEntityManager() {
	    
	}
	
	public void endCommandScopedEntityManager() {
	    
	}
	
	public void dispose() {
//        TransactionSynchronizationManager.unbindResource( this.emf );
//        
//        EntityManager appScopedEntityManager = getEntityManager(false);
//        if ( appScopedEntityManager != null && appScopedEntityManager.isOpen() ) {
//            appScopedEntityManager.close();
//        }
//        this.endCommandScopedEntityManager();
	}
	
	public ProcessPersistenceContext getProcessPersistenceContext() {
	    return new JpaProcessPersistenceContext( getEntityManager(true) );
	}

	public void clearPersistenceContext() {
		EntityManager appScopedEntityManager = getEntityManager(false);
        if ( appScopedEntityManager != null && appScopedEntityManager.isOpen() ) {
        	appScopedEntityManager.clear();
        }
		
	}
	

}
