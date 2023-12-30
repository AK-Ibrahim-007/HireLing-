package com.hireling.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hireling.entites.Country;

public interface CountryRepo extends JpaRepository<Country, Integer> {

}
