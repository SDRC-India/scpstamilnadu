package org.sdrc.scpstamilnadu.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
/**
 * 
 * @author Azaruddin (azaruddin@sdrc.co.in)
 *
 */
@Entity
@Table(name = "time_period",indexes = { @Index(name = "i_timeperiod", columnList = "timeperiod") })


public class Timeperiod {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "timeperiod_id")
	private Integer timeperiodId;

	@Column(name = "timeperiod")
	private String timePeriod;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "periodicity")
	private String periodicity;
	

	public Integer getTimeperiodId() {
		return timeperiodId;
	}

	public void setTimeperiodId(Integer timeperiodId) {
		this.timeperiodId = timeperiodId;
	}

	public String getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(String periodicity) {
		this.periodicity = periodicity;
	}


}
