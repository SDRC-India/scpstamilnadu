package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.FacilityUserMapping;
import org.sdrc.scpstamilnadu.domain.User;

public interface FacilityUserMappingRepository {
	
	
	public <S extends FacilityUserMapping> List<S> save(Iterable<S> mappings);
	
	public FacilityUserMapping findByFacilityUserMappingId(int facilityUserMappingId);
	
	
	public FacilityUserMapping findByUser(User user);

	public FacilityUserMapping save(FacilityUserMapping mappings);
	
}
