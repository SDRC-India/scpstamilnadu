package org.sdrc.scpstamilnadu.model;

import java.util.List;

/**
 * 
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */
public class UserModel {

	private Integer userId;
	private String username;
	private String password;
	private Integer areaId;
	private long userLoginMetaId;
	private int roleId;
	private int agencyId;

	private List<UserAreaModel> userAreaModels;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public long getUserLoginMetaId() {
		return userLoginMetaId;
	}

	public void setUserLoginMetaId(long userLoginMetaId) {
		this.userLoginMetaId = userLoginMetaId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserAreaModel> getUserAreaModels() {
		return userAreaModels;
	}

	public void setUserAreaModels(List<UserAreaModel> userAreaModels) {
		this.userAreaModels = userAreaModels;
	}

	public int getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}
	
	

	// private int stateId;

}
