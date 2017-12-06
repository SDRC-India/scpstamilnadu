package org.sdrc.scpstamilnadu.util;

import java.util.List;

import org.sdrc.scpstamilnadu.model.AreaDetailsModel;
import org.sdrc.scpstamilnadu.model.IndicatorClassificationModel;
import org.sdrc.scpstamilnadu.model.TimePeriodModel;

public class FactsheetObject {

	private AreaDetailsModel state;
	private List<AreaDetailsModel> districtList;
	private TimePeriodModel timePeriod;
	private IndicatorClassificationModel source;
	private IndicatorClassificationModel sector;
//	private Integer agencyId;
	
	public AreaDetailsModel getState() {
		return state;
	}
	public void setState(AreaDetailsModel state) {
		this.state = state;
	}
	public List<AreaDetailsModel> getDistrictList() {
		return districtList;
	}
	public void setDistrictList(List<AreaDetailsModel> districtList) {
		this.districtList = districtList;
	}
	public TimePeriodModel getTimePeriod() {
		return timePeriod;
	}
	public void setTimePeriod(TimePeriodModel timePeriod) {
		this.timePeriod = timePeriod;
	}
	public IndicatorClassificationModel getSource() {
		return source;
	}
	public void setSource(IndicatorClassificationModel source) {
		this.source = source;
	}
	public IndicatorClassificationModel getSector() {
		return sector;
	}
	public void setSector(IndicatorClassificationModel sector) {
		this.sector = sector;
	}
	
}
