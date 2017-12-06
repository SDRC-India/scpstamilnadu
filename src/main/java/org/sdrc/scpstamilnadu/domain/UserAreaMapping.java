package org.sdrc.scpstamilnadu.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Azaruddin (azaruddin@sdrc.co.in)
 *
 */

@Entity
@Table(name="user_area_mapping")
public class UserAreaMapping implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_area_mapping_id")
	private Integer userAreaMappingId;
	
	@Column(name="created_date", nullable = false)
	private Timestamp createdDate;
	
	@Column(name="is_active")
	private Boolean isActive;
	
//	********  bi-directional many-to-one association to Area *******
	
	@ManyToOne
	@JoinColumn(name="area_id_fk")
	private Area area;
	
//	********  bi-directional many-to-one association to User *******
	
	@ManyToOne
	@JoinColumn(name="user_fk")
	private User user;

//	********bi-directional one-to-many association to UserRoleFeaturePermissionMapping *******
	
	@OneToMany(mappedBy="userAreaMapping",fetch=FetchType.LAZY)
	private List<UserRoleFeaturePermissionMapping> userRoleFeaturePermissionMappings;

	public Integer getUserAreaMappingId() {
		return userAreaMappingId;
	}

	public void setUserAreaMappingId(Integer userAreaMappingId) {
		this.userAreaMappingId = userAreaMappingId;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<UserRoleFeaturePermissionMapping> getUserRoleFeaturePermissionMappings() {
		return userRoleFeaturePermissionMappings;
	}

	public void setUserRoleFeaturePermissionMappings(List<UserRoleFeaturePermissionMapping> userRoleFeaturePermissionMappings) {
		this.userRoleFeaturePermissionMappings = userRoleFeaturePermissionMappings;
	}

	//GETTER SETTER **********************
	
	
	
	
	
}
