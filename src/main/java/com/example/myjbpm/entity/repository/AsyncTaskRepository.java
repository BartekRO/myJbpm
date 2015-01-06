package com.example.myjbpm.entity.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.myjbpm.entity.AsyncTask;
import com.example.myjbpm.entity.AsyncTaskState;

public interface AsyncTaskRepository extends JpaRepository<AsyncTask, Long> {

	@Query(value = "select at from com.example.myjbpm.entity.AsyncTask at where at.state = :state and mod(at.id, :divisor) = :reminder")
	public List<AsyncTask> findByState(@Param("state")AsyncTaskState state, @Param("divisor")int divisor, @Param("reminder")int reminder, Pageable pageable);
	
}
