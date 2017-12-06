package org.sdrc.scpstamilnadu.repository.springdata;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.IndicatorClassification;
import org.sdrc.scpstamilnadu.repository.IndicatorClassificationRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;


@RepositoryDefinition(domainClass=IndicatorClassification.class,idClass=Integer.class)
public interface SpringDataIndicatorClassificationRepository extends IndicatorClassificationRepository {
	
	@Override
	@Query("SELECT ic FROM IndicatorClassification ic WHERE ic.indicatorClassificationId " +
			" in (SELECT distinct data.source FROM Data data JOIN data.indicatorUnitSubgroup ius JOIN ius.indicator ind JOIN ind.agency ag WHERE data.indicatorUnitSubgroup.indicatorUnitSubgroupId = :iusNid and ag.agencyId = :agencyId )" )
	public List<IndicatorClassification> findByIUS_Nid(@Param("iusNid")Integer iusNid,@Param("agencyId")Integer agencyId) throws DataAccessException;
	
	@Override
	@Query(value="SELECT ic.indicator_classification_id as icNId, ic.parent_id as icParentNId, ic.classification_name as icName, "
			+ "ius.indicator_id_fk as indicatorNId, indi.indicator_name as indicatorName FROM indicator_classification as ic, "
			+ "ic_ius_mapping as icius, indicator_unit_subgroup as ius, indicator as indi WHERE ic.indicator_classification_id=icius.ic_fk and "
			+ "icius.ius_fk=ius.indicator_unit_subgroup_id and ius.indicator_id_fk=indi.indicator_id and ic.parent_id != -1 and "
			+ "ic.indicatorClassificationType='SC' ORDER BY ic.parent_id, ic.classification_name Asc,indi.indicator_name",nativeQuery=true)
	List<Object[]> findAllIndicators();
	
	
	@Override
	@Query(value="SELECT ic.indicator_classification_id as icNId, ic.parent_id as icParentNId, ic.classification_name as icName, "
			+ "ius.indicator_id_fk as indicatorNId, indi.indicator_name as indicatorName FROM indicator_classification as ic, "
			+ "ic_ius_mapping as icius, indicator_unit_subgroup as ius, indicator as indi WHERE ic.indicator_classification_id=icius.ic_fk and "
			+ "icius.ius_fk=ius.indicator_unit_subgroup_id and ius.indicator_id_fk=indi.indicator_id and ic.parent_id != -1 and "
			+ "ic.indicatorClassificationType='SC' and indi.agency_id_fk= :agencyId ORDER BY ic.parent_id, ic.classification_name Asc,indi.indicator_name",nativeQuery=true)
	List<Object[]> findAllIndicatorsByAgency(@Param("agencyId")int agencyId);
	
//	@Override
//	@Query(value="SELECT ic.indicator_classification_id as icNId, ic.parent_id as icParentNId, ic.classification_name as icName, "
//			+ "ius.indicator_id_fk as indicatorNId, indi.indicator_name as indicatorName "
//			+ "FROM indicator_classification as ic, "
//			+ "ic_ius_mapping as icius, "
//			+ "indicator_unit_subgroup as ius, "
//			+ "indicator as indi "
//			+ "WHERE ic.indicator_classification_id=icius.ic_fk and icius.ius_fk=ius.indicator_unit_subgroup_id and ius.indicator_id_fk=indi.indicator_id "
//			+ "and ic.parent_id = :parentSectorId and ic.indicatorclassificationtype='SC' ORDER BY ic.parent_id",nativeQuery=true)
//	public List<Object[]> findAllIndicatorsOfaSector(@Param("parentSectorId")Integer parentSectorId);
//	
	
	@Override
	@Query(value="SELECT ic.indicator_classification_id as icNId, ic.parent_id as icParentNId, ic.classification_name as icName, "
			+ "ius.indicator_id_fk as indicatorNId, indi.indicator_name as indicatorName "
			+ "FROM indicator_classification as ic inner join "
			+ "ic_ius_mapping as icius on ic.indicator_classification_id=icius.ic_fk INNER JOIN "
			+ "indicator_unit_subgroup as ius ON  icius.ius_fk=ius.indicator_unit_subgroup_id INNER JOIN  "
			+ "indicator as indi ON  ius.indicator_id_fk=indi.indicator_id "
			+ "WHERE   "
			+ " ic.parent_id = :parentSectorId and ic.indicatorclassificationtype='SC' and indi.agency_id_fk = :agencyId ORDER BY ic.parent_id",nativeQuery=true)
	public List<Object[]> findAllIndicatorsOfaSector(@Param("parentSectorId")Integer parentSectorId,@Param("agencyId")int agencyId);
}
