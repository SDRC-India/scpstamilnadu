package org.sdrc.scpstamilnadu.repository.springdata;

import org.sdrc.scpstamilnadu.domain.Subgroup;
import org.sdrc.scpstamilnadu.repository.SubgroupRepository;
import org.springframework.data.repository.RepositoryDefinition;


@RepositoryDefinition(domainClass=Subgroup.class,idClass=Integer.class)
public interface SpringDataSubgroupRepository  extends SubgroupRepository{

}
