package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.Agency;

public interface AgencyRepository {
	
	
	public Agency findByAgencyName(String agencyName);
	
	public Agency findByEncryptedAgencyId(String encryptedAgencyId);
	
	public Agency findByAgencyId(int agencyId);
	
	public List<Agency> findAll();
	

}
