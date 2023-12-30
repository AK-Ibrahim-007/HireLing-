package com.hireling.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hireling.entites.Country;
import com.hireling.entites.State;

public interface StateRepo extends JpaRepository<State, Integer> {
	  
	public List<State> findByCountryState(Country country);
	
}
