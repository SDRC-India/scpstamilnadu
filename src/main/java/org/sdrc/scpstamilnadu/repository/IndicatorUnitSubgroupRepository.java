package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.Indicator;
import org.sdrc.scpstamilnadu.domain.IndicatorUnitSubgroup;
import org.sdrc.scpstamilnadu.domain.Subgroup;
import org.sdrc.scpstamilnadu.domain.Unit;

public interface IndicatorUnitSubgroupRepository {

	public IndicatorUnitSubgroup save(IndicatorUnitSubgroup ius);

	public IndicatorUnitSubgroup findByIndicatorUnitSubgroupId(int id);
	
	public IndicatorUnitSubgroup findByIndicatorAndUnitAndSubgroup(Indicator indicator,Unit unit,Subgroup subgroup);


	public List<IndicatorUnitSubgroup> findAll();
	
	public List<IndicatorUnitSubgroup> findAllByOrderByIndicatorUnitSubgroupIdAsc();
	
	
	
	

}
