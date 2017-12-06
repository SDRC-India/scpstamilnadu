package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.Agency;
import org.sdrc.scpstamilnadu.domain.Timeperiod;

public interface TimePeriodRepository {

	public Timeperiod findByTimePeriod(String timePeriod);

	public Timeperiod save(Timeperiod timeperiod);

	public List<Timeperiod> findBySource_Nid(Integer iusNid, Integer sourceNid);

	public Timeperiod findByTimeperiodId(int timePeriod);

	public List<Timeperiod> findBySource_NidForPublicView(Integer iusNid, Integer sourceNid);

	List<Timeperiod> findAll();

	public List<Timeperiod> findTimePeriodsPresentForDataOfMyAgencyAllPublishedAndUnPublished(int agencyId);
	
	public List<Timeperiod> findTimePeriodsPresentForDataOfMyAgencyOnlyPublished(int agencyId);
	

}
