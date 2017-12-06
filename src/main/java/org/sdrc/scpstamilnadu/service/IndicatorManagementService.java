package org.sdrc.scpstamilnadu.service;

import java.util.Map;

import org.json.simple.JSONArray;
import org.sdrc.scpstamilnadu.model.NewIndicatorModel;

public interface IndicatorManagementService {
	
	
	public Map<String, JSONArray> initializeJson();
	
	
	public boolean saveNewIndicator(NewIndicatorModel newIndicatorModel);

}
