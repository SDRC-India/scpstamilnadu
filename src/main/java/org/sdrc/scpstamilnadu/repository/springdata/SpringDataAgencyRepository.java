package org.sdrc.scpstamilnadu.repository.springdata;

import org.sdrc.scpstamilnadu.domain.Agency;
import org.sdrc.scpstamilnadu.repository.AgencyRepository;
import org.springframework.data.repository.RepositoryDefinition;


@RepositoryDefinition(domainClass=Agency.class,idClass=Integer.class)
public interface SpringDataAgencyRepository extends AgencyRepository {

}
