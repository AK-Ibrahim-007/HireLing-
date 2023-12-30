package com.hireling.entites;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class State {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String stateName;
	
    @JsonBackReference
	@ManyToOne 
	private Country countryState;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "state")
	private List<Worker> worker=new ArrayList<>();
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "state")
	private List<Distric> distric=new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	

	public Country getCountryState() {
		return countryState;
	}

	public void setCountryState(Country countryState) {
		this.countryState = countryState;
	}

	public List<Worker> getWorker() {
		return worker;
	}

	public void setWorker(List<Worker> worker) {
		this.worker = worker;
	}

	public List<Distric> getDistric() {
		return distric;
	}

	public void setDistric(List<Distric> distric) {
		this.distric = distric;
	}


	
	

}
