package org.sdrc.scpstamilnadu.service;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public interface ConfigurationService {

	public boolean configureIUSTable();

	public boolean genPassword();

	public boolean generateUserRoleFeaturePermissionMapping();

	public boolean generateFacilityUserMapping();

	public boolean generateUserRoleMapping();

	public boolean generateFacilitiesFromUserNos();

	public boolean generateIcIusMapping();

	public boolean generatePasswordForCommissioner();

	public boolean generateIndicatorRoleMapping();

	boolean generateEncyptedAgencyId();

	public boolean readAndInsertDataFromExcelFileForAgency() throws InvalidFormatException, IOException;

	public boolean createFacilitiesFromExcel() throws InvalidFormatException, IOException;

	boolean createCommisioner();

	public boolean genPasswordForCommisioner();
	
	public boolean createOtherUser();

	boolean createUserProgramXformMapping();

	boolean updateFaciltynames();
}
