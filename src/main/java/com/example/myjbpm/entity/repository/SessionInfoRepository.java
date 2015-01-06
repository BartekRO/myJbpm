package com.example.myjbpm.entity.repository;

import org.drools.persistence.info.SessionInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SessionInfoRepository  extends JpaRepository<SessionInfo, Integer> {

}
