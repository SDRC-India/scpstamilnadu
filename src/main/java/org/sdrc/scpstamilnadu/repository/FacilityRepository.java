package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.Facility;

public interface FacilityRepository {
	
	
	public List<Facility> findAll();
	
	public Facility save(Facility facility);

	public List<Facility> findAllByCciTypeIsNull();

}
