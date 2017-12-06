package org.sdrc.scpstamilnadu.domain;

import javax.persistence.Column;
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
@Table(name="indicator_role_mapping")
public class IndicatorRoleMapping {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="indicator_role_mapping_id")
	private Integer indicatorRoleMappingId;
	
	
	@ManyToOne
	@JoinColumn(name="indicator_id_fk")
	private Indicator indicator;
	
	@ManyToOne
	@JoinColumn(name="role_id_fk")
	private Role role;
	
	
	public Integer getIndicatorRoleMappingId() {
		return indicatorRoleMappingId;
	}

	public void setIndicatorRoleMappingId(Integer indicatorRoleMappingId) {
		this.indicatorRoleMappingId = indicatorRoleMappingId;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	

	
	
	

}
