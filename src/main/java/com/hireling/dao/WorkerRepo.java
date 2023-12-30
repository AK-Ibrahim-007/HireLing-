package com.hireling.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hireling.entites.Worker;

public interface WorkerRepo extends JpaRepository<Worker, Integer> {
    
	@Query("select w from Worker w where w.email= :email")
	public Worker getWorkerByUserNameWorker(@Param("email") String email);
}
