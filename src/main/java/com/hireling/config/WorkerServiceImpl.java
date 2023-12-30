package com.hireling.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hireling.dao.WorkerRepo;
import com.hireling.entites.Worker;

public class WorkerServiceImpl implements UserDetailsService {

	@Autowired
	private WorkerRepo workerRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Worker worker=workerRepo.getWorkerByUserNameWorker(username);
		
		if(worker==null) {
			throw new UsernameNotFoundException("Could Not found Worker !!");
		}
		
		CustomWorkerDetails customWorkerDetails=new CustomWorkerDetails(worker);
		
		return customWorkerDetails;
	}

}
