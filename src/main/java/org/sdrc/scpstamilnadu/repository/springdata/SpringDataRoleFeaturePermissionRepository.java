package org.sdrc.scpstamilnadu.repository.springdata;

import org.sdrc.scpstamilnadu.domain.RoleFeaturePermissionScheme;
import org.sdrc.scpstamilnadu.repository.RoleFeaturePermissionRepository;
import org.springframework.data.repository.RepositoryDefinition;


@RepositoryDefinition(domainClass=RoleFeaturePermissionScheme.class,idClass=Integer.class)
public interface SpringDataRoleFeaturePermissionRepository extends RoleFeaturePermissionRepository{

}
