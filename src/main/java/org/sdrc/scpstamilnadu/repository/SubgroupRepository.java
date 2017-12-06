package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import javax.persistence.OrderBy;
import javax.persistence.criteria.Order;

import org.sdrc.scpstamilnadu.domain.Subgroup;

public interface SubgroupRepository {

	
	public List<Subgroup> findAllByOrderBySubgroupValueIdAsc();
	public Subgroup findBySubgroupValueId(int subgroupValueId);
	public Subgroup findBySubgroupVal(String stringCellValue);
	
}
