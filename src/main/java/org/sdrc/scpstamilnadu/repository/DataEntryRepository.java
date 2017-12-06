package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.Area;
import org.sdrc.scpstamilnadu.domain.Data;
import org.sdrc.scpstamilnadu.domain.Indicator;
import org.sdrc.scpstamilnadu.domain.IndicatorClassification;
import org.sdrc.scpstamilnadu.domain.IndicatorUnitSubgroup;
import org.sdrc.scpstamilnadu.domain.Subgroup;
import org.sdrc.scpstamilnadu.domain.Timeperiod;
import org.sdrc.scpstamilnadu.domain.Unit;

public interface DataEntryRepository {

	public Data save(Data data);

	public <S extends Data> List<S> save(Iterable<S> data);

	public Data findByIndicatorUnitSubgroupAndSource(IndicatorUnitSubgroup ius, IndicatorClassification source);

	public Data findByIndicatorUnitSubgroupAndSourceAndTimePeriod(IndicatorUnitSubgroup ius, IndicatorClassification source, Timeperiod timePerioid);

	public Data findByIndicatorUnitSubgroupAndSourceAndTimePeriodAndArea(IndicatorUnitSubgroup ius, IndicatorClassification source, Timeperiod timePerioid, Area area);

	public List<Data> findByIndicatorAndUnitAndSubgroupAndSourceAndTimePeriodOrderByPercentageAsc(Indicator in, Unit unit, Subgroup subgoup, IndicatorClassification source, Timeperiod timePerioid);

	//dashboard
	
	Area[] getAreaNid(String areaCode, Integer childLevel);

	public List<Object[]> findDataByTimePeriod(Integer parseInt, Integer parseInt2,	Integer parseInt3, Integer[] areaNid);

	public List<Object[]> findData(Integer iusNid, Integer areaNid);


	public List<Object[]> findByArea(List<Integer> areaIdList,
			Integer timePeriod, int indexId);

	public List<Object[]> findInByArea(List<Integer> areaIdList,
			Integer timePeriod, Integer parentSectorId);

	public List<Data> findByTimePeriodAndAreaIn(Timeperiod timePeriod, List<Area> areas);

}
