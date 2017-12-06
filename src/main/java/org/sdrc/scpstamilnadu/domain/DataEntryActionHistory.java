package org.sdrc.scpstamilnadu.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.sdrc.core.ActionDetailType;
/**
 * 
 * @author Azaruddin (azaruddin@sdrc.co.in)
 *
 */
@Entity
@Table(name = "data_entry_action_history")
public class DataEntryActionHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "data_entry_action_history_id")
	private int dataEntryActionHistoryId;

	@ManyToOne
	@JoinColumn(name = "facility_district_wise_data_id_fk")
	private FaciltyDataDistrictAndTimeperiodWise faciltyDataDistrictAndTimeperiodWise;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "action_detail_type")
	@Enumerated(EnumType.STRING)
	private ActionDetailType actionDetailType;

	@Column(name = "prev_numerator")
	private String prevNumerator;

	@Column(name = "prev_denominator")
	private String prevDenominator;

	@Column(name = "prev_perc")
	private String prevPercentage;

	@Column(name = "curr_numerator")
	private String currNumerator;

	@Column(name = "curr_denominator")
	private String currDenominator;

	@Column(name = "curr_percentage")
	private String currPercentage;

	public int getDataEntryActionHistoryId() {
		return dataEntryActionHistoryId;
	}

	public void setDataEntryActionHistoryId(int dataEntryActionHistoryId) {
		this.dataEntryActionHistoryId = dataEntryActionHistoryId;
	}

	public FaciltyDataDistrictAndTimeperiodWise getFaciltyDataDistrictAndTimeperiodWise() {
		return faciltyDataDistrictAndTimeperiodWise;
	}

	public void setFaciltyDataDistrictAndTimeperiodWise(FaciltyDataDistrictAndTimeperiodWise faciltyDataDistrictAndTimeperiodWise) {
		this.faciltyDataDistrictAndTimeperiodWise = faciltyDataDistrictAndTimeperiodWise;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public ActionDetailType getActionDetailType() {
		return actionDetailType;
	}

	public void setActionDetailType(ActionDetailType actionDetailType) {
		this.actionDetailType = actionDetailType;
	}

	public String getPrevNumerator() {
		return prevNumerator;
	}

	public void setPrevNumerator(String prevNumerator) {
		this.prevNumerator = prevNumerator;
	}

	public String getPrevDenominator() {
		return prevDenominator;
	}

	public void setPrevDenominator(String prevDenominator) {
		this.prevDenominator = prevDenominator;
	}

	public String getCurrNumerator() {
		return currNumerator;
	}

	public void setCurrNumerator(String currNumerator) {
		this.currNumerator = currNumerator;
	}

	public String getCurrDenominator() {
		return currDenominator;
	}

	public void setCurrDenominator(String currDenominator) {
		this.currDenominator = currDenominator;
	}

	public String getPrevPercentage() {
		return prevPercentage;
	}

	public void setPrevPercentage(String prevPercentage) {
		this.prevPercentage = prevPercentage;
	}

	public String getCurrPercentage() {
		return currPercentage;
	}

	public void setCurrPercentage(String currPercentage) {
		this.currPercentage = currPercentage;
	}

}
