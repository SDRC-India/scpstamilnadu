package org.sdrc.scpstamilnadu.repository.springdata;

import org.sdrc.scpstamilnadu.domain.FacilityUserMapping;
import org.sdrc.scpstamilnadu.repository.FacilityUserMappingRepository;
import org.springframework.data.repository.RepositoryDefinition;


@RepositoryDefinition(domainClass=FacilityUserMapping.class,idClass=Integer.class)
public interface SpringDataFacilityUserMappingRepository extends FacilityUserMappingRepository{

}
