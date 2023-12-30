package com.hireling.entites;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String countryName;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "countryWorker")
	private List<Worker> worker = new ArrayList<>();

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "countryState")
	private List<State> state = new ArrayList<>();
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "countryDistric")
	private List<Distric> distric = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public List<Worker> getWorker() {
		return worker;
	}

	public void setWorker(List<Worker> worker) {
		this.worker = worker;
	}

	public List<State> getState() {
		return state;
	}

	public void setState(List<State> state) {
		this.state = state;
	}

	public List<Distric> getDistric() {
		return distric;
	}

	public void setDistric(List<Distric> distric) {
		this.distric = distric;
	}
	@Override
	public String toString() {
	    return "Country [id=" + id + ", countryName=" + countryName + "]";
	}
	

	
}
