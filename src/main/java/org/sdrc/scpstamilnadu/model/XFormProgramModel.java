/**
 * This Model class will help us to get & set  the data from Program & xForm table.
 * @auther Debiprasad Parida (debiprasad@gmail.com)
 * @since version 1.0.0.0
 *
 */
package org.sdrc.scpstamilnadu.model;

public class XFormProgramModel {
	
	private String xFormId;
	private String serverURL;
	private String programName;
	private String userId;
	private String password;
	private int id;
	private int programId;
	private String xformTitle;
	private String areaXPath;
	private String secondaryAreaXPath;
	private String dateOfVisitXPath;
	private String locationXPath;
	private String ccEmailIds;
	private boolean sendRawData;
	private Integer areaLevelId;
	private String areaLevelName;
	
	
	public String getxFormId() {
		return xFormId;
	}
	public void setxFormId(String xFormId) {
		this.xFormId = xFormId;
	}
	public String getServerURL() {
		return serverURL;
	}
	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}
	
	public String getProgramName() {
		
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProgramId() {
		return programId;
	}
	public void setProgramId(int programId) {
		this.programId = programId;
	}
	public String getXformTitle() {
		return xformTitle;
	}
	public void setXformTitle(String xformTitle) {
		this.xformTitle = xformTitle;
	}
	public String getAreaXPath() {
		return areaXPath;
	}
	public void setAreaXPath(String areaXPath) {
		this.areaXPath = areaXPath;
	}
	public String getSecondaryAreaXPath() {
		return secondaryAreaXPath;
	}
	public void setSecondaryAreaXPath(String secondaryAreaXPath) {
		this.secondaryAreaXPath = secondaryAreaXPath;
	}
	public String getDateOfVisitXPath() {
		return dateOfVisitXPath;
	}
	public void setDateOfVisitXPath(String dateOfVisitXPath) {
		this.dateOfVisitXPath = dateOfVisitXPath;
	}
	public String getLocationXPath() {
		return locationXPath;
	}
	public void setLocationXPath(String locationXPath) {
		this.locationXPath = locationXPath;
	}
	public String getCcEmailIds() {
		return ccEmailIds;
	}
	public void setCcEmailIds(String ccEmailIds) {
		this.ccEmailIds = ccEmailIds;
	}
	public boolean isSendRawData() {
		return sendRawData;
	}
	public void setSendRawData(boolean sendRawData) {
		this.sendRawData = sendRawData;
	}
	public Integer getAreaLevelId() {
		return areaLevelId;
	}
	public void setAreaLevelId(Integer areaLevelId) {
		this.areaLevelId = areaLevelId;
	}
	public String getAreaLevelName() {
		return areaLevelName;
	}
	public void setAreaLevelName(String areaLevelName) {
		this.areaLevelName = areaLevelName;
	}

}
