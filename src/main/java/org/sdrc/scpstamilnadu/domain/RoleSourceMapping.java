package org.sdrc.scpstamilnadu.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author Azaruddin (azaruddin@sdrc.co.in)
 *
 */
@Entity
@Table(name = "role_icsource_mapping")
public class RoleSourceMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer roleSourceMappingId;

	@OneToOne
	@JoinColumn(name = "role_id_fk")
	private Role role;

	@OneToOne
	@JoinColumn(name = "ic_id_fk")
	private IndicatorClassification ic;

	public Integer getRoleSourceMappingId() {
		return roleSourceMappingId;
	}

	public void setRoleSourceMappingId(Integer roleSourceMappingId) {
		this.roleSourceMappingId = roleSourceMappingId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public IndicatorClassification getIc() {
		return ic;
	}

	public void setIc(IndicatorClassification ic) {
		this.ic = ic;
	}

}
