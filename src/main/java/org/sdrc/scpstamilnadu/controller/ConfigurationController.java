package org.sdrc.scpstamilnadu.controller;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.sdrc.scpstamilnadu.service.AggregationService;
import org.sdrc.scpstamilnadu.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Azaruddin (azaruddin@sdrc.co.in)
 *
 */
@Controller
public class ConfigurationController {

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private AggregationService aggregationService;

	/*
	 * @RequestMapping(value = "/ius")
	 * 
	 * @ResponseBody public boolean generateIUSTable() { return configurationService.configureIUSTable(); }
	 * 
	 * @RequestMapping("/generateIcIusMapping")
	 * 
	 * @ResponseBody public boolean generateIcIusMapping(){ return configurationService.generateIcIusMapping(); }
	 * 
	 * 
	 * @RequestMapping("/generatePassword")
	 * 
	 * @ResponseBody public boolean genPassword(){ return configurationService.genPassword(); }
	 * 
	 * 
	 * 
	 * @RequestMapping("/generatePasswordForCommissioner")
	 * 
	 * @ResponseBody public boolean generatePasswordForCommissioner(){ return
	 * configurationService.generatePasswordForCommissioner(); }
	 * 
	 * 
	 * @RequestMapping("/generateUserRolePermissionMapping")
	 * 
	 * @ResponseBody public boolean generateUserRoleFeaturePermissionMapping(){ return
	 * configurationService.generateUserRoleFeaturePermissionMapping(); }
	 * 
	 * 
	 * @RequestMapping("/generateFacilityUserMapping")
	 * 
	 * @ResponseBody public boolean generateFacilityUserMapping(){ return
	 * configurationService.generateFacilityUserMapping(); }
	 * 
	 * 
	 * @RequestMapping("/generateUserRoleMapping")
	 * 
	 * @ResponseBody public boolean generateUserRoleMapping(){ return configurationService.generateUserRoleMapping(); }
	 * 
	 * 
	 * @RequestMapping("/generateFacilitiesFromUserNos")
	 * 
	 * @ResponseBody public boolean generateFacilitiesFromUserNos(){ return
	 * configurationService.generateFacilitiesFromUserNos(); }
	 * 
	 * 
	 * 
	 * @RequestMapping("/generateIndicatorRoleMapping")
	 * 
	 * @ResponseBody public boolean generateIndicatorRoleMapping(){ return
	 * configurationService.generateIndicatorRoleMapping(); }
	 * 
	 * @RequestMapping("/generateEncyptedAgencyId")
	 * 
	 * @ResponseBody public boolean generateEncyptedAgencyId(){ return configurationService.generateEncyptedAgencyId();
	 * }
	 * 
	 * @RequestMapping("/readAndInsertAgencyDataFromExcel")
	 * 
	 * @ResponseBody public boolean readAndInsertAgencyDataFromExcel() throws InvalidFormatException, IOException{
	 * return configurationService.readAndInsertDataFromExcelFileForAgency(); }
	 * 
	 * @RequestMapping("/createFacilitiesFromExcel")
	 * 
	 * @ResponseBody public boolean createFacilitiesFromExcel() throws InvalidFormatException, IOException{ return
	 * configurationService.createFacilitiesFromExcel(); }
	 * 
	 * 
	 * @RequestMapping("/createCommisioner")
	 * 
	 * @ResponseBody public boolean createCommisioner() throws InvalidFormatException, IOException{ return
	 * configurationService.createCommisioner(); }
	 * 
	 * @RequestMapping("/genPasswordForCommisioner")
	 * 
	 * @ResponseBody public boolean genPasswordForCommisioner() throws InvalidFormatException, IOException{ return
	 * configurationService.genPasswordForCommisioner(); }
	 * 
	 * @RequestMapping("/createOtherUser")
	 * 
	 * @ResponseBody public boolean createOtherUser() { return configurationService.createOtherUser(); }
	 * 
	 * 
	 */
	@RequestMapping("/readAndInsertAgencyDataFromExcel")
	@ResponseBody
	public boolean readAndInsertAgencyDataFromExcel() throws InvalidFormatException, IOException {
		return configurationService.readAndInsertDataFromExcelFileForAgency();
	}

	@RequestMapping("aggregateData")
	@ResponseBody
	public boolean getAgData() {
		boolean result = aggregationService.startAggregation();
		return result;
	}

//	@RequestMapping("createUserProgramXformMapping")
//	@ResponseBody
//	public boolean createUserProgramXformMapping() {
//		return configurationService.createUserProgramXformMapping();
//	}
//	
//	@RequestMapping("updateFaciltynames")
//	@ResponseBody
//	public boolean updateFaciltynames() {
//		boolean result = configurationService.updateFaciltynames();
//		return result;
//	}


}
