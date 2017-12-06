package org.sdrc.scpstamilnadu.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.sdrc.scpstamilnadu.domain.User;
import org.sdrc.scpstamilnadu.model.CollectUserModel;
import org.sdrc.scpstamilnadu.model.DataEntryModel;

public interface DataEntryService {

	public JSONObject getIndicatorsJsonForDataEntry();
	
	
	public boolean saveIndicatorsJsonFromDataEntry(List<DataEntryModel> jsonRecieved);
	

	
	
	public JSONObject returnSubmissionManagementDetails();

	
	public boolean checkIfDataEntryDoneForMonth();


	public JSONObject getPreviewData(Integer facilityId, Integer month, Integer year);


	public boolean saveIndicatorsJsonFromDataEntryFromMobile(List<DataEntryModel> dataEntryModels, User user);
}
