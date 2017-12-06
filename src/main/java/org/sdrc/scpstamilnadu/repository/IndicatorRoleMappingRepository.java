package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.Agency;
import org.sdrc.scpstamilnadu.domain.IndicatorRoleMapping;
import org.sdrc.scpstamilnadu.domain.Role;

public interface IndicatorRoleMappingRepository {
	
	
	public List<IndicatorRoleMapping> findByRole(Role role);
	
	
	//public List<IndicatorRoleMapping> findByRoleAndAgency(Role role,Agency agency);
	
	public List<IndicatorRoleMapping> findByRoleAndAgency(int roleId,int agencyId);
	
	public IndicatorRoleMapping save(IndicatorRoleMapping indicatorRoleMapping);

}
