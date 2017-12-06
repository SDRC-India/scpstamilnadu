package org.sdrc.scpstamilnadu.service;

import org.sdrc.scpstamilnadu.domain.Agency;


public interface AggregationService {

	public boolean startAggregation();

	public boolean createIndex(Agency agency, int year, int month);

	public boolean aggregateDataByAgency(Agency agency, int year, int month);

	public boolean startAggregationFromMonthYear(int year, int month);

	public boolean startPublishingForCurrentMonth();
}
