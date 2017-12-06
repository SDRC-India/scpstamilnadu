package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.Role;
import org.sdrc.scpstamilnadu.domain.RoleFeaturePermissionScheme;

public interface RoleFeaturePermissionRepository {
	
	
	public List<RoleFeaturePermissionScheme> findAll();

	public List<RoleFeaturePermissionScheme> findByRole(Role role);

}
