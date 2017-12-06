package org.sdrc.scpstamilnadu.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sdrc.scpstamilnadu.service.AggregationService;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class DailyAggregationForRawDataJob extends QuartzJobBean {

	private AggregationService aggregationService;

	public AggregationService getAggregationService() {
		return aggregationService;
	}

	public void setAggregationService(AggregationService aggregationService) {
		this.aggregationService = aggregationService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("Starting aggregation");
		aggregationService.startAggregation();
		aggregationService.startPublishingForCurrentMonth();

	}

}
