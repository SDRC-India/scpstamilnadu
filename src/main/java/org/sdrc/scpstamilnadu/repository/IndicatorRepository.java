package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import org.sdrc.core.IndicatorType;
import org.sdrc.scpstamilnadu.domain.Agency;
import org.sdrc.scpstamilnadu.domain.Indicator;
import org.sdrc.scpstamilnadu.domain.IndicatorClassification;
import org.springframework.dao.DataAccessException;

public interface IndicatorRepository {

	public Indicator save(Indicator indicator);

	public Indicator findByIndicatorId(int indicatorId);

	public List<Indicator> findAll();

	public List<Indicator> findAllByAgencyAgencyId(int agencyId);

	public List<Indicator> findIndicatorsOfAgencyBySector(Agency agency, List<Integer> subsector);

	public List<Indicator> findAllByAgencyAndIndicatorType(Agency agency, IndicatorType indicatorType);

	public List<Indicator> findAllByAgencyAndIndicatorTypeOrderByIndicatorIdAsc(Agency agency, IndicatorType indicatorType);

	public Indicator findByAgencyAndIndicatorClassificationAndIndicatorType(Agency agency, IndicatorClassification ic, IndicatorType indicatorType);

	public List<Indicator> findIndicatorByICforAllAgency(int subsector);

	public List<Indicator> findAllIndicatorByICforAgency(int subsector, int agency);



	public Indicator findByIndicator_NId(int indicatorId);
	
	public List<Object[]> findByIC_Type_And_Agency(IndicatorClassification sectorNid, Agency agency) throws DataAccessException;

	public Indicator findByIndicatorNameAndAgencyAgencyId(String indicatorName, int agencyId);

	public List<Indicator> findAllByIndicatorNameIn(List<String> commaSeparatedIndicatorNames,Integer roleId);

}
