package com.hireling.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hireling.entites.Distric;
import com.hireling.entites.State;

public interface DistricRepo extends JpaRepository<Distric, Integer> {
	@Query("SELECT d FROM Distric d WHERE d.state = :state")
	List<Distric> findByStateDistrics(@Param("state") State state);
	

}
