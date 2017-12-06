package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import org.sdrc.core.IndicatorClassificationType;
import org.sdrc.core.Type;
import org.sdrc.scpstamilnadu.domain.IndicatorClassification;
import org.sdrc.scpstamilnadu.domain.Timeperiod;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

public interface IndicatorClassificationRepository {

	public IndicatorClassification save(IndicatorClassification ic);

	public IndicatorClassification findByIndicatorClassificationId(int icId);

	public IndicatorClassification findByIndexIsTrue();

	public List<IndicatorClassification> findByIndicatorClassificationTypeAndParentIsNull(IndicatorClassificationType indicatorClassificationType);

	public List<IndicatorClassification> findByIndicatorClassificationTypeAndParentIsNullAndIndexIsFalse(IndicatorClassificationType indicatorClassificationType);

	public List<IndicatorClassification> findByIndicatorClassificationTypeAndParentIsNotNull(IndicatorClassificationType indicatorClassificationType);

	public List<IndicatorClassification> findByParent(IndicatorClassification indicatorClassificationOrSectorSubSectorSource);

	public List<IndicatorClassification> findAll();
	
	public List<IndicatorClassification> findByIndicatorClassificationType(IndicatorClassificationType indicatorClassificationType);
	
	public IndicatorClassification findByType(Type indicatorClassificationType);
	
	public IndicatorClassification findBySectorIds(String stringIds);

	public List<IndicatorClassification> findByIUS_Nid(Integer iusNid,Integer agencyId) throws DataAccessException;
	
	public List<Object[]> findAllIndicators();
	
	List<Object[]> findAllIndicatorsByAgency(int agencyId);

//	public List<Object[]> findAllIndicatorsOfaSector(Integer parentSectorId);
	
	public List<Object[]> findAllIndicatorsOfaSector(Integer parentSectorId,int agencyId);

	public IndicatorClassification findByNameAndParentIsNotNull(String stringCellValue);

	public IndicatorClassification findByNameAndParentIsNull(String stringCellValue);

	public IndicatorClassification findByNameAndParent(String stringCellValue, IndicatorClassification icSector);


}
