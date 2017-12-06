package org.sdrc.scpstamilnadu.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * 
 * @author Azaruddin (azaruddin@sdrc.co.in)
 *
 */

@Entity
@Table(name="facility_user_mapping")
public class FacilityUserMapping {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer facilityUserMappingId;
	
	
	@ManyToOne
	@JoinColumn(name="facility_id_fk")
	private Facility facility;
	
	
	@ManyToOne
	@JoinColumn(name="user_id_fk")
	private User user;


	public Integer getFacilityUserMappingId() {
		return facilityUserMappingId;
	}


	public void setFacilityUserMappingId(Integer facilityUserMappingId) {
		this.facilityUserMappingId = facilityUserMappingId;
	}


	public Facility getFacility() {
		return facility;
	}


	public void setFacility(Facility facility) {
		this.facility = facility;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	

}
