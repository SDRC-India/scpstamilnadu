package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.Area;

public interface AreaRepository {
	
	public List<Area> findAll();
	
	public Area findByAreaId(int areaId);
	
	List<Area> findAllByParentAreaId(Integer stateId);

	public List<Area> findCountryAndStateByStateId(int stateId);

	public Area findByAreaCode(String areaCode);

	public Area findByAreaNameAndParentAreaId(String districtName, int parentAreaId);
}
