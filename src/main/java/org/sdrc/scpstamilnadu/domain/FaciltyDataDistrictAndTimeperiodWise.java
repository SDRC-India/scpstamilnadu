package org.sdrc.scpstamilnadu.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;

import org.sdrc.core.ActionDetailType;
/**
 * 
 * @author Azaruddin (azaruddin@sdrc.co.in)
 *
 */
@Entity
@Table(name = "facilty_data_district_and_timeperiod_wise")
public class FaciltyDataDistrictAndTimeperiodWise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "facility_district_wise_data_id")
	private Integer faciltyDataWithTimePeriodMappingId;

	@ManyToOne
	@JoinColumn(name = "indicator_id_fk")
	private Indicator indicator;

	@ManyToOne
	@JoinColumn(name = "unit_id_fk")
	private Unit unit;

	@ManyToOne
	@JoinColumn(name = "subgroup_id_fk")
	private Subgroup subgroup;

	@ManyToOne
	@JoinColumn(name = "facility_id_fk")
	private Facility facility;

	@Column
	private int numerator;

	@Column
	private int denominator;

	@Column(columnDefinition="numeric(5,1)")
	private double data;

	@Column
	@Max(value = 12)
	private int month;

	@Column(length = 4)
	private int year;

	@Column(name = "last_modified")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;
	
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "action_detail_type")
	@Enumerated(EnumType.STRING)
	private ActionDetailType actionDetailType;

	@OneToMany(mappedBy = "faciltyDataDistrictAndTimeperiodWise", cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	private List<DataEntryActionHistory> dataEntryHistorys;

	public Integer getFaciltyDataWithTimePeriodMappingId() {
		return faciltyDataWithTimePeriodMappingId;
	}

	public void setFaciltyDataWithTimePeriodMappingId(Integer faciltyDataWithTimePeriodMappingId) {
		this.faciltyDataWithTimePeriodMappingId = faciltyDataWithTimePeriodMappingId;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public int getNumerator() {
		return numerator;
	}

	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}

	public int getDenominator() {
		return denominator;
	}

	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}


	

	public double getData() {
		return data;
	}

	public void setData(double data) {
		this.data = data;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Subgroup getSubgroup() {
		return subgroup;
	}

	public void setSubgroup(Subgroup subgroup) {
		this.subgroup = subgroup;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public List<DataEntryActionHistory> getDataEntryHistorys() {
		return dataEntryHistorys;
	}

	public void setDataEntryHistorys(List<DataEntryActionHistory> dataEntryHistorys) {
		this.dataEntryHistorys = dataEntryHistorys;
	}

	public ActionDetailType getActionDetailType() {
		return actionDetailType;
	}

	public void setActionDetailType(ActionDetailType actionDetailType) {
		this.actionDetailType = actionDetailType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	

}
