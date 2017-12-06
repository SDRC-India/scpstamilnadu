package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import org.sdrc.core.UnitType;
import org.sdrc.scpstamilnadu.domain.Unit;

public interface UnitRepository {

	public List<Unit> findAll();

	public Unit findByUnitType(UnitType unitType);
	
	

	public Unit findByUnitId(int unitId);

	public Unit findByUnitName(String stringCellValue);

}
