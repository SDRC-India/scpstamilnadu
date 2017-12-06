package org.sdrc.scpstamilnadu.repository.springdata;

import org.sdrc.scpstamilnadu.domain.IndicatorUnitSubgroup;
import org.sdrc.scpstamilnadu.repository.IndicatorUnitSubgroupRepository;
import org.springframework.data.repository.RepositoryDefinition;


@RepositoryDefinition(domainClass=IndicatorUnitSubgroup.class,idClass=Integer.class)
public interface SpringDataIndicatorUnitSubgroupRepository extends IndicatorUnitSubgroupRepository{
	
	

}
