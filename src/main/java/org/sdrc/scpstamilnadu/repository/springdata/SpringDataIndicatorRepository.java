package org.sdrc.scpstamilnadu.repository.springdata;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.Agency;
import org.sdrc.scpstamilnadu.domain.Indicator;
import org.sdrc.scpstamilnadu.domain.IndicatorClassification;
import org.sdrc.scpstamilnadu.repository.IndicatorRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

@RepositoryDefinition(domainClass = Indicator.class, idClass = Integer.class)
public interface SpringDataIndicatorRepository extends IndicatorRepository {

	@Override
//	@Query(value = "select distinct(i.indicator_name) as disInName,i from indicator i where i.indicator_name in (select e.indicator_name from indicator e where "
//			+ "e.indicator_type='ACTUAL_INDICATOR' OR e.indicator_type='INDEX_INDICATOR' OR e.indicator_type='OVERALL' and e.indicator_classification_id = :subsector_id_fk) and i.indicator_type='ACTUAL_INDICATOR' OR i.indicator_type='INDEX_INDICATOR' OR i.indicator_type='OVERALL' "
//			+ "and i.indicator_classification_id = :subsector_id_fk ", nativeQuery = true)
	@Query(value = "select i from Indicator i where "
			+ " (i.indicatorType='ACTUAL_INDICATOR' OR i.indicatorType='INDEX_INDICATOR' OR i.indicatorType='OVERALL') "
			+ "and i.indicatorClassification.indicatorClassificationId = :subsector_id_fk ")
	public List<Indicator> findIndicatorByICforAllAgency(@Param(value = "subsector_id_fk") int subsector);

	@Override
	@Query(value = "select * from indicator i where (i.indicator_type='ACTUAL_INDICATOR' OR i.indicator_type='INDEX_INDICATOR' OR i.indicator_type='OVERALL') and " + "i.indicator_classification_id = :subsector_id_fk and i.agency_id_fk = :agency", nativeQuery = true)
	public List<Indicator> findAllIndicatorByICforAgency(@Param(value = "subsector_id_fk") int subsector, @Param(value = "agency") int agency);

	@Query(value = "select * from indicator i where i.indicator_type='ACTUAL_INDICATOR' and i.agency_id_fk = :agency and i.indicator_classification_id IN (:subsector_id_fk)", nativeQuery = true)
	public List<Indicator> findIndicatorsOfAgencyBySector(@Param(value = "agency") Agency agency, @Param(value = "subsector_id_fk") List<Integer> subsector);

//	@Override
//	@Query("SELECT distinct uticius,(SELECT utUnit FROM Unit utUnit where utUnit.unitId = utius.unit), " + " (SELECT utIn from Indicator utIn WHERE utIn.indicatorId = utius.indicator), "
//			+ " (SELECT subEn from Subgroup subEn WHERE subEn.subgroupValueId = utius.subgroup) "
//			+ " FROM IndicatorClassificationIndicatorUnitSubgroupMapping uticius,IndicatorUnitSubgroup utius,Indicator utIn "
//			+ " WHERE utius.indicatorUnitSubgroupId = uticius.indicatorUnitSubgroup AND uticius.indicatorClassification = :sectorNid  Order by uticius.indicatorUnitSubgroup ")
//	public List<Object[]> findByIC_Type(@Param("sectorNid") IndicatorClassification sectorNid) throws DataAccessException;

	@Query("SELECT uticius, utUnit, " + "  utIn, " + "  subEn   "
			+ " FROM IndicatorClassificationIndicatorUnitSubgroupMapping uticius JOIN uticius.indicatorUnitSubgroup utius "
			+ " JOIN utius.indicator utIn JOIN  utius.unit utUnit JOIN utius.subgroup subEn "
			+ " WHERE uticius.indicatorClassification = :sectorNid and utIn.agency = :agency Order by uticius.indicatorUnitSubgroup ")
	public List<Object[]> findByIC_Type_And_Agency(@Param("sectorNid") IndicatorClassification sectorNid,@Param("agency") Agency agency) throws DataAccessException;

	@Override
	@Query("SELECT  utIndicatorEn FROM Data utData, Indicator utIndicatorEn"
			+ " WHERE utData.indicator.indicatorId = utIndicatorEn.indicatorId AND "
			+ " utData.indicatorUnitSubgroup.indicatorUnitSubgroupId = :iusNID " )
	public Indicator findByIndicator_NId(@Param("iusNID")int indicatorId);
	
	
	@Override
	@Query("SELECT  utIndicatorEn FROM  Indicator utIndicatorEn join utIndicatorEn.indicatorRoleMapping irm  "
			+ " WHERE utIndicatorEn.indicatorType='ACTUAL_INDICATOR' AND  utIndicatorEn.indicatorXpath IN (:indicatorXpathNames) AND irm.role.roleId = :roleId" )
	public List<Indicator> findAllByIndicatorNameIn(@Param("indicatorXpathNames")List<String> commaSeparatedIndicatorNames,@Param("roleId")Integer roleId);
	
}
