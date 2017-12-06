package org.sdrc.scpstamilnadu.controller;

import java.util.Map;

import org.json.simple.JSONArray;
import org.sdrc.core.Authorize;
import org.sdrc.scpstamilnadu.model.NewIndicatorModel;
import org.sdrc.scpstamilnadu.model.ValueObject;
import org.sdrc.scpstamilnadu.service.IndicatorManagementService;
import org.sdrc.scpstamilnadu.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Azaruddin (azaruddin@sdrc.co.in)
 *
 */
@Controller
public class IndicatorManagementController {

	@Autowired
	private IndicatorManagementService indicatorManagementService;

	@Autowired
	private ResourceBundleMessageSource errorMessageSource;

	@Autowired
	private ResourceBundleMessageSource messages;
	
	private Logger _log = LoggerFactory.getLogger(IndicatorManagementController.class);

	@Authorize(feature = "indicator_mgmt", permission = "edit")
	@RequestMapping(value = "/initIndicatorManagementView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, JSONArray> getInitalJson() {
		return indicatorManagementService.initializeJson();
	}

	@Authorize(feature = "indicator_mgmt", permission = "edit")
	@RequestMapping(value = "/addNewIndicator", method = RequestMethod.POST)
	@ResponseBody
	public ValueObject submitNewIndicator(@RequestBody NewIndicatorModel newIndicatorModel) {
		ValueObject object = new ValueObject();
		try {

			boolean isIndicatorSaved = indicatorManagementService.saveNewIndicator(newIndicatorModel);
			if (isIndicatorSaved) {
				object.setKey("200");
				object.setValue(messages.getMessage(Constants.Web.INDICATOR_SAVED_SUCCESSFULLY, null, null));
				return object;
			} else {
				object.setKey("400");
				object.setValue(errorMessageSource.getMessage(Constants.Web.INTERNAL_ERROR, null, null));
				return object;
			}
		} catch (Exception ex) {
			_log.error("Error occured while adding new Indicator : {}",ex);
			object.setKey("400");
			object.setValue(errorMessageSource.getMessage(Constants.Web.INTERNAL_ERROR, null, null));
			return object;
		}

	}

	@Authorize(feature = "indicator_mgmt", permission = "edit")
	@RequestMapping("/indicatorManagement")
	public String getDataEntryPage() {
		return "indicatorManagement";
	}

}
