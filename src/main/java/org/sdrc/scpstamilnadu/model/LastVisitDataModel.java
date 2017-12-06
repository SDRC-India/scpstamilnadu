package org.sdrc.scpstamilnadu.model;

public class LastVisitDataModel {
	private Integer lastVisitDataId;
	
	private String submissionFileURL;
	
	private String submissionFileName;
	
	private AreaModel areaModel;

	public Integer getLastVisitDataId() {
		return lastVisitDataId;
	}

	public void setLastVisitDataId(Integer lastVisitDataId) {
		this.lastVisitDataId = lastVisitDataId;
	}

	public String getSubmissionFileURL() {
		return submissionFileURL;
	}

	public void setSubmissionFileURL(String submissionFileURL) {
		this.submissionFileURL = submissionFileURL;
	}

	public String getSubmissionFileName() {
		return submissionFileName;
	}

	public void setSubmissionFileName(String submissionFileName) {
		this.submissionFileName = submissionFileName;
	}

	public AreaModel getAreaModel() {
		return areaModel;
	}

	public void setAreaModel(AreaModel areaModel) {
		this.areaModel = areaModel;
	}
}
