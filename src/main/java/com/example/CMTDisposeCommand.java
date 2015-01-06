package com.example;

import org.drools.command.Context;
import org.drools.command.impl.GenericCommand;
import org.drools.command.impl.KnowledgeCommandContext;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class CMTDisposeCommand implements GenericCommand<Void> {

    private static final long serialVersionUID = 1L;
    
    
    public CMTDisposeCommand() {
        
    }

    public Void execute(Context context) {
        
        final StatefulKnowledgeSession ksession = ((KnowledgeCommandContext) context).getStatefulKnowledgesession();
        try {
        	if ( TransactionSynchronizationManager.isActualTransactionActive() ) {
        		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
					public void afterCompletion(int status) {
						ksession.dispose();
					}
        		});
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }   
        
        return null;
    }


}
