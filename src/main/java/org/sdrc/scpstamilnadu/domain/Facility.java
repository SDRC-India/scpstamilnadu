package org.sdrc.scpstamilnadu.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * 
 * @author Azaruddin (azaruddin@sdrc.co.in)
 *
 */
@Entity
public class Facility {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "facility_id")
	private Integer facilityId;

	@Column(name = "facility_name",length=65536)
	private String faciltyName;

	@ManyToOne
	@JoinColumn(name = "district_id_fk")
	private Area district;

	@Column(name = "cci_type")
	private String cciType;

	@Column(name = "serving_districts")
	private String servingDistricts;

	
	@OneToOne(mappedBy="facility")
	private FacilityUserMapping facilityUserMapping;
	
	public Facility() {
		super();
	}

	public Facility(Integer facilityId) {
		super();
		this.facilityId = facilityId;
	}

	public Integer getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Integer facilityId) {
		this.facilityId = facilityId;
	}

	public String getFaciltyName() {
		return faciltyName;
	}

	public void setFaciltyName(String faciltyName) {
		this.faciltyName = faciltyName;
	}

	public Area getDistrict() {
		return district;
	}

	public void setDistrict(Area district) {
		this.district = district;
	}

	public String getCciType() {
		return cciType;
	}

	public void setCciType(String cciType) {
		this.cciType = cciType;
	}

	public String getServingDistricts() {
		return servingDistricts;
	}

	public void setServingDistricts(String servingDistricts) {
		this.servingDistricts = servingDistricts;
	}

	public FacilityUserMapping getFacilityUserMapping() {
		return facilityUserMapping;
	}

	public void setFacilityUserMapping(FacilityUserMapping facilityUserMapping) {
		this.facilityUserMapping = facilityUserMapping;
	}

	
	
	
}
