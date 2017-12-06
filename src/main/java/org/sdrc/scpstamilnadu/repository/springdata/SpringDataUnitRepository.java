package org.sdrc.scpstamilnadu.repository.springdata;

import org.sdrc.scpstamilnadu.domain.Unit;
import org.sdrc.scpstamilnadu.repository.UnitRepository;
import org.springframework.data.repository.RepositoryDefinition;



@RepositoryDefinition(domainClass=Unit.class,idClass=Integer.class)
public interface SpringDataUnitRepository extends UnitRepository{

}
