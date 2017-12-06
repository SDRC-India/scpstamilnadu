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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.sdrc.core.ActionDetailType;
/**
 * 
 * @author Azaruddin (azaruddin@sdrc.co.in)
 *
 */

@Entity
@Table(name="data_submission")
public class DataSubmission {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer dataSubmissionId;
	
	@Column(name="submission_date")
	private Date submissionDate;
	
	
	@Column(name="last_edited_date")
	private Date lastEditedDate; 
	
	@ManyToOne
	@JoinColumn(name="facility_id_fk")
	private Facility facility;
	
	@Column(name="data_entered_for_month",updatable=false)
	private int dataEnteredForMonth;
	
	
	@Column(name = "last_action")
	@Enumerated(EnumType.STRING)
	private ActionDetailType actionDetailType;
	

	@Column(name="data_entered_for_year",updatable=false)
	private int dataEnteredForYear;

	@Column(name="data_published",columnDefinition="boolean default 'false'")
	private Boolean dataPublished;
	
	@Column(name="no_of_time_data_edited",columnDefinition="int default '0'")
	private Integer noOfTimeDataEdited;
	
	

	public Integer getDataSubmissionId() {
		return dataSubmissionId;
	}



	public void setDataSubmissionId(Integer dataSubmissionId) {
		this.dataSubmissionId = dataSubmissionId;
	}



	public Date getSubmissionDate() {
		return submissionDate;
	}



	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}



	public Date getLastEditedDate() {
		return lastEditedDate;
	}



	public void setLastEditedDate(Date lastEditedDate) {
		this.lastEditedDate = lastEditedDate;
	}



	public Facility getFacility() {
		return facility;
	}



	public void setFacility(Facility facility) {
		this.facility = facility;
	}



	public int getDataEnteredForMonth() {
		return dataEnteredForMonth;
	}



	public void setDataEnteredForMonth(int dataEnteredForMonth) {
		this.dataEnteredForMonth = dataEnteredForMonth;
	}



	public int getDataEnteredForYear() {
		return dataEnteredForYear;
	}



	public void setDataEnteredForYear(int dataEnteredForYear) {
		this.dataEnteredForYear = dataEnteredForYear;
	}



	public ActionDetailType getActionDetailType() {
		return actionDetailType;
	}



	public void setActionDetailType(ActionDetailType actionDetailType) {
		this.actionDetailType = actionDetailType;
	}



	public Boolean getDataPublished() {
		return dataPublished;
	}



	public void setDataPublished(Boolean dataPublished) {
		this.dataPublished = dataPublished;
	}



	public Integer getNoOfTimeDataEdited() {
		return noOfTimeDataEdited;
	}



	public void setNoOfTimeDataEdited(Integer noOfTimeDataEdited) {
		this.noOfTimeDataEdited = noOfTimeDataEdited;
	}
	
	
	
	
}
