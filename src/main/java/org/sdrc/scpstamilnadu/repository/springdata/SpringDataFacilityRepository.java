package org.sdrc.scpstamilnadu.repository.springdata;

import org.sdrc.scpstamilnadu.domain.Facility;
import org.sdrc.scpstamilnadu.repository.FacilityRepository;
import org.springframework.data.repository.RepositoryDefinition;


@RepositoryDefinition(domainClass=Facility.class,idClass=Integer.class)
public interface SpringDataFacilityRepository extends FacilityRepository{

}
