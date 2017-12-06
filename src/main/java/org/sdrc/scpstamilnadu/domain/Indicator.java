package org.sdrc.scpstamilnadu.domain;

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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.sdrc.core.IndicatorType;
/**
 * 
 * @author Azaruddin (azaruddin@sdrc.co.in)
 *
 */
@Entity
public class Indicator {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "indicator_id")
	private Integer indicatorId;

	@Column(name = "indicator_name")
	private String indicatorName;

	@Column(name = "indicator_metadata", length = 65536)
	private String indicatorMetadata;
	
	@Column(name = "indicator_xpath", length = 255)
	private String indicatorXpath;
	
	@Column(name = "numerator_xpath", length = 255)
	private String numeratorXpath;
	
	@Column(name = "denominator_xpath", length = 255)
	private String denominatorXpath;
	
	@Column(name = "percentage_xpath", length = 255)
	private String percentageXPath;

	@Column(name = "indicator_type")
	@Enumerated(EnumType.STRING)
	private IndicatorType indicatorType;

	@Column(name = "high_is_good")
	private boolean highIsGood;

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "belongs_to_indicator")
	private List<Indicator> numeratorDenominator;

	@ManyToOne
	@JoinColumn(name = "agency_id_fk")
	private Agency agency;

	@ManyToOne
	@JoinColumn(name = "indicator_classification_id")
	private IndicatorClassification indicatorClassification;
	
	
	@OneToOne(mappedBy="indicator")
	private IndicatorRoleMapping indicatorRoleMapping;
	
	
//	@OneToMany(mappedBy="indicator")
//	private List<IndicatorUnitSubgroup>indicatorUnitSubgroup;
	
	
	@Column(name="publish_month")
	private int publishMonth;
	
	@Column(name="publish_year")
	private int publishYear;
	
	
	@Column(name="display_to_deo_from_month")
	private int displayToDeoFromMonth;
	
	@Column(name="display_to_deo_from_year")
	private int displayToDeoFromYear;
	
	
	
	@Transient
	private double normalalizedValue;
	
	public Integer getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(Integer indicatorId) {
		this.indicatorId = indicatorId;
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public String getIndicatorMetadata() {
		return indicatorMetadata;
	}

	public void setIndicatorMetadata(String indicatorMetadata) {
		this.indicatorMetadata = indicatorMetadata;
	}

	public IndicatorType getIndicatorType() {
		return indicatorType;
	}

	public void setIndicatorType(IndicatorType indicatorType) {
		this.indicatorType = indicatorType;
	}

	public boolean isHighIsGood() {
		return highIsGood;
	}

	public void setHighIsGood(boolean highIsGood) {
		this.highIsGood = highIsGood;
	}

	public List<Indicator> getNumeratorDenominator() {
		return numeratorDenominator;
	}

	public void setNumeratorDenominator(List<Indicator> numeratorDenominator) {
		this.numeratorDenominator = numeratorDenominator;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public IndicatorClassification getIndicatorClassification() {
		return indicatorClassification;
	}

	public void setIndicatorClassification(IndicatorClassification indicatorClassification) {
		this.indicatorClassification = indicatorClassification;
	}

	public int getPublishMonth() {
		return publishMonth;
	}

	public void setPublishMonth(int publishMonth) {
		this.publishMonth = publishMonth;
	}

	public int getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(int publishYear) {
		this.publishYear = publishYear;
	}

	public int getDisplayToDeoFromMonth() {
		return displayToDeoFromMonth;
	}

	public void setDisplayToDeoFromMonth(int displayToDeoFromMonth) {
		this.displayToDeoFromMonth = displayToDeoFromMonth;
	}

	public int getDisplayToDeoFromYear() {
		return displayToDeoFromYear;
	}

	public void setDisplayToDeoFromYear(int displayToDeoFromYear) {
		this.displayToDeoFromYear = displayToDeoFromYear;
	}

	public IndicatorRoleMapping getIndicatorRoleMapping() {
		return indicatorRoleMapping;
	}

	public void setIndicatorRoleMapping(IndicatorRoleMapping indicatorRoleMapping) {
		this.indicatorRoleMapping = indicatorRoleMapping;
	}

	
//	public List<IndicatorUnitSubgroup> getIndicatorUnitSubgroup() {
//		return indicatorUnitSubgroup;
//	}
//
//	public void setIndicatorUnitSubgroup(List<IndicatorUnitSubgroup> indicatorUnitSubgroup) {
//		this.indicatorUnitSubgroup = indicatorUnitSubgroup;
//	}

	public double getNormalalizedValue() {
		return normalalizedValue;
	}

	public void setNormalalizedValue(double normalalizedValue) {
		this.normalalizedValue = normalalizedValue;
	}

	public String getIndicatorXpath() {
		return indicatorXpath;
	}

	public void setIndicatorXpath(String indicatorXpath) {
		this.indicatorXpath = indicatorXpath;
	}

	public String getNumeratorXpath() {
		return numeratorXpath;
	}

	public void setNumeratorXpath(String numeratorXpath) {
		this.numeratorXpath = numeratorXpath;
	}

	public String getDenominatorXpath() {
		return denominatorXpath;
	}

	public void setDenominatorXpath(String denominatorXpath) {
		this.denominatorXpath = denominatorXpath;
	}

	public String getPercentageXPath() {
		return percentageXPath;
	}

	public void setPercentageXPath(String percentageXPath) {
		this.percentageXPath = percentageXPath;
	}

	

	
	
}
