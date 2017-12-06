package org.sdrc.scpstamilnadu.model;


public class UserRoleSchemeModel {

	private Integer userRoleSchemeId;
	private Integer roleSchemeId;
	private String userName;
	private Integer userId;
	private String roleName;
	private Integer roleId;
	private String emailId;
	private String contactNum;
	private boolean isAttached;
	private String areaName;
	private String areaCode;
	private String schemeName;
	private Integer[] userRoleSchemeIds;
	
	public Integer getUserRoleSchemeId() {
		return userRoleSchemeId;
	}
	public Integer getRoleSchemeId() {
		return roleSchemeId;
	}
	public void setUserRoleSchemeId(Integer userRoleSchemeId) {
		this.userRoleSchemeId = userRoleSchemeId;
	}
	public void setRoleSchemeId(Integer roleSchemeId) {
		this.roleSchemeId = roleSchemeId;
	}
	public String getUserName() {
		return userName;
	}
	public Integer getUserId() {
		return userId;
	}
	public String getRoleName() {
		return roleName;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public String getEmailId() {
		return emailId;
	}
	public String getContactNum() {
		return contactNum;
	}
	public boolean isAttached() {
		return isAttached;
	}
	public String getAreaName() {
		return areaName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}
	public void setAttached(boolean isAttached) {
		this.isAttached = isAttached;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public Integer[] getUserRoleSchemeIds() {
		return userRoleSchemeIds;
	}
	public void setUserRoleSchemeIds(Integer[] userRoleSchemeIds) {
		this.userRoleSchemeIds = userRoleSchemeIds;
	}
	
}
