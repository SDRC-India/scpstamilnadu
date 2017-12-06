package org.sdrc.scpstamilnadu.repository.springdata;

import org.sdrc.scpstamilnadu.domain.Role;
import org.sdrc.scpstamilnadu.repository.RoleRepository;
import org.springframework.data.repository.RepositoryDefinition;


@RepositoryDefinition(domainClass=Role.class,idClass=Integer.class)
public interface SpringDataRoleRepository extends RoleRepository{

}
