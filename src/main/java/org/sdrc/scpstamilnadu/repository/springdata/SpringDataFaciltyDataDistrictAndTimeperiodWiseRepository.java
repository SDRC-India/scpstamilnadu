package org.sdrc.scpstamilnadu.repository.springdata;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.Agency;
import org.sdrc.scpstamilnadu.domain.FaciltyDataDistrictAndTimeperiodWise;
import org.sdrc.scpstamilnadu.repository.FaciltyDataDistrictAndTimeperiodWiseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

@RepositoryDefinition(domainClass=FaciltyDataDistrictAndTimeperiodWise.class,idClass=Integer.class)
public interface SpringDataFaciltyDataDistrictAndTimeperiodWiseRepository extends FaciltyDataDistrictAndTimeperiodWiseRepository{

	//Since each is managed by a specific role or source for an agency to only a specific source we are aggregating data grouping by district,indicator,timeperiod
	//we skip all denominator values that are 0
	@Query(value="select f.district.areaId,fd.indicator.indicatorId,fd.unit.unitId,fd.subgroup.subgroupValueId, sum(fd.numerator) AS numerator, sum(fd.denominator) AS denominator from FaciltyDataDistrictAndTimeperiodWise fd"
	+ " JOIN fd.indicator as i JOIN i.indicatorRoleMapping as irm JOIN fd.facility as f WHERE fd.month = :month AND fd.year = :year AND i.agency = :agency AND fd.denominator != 0 "
	+ "GROUP BY f.district,fd.indicator,fd.unit,fd.subgroup ORDER BY f.district,fd.indicator ASC")
	public List<Object[]> aggregateRawDataByIndicatorAndAgencyAndMonthAndYearAndRole(@Param("month")int month, @Param("year")int year,@Param("agency")Agency agency);
}
