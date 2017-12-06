package org.sdrc.scpstamilnadu.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.sdrc.core.ActionDetailType;
import org.sdrc.core.IndicatorType;
import org.sdrc.core.UnitType;
import org.sdrc.scpstamilnadu.domain.Agency;
import org.sdrc.scpstamilnadu.domain.DataEntryActionHistory;
import org.sdrc.scpstamilnadu.domain.DataSubmission;
import org.sdrc.scpstamilnadu.domain.Facility;
import org.sdrc.scpstamilnadu.domain.FacilityUserMapping;
import org.sdrc.scpstamilnadu.domain.FaciltyDataDistrictAndTimeperiodWise;
import org.sdrc.scpstamilnadu.domain.Indicator;
import org.sdrc.scpstamilnadu.domain.IndicatorRoleMapping;
import org.sdrc.scpstamilnadu.domain.Role;
import org.sdrc.scpstamilnadu.domain.User;
import org.sdrc.scpstamilnadu.exceptions.DataEntryDateExceededException;
import org.sdrc.scpstamilnadu.model.CollectUserModel;
import org.sdrc.scpstamilnadu.model.DataEntryModel;
import org.sdrc.scpstamilnadu.model.UserModel;
import org.sdrc.scpstamilnadu.repository.DataSubmissionRepository;
import org.sdrc.scpstamilnadu.repository.FacilityUserMappingRepository;
import org.sdrc.scpstamilnadu.repository.FaciltyDataDistrictAndTimeperiodWiseRepository;
import org.sdrc.scpstamilnadu.repository.IndicatorRoleMappingRepository;
import org.sdrc.scpstamilnadu.repository.SubgroupRepository;
import org.sdrc.scpstamilnadu.repository.UnitRepository;
import org.sdrc.scpstamilnadu.repository.UserRepository;
import org.sdrc.scpstamilnadu.util.Constants;
import org.sdrc.scpstamilnadu.util.StateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataEntryServiceImpl implements DataEntryService {

	@Autowired
	private StateManager stateManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UnitRepository unitRepository;

	@Autowired
	private SubgroupRepository subgroupRepository;

	@Autowired
	private DataSubmissionRepository dataSubmissionRepository;

	@Autowired
	private IndicatorRoleMappingRepository indicatorRoleMappingRepository;

	@Autowired
	private FacilityUserMappingRepository facilityUserMappingRepository;

	@Autowired
	private FaciltyDataDistrictAndTimeperiodWiseRepository faciltyDataDistrictAndTimeperiodWiseRepository;

	@Autowired
	private ResourceBundleMessageSource errorMessageSource;

	@Autowired
	private AggregationService aggregationService;

	// @SuppressWarnings("unchecked")
	// @org.springframework.transaction.annotation.Transactional
	// public boolean saveIndicatorsJsonFromDataEntry(List<DataEntryModel> jsonRecieved) {
	//
	// UserModel userModel = (UserModel) stateManager.getValue(Constants.Web.USER_PRINCIPAL);
	//
	// User user = userRepository.findByUserId(userModel.getUserId());
	//
	// Date todaysDate = new Date();
	//
	// LocalDate currentDate = new LocalDate();
	//
	// int day = currentDate.getDayOfMonth();
	// int month = currentDate.getMonthOfYear() - 1;
	// int year = currentDate.getYear();
	//
	// // checking if month is zero i.e. January, then check for previous year
	// // data for month December.
	// if (month == 0) {
	// month = 12;
	// year = year - 1;
	// }
	//
	// Role role = new Role();
	// role.setRoleId(userModel.getRoleId());
	//
	// FacilityUserMapping facilityUserMapping = facilityUserMappingRepository.findByUser(user);
	//
	// Facility facility = facilityUserMapping.getFacility();
	//
	// Agency agency = user.getAgency();
	//
	// if (day <= agency.getLastDayForDataEdit()) {
	// // only save data
	//
	// List<FaciltyDataDistrictAndTimeperiodWise> facilityDatas = new ArrayList<>();
	//
	// for (DataEntryModel dataEntryModel : jsonRecieved) {
	//
	// int numeratorValue = dataEntryModel.getNumeratorValue();
	// int denominatorValue = dataEntryModel.getDenominatorValue();
	//
	// if (numeratorValue > denominatorValue) {
	// throw new IllegalArgumentException("Numerator cannot be greater than denominator");
	// }
	// }
	//
	// for (DataEntryModel dataEntryModel : jsonRecieved) {
	//
	// int indicatorId = dataEntryModel.getIndicatorId();
	// int numeratorValue = dataEntryModel.getNumeratorValue();
	// int denominatorValue = dataEntryModel.getDenominatorValue();
	//
	// double percentage = Double.parseDouble(dataEntryModel.getPercentage().trim());
	//
	// Indicator indicator = new Indicator();
	// indicator.setIndicatorId(indicatorId);
	//
	// FaciltyDataDistrictAndTimeperiodWise faciltyDataDistrictAndTimeperiodWise =
	// faciltyDataDistrictAndTimeperiodWiseRepository.findByIndicatorAndFacilityAndMonthAndYear(indicator, facility,
	// month, year);
	//
	// if (faciltyDataDistrictAndTimeperiodWise == null) {
	//
	// faciltyDataDistrictAndTimeperiodWise = new FaciltyDataDistrictAndTimeperiodWise();
	// faciltyDataDistrictAndTimeperiodWise.setFacility(facility);
	// faciltyDataDistrictAndTimeperiodWise.setIndicator(indicator);
	// faciltyDataDistrictAndTimeperiodWise.setMonth(month);
	// faciltyDataDistrictAndTimeperiodWise.setYear(year);
	// faciltyDataDistrictAndTimeperiodWise.setData(percentage);
	// faciltyDataDistrictAndTimeperiodWise.setDenominator(denominatorValue);
	// faciltyDataDistrictAndTimeperiodWise.setNumerator(numeratorValue);
	// faciltyDataDistrictAndTimeperiodWise.setLastModifiedDate(todaysDate);
	// faciltyDataDistrictAndTimeperiodWise.setCreatedDate(todaysDate);
	//
	// // Persisting data taking that unit is Percentage and
	// // Subgroup is Total
	// faciltyDataDistrictAndTimeperiodWise.setUnit(unitRepository.findByUnitType(UnitType.PERCENTAGE));
	// faciltyDataDistrictAndTimeperiodWise.setSubgroup(subgroupRepository.findAllByOrderBySubgroupValueIdAsc().get(0));
	//
	// DataEntryActionHistory history = new DataEntryActionHistory();
	// history.setActionDetailType(ActionDetailType.DATA_SUBMITTED);
	// history.setPrevNumerator(null);
	// history.setPrevDenominator(null);
	// history.setPrevPercentage(null);
	// history.setCurrNumerator(numeratorValue + "");
	// history.setCurrDenominator(denominatorValue + "");
	// history.setCurrPercentage(percentage + "");
	// history.setCreatedDate(todaysDate);
	//
	// history.setFaciltyDataDistrictAndTimeperiodWise(faciltyDataDistrictAndTimeperiodWise);
	//
	// List<DataEntryActionHistory> historys = new ArrayList<>();
	// historys.add(history);
	//
	// faciltyDataDistrictAndTimeperiodWise.setDataEntryHistorys(historys);
	//
	// facilityDatas.add(faciltyDataDistrictAndTimeperiodWise);
	//
	// faciltyDataDistrictAndTimeperiodWise.setActionDetailType(ActionDetailType.DATA_SUBMITTED);
	// faciltyDataDistrictAndTimeperiodWise =
	// faciltyDataDistrictAndTimeperiodWiseRepository.save(faciltyDataDistrictAndTimeperiodWise);
	// } else {
	// List<DataEntryActionHistory> historys = faciltyDataDistrictAndTimeperiodWise.getDataEntryHistorys();
	//
	// if ((faciltyDataDistrictAndTimeperiodWise.getNumerator() != numeratorValue) ||
	// (faciltyDataDistrictAndTimeperiodWise.getNumerator() != denominatorValue)) {
	//
	// DataEntryActionHistory history = new DataEntryActionHistory();
	// history.setActionDetailType(ActionDetailType.DATA_EDITED);
	// history.setPrevNumerator(faciltyDataDistrictAndTimeperiodWise.getNumerator() + "");
	// history.setPrevDenominator(faciltyDataDistrictAndTimeperiodWise.getDenominator() + "");
	// history.setPrevPercentage(faciltyDataDistrictAndTimeperiodWise.getData() + "");
	// history.setCurrNumerator(numeratorValue + "");
	// history.setCurrDenominator(denominatorValue + "");
	// history.setCurrPercentage(percentage + "");
	// history.setCreatedDate(todaysDate);
	// history.setFaciltyDataDistrictAndTimeperiodWise(faciltyDataDistrictAndTimeperiodWise);
	// historys.add(history);
	// }
	// faciltyDataDistrictAndTimeperiodWise.setDataEntryHistorys(historys);
	// faciltyDataDistrictAndTimeperiodWise.setData(percentage);
	// faciltyDataDistrictAndTimeperiodWise.setDenominator(denominatorValue);
	// faciltyDataDistrictAndTimeperiodWise.setNumerator(numeratorValue);
	// faciltyDataDistrictAndTimeperiodWise.setLastModifiedDate(todaysDate);
	// faciltyDataDistrictAndTimeperiodWise.setActionDetailType(ActionDetailType.DATA_EDITED);
	// }
	// }
	//
	// DataSubmission dataSubmission =
	// dataSubmissionRepository.findByFacilityAndDataEnteredForMonthAndDataEnteredForYear(facility, month, year);
	// if (dataSubmission == null) {
	// dataSubmission = new DataSubmission();
	// dataSubmission.setDataEnteredForMonth(month);
	// dataSubmission.setDataEnteredForYear(year);
	// dataSubmission.setFacility(facility);
	// dataSubmission.setSubmissionDate(todaysDate);
	// dataSubmission.setLastEditedDate(todaysDate);
	// dataSubmission.setActionDetailType(ActionDetailType.DATA_SUBMITTED);
	// dataSubmission.setNoOfTimeDataEdited(0);
	// dataSubmission.setDataPublished(false);
	// dataSubmissionRepository.save(dataSubmission);
	//
	// } else {
	// dataSubmission.setLastEditedDate(todaysDate);
	// dataSubmission.setActionDetailType(ActionDetailType.DATA_EDITED);
	// dataSubmission.setNoOfTimeDataEdited(dataSubmission.getNoOfTimeDataEdited() + 1);
	// }
	//
	// if (day > agency.getLastDayForDataEntry() && day <= agency.getLastDayForDataEdit()) {
	// // submit data and run instant aggregation
	// // update the values of indicators
	// // calculate the index for that agency
	//
	// aggregationService.aggregateDataByAgency(agency, year, month);
	// aggregationService.createIndex(agency, year, month);
	//
	// // instant
	// }
	//
	// } else {
	// // exception check
	// throw new DataEntryDateExceededException("Data entry cannot be done anymore date of month");
	//
	// }
	//
	// return true;
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see org.sdrc.scpstamilnadu.service.DataEntryService#returnSubmissionManagementDetails() This method will
	// return
	// * last 6 months submission history.Moreover.The last submitted entry,will have option edit unless, last date of
	// * data edit has not expired or data has not been published.
	// */
	// @SuppressWarnings("unchecked")
	// @org.springframework.transaction.annotation.Transactional
	// public JSONObject returnSubmissionManagementDetails() {
	// UserModel userModel = (UserModel) stateManager.getValue(Constants.Web.USER_PRINCIPAL);
	//
	// User user = userRepository.findByUserId(userModel.getUserId());
	//
	// Agency agency = user.getAgency();
	//
	// LocalDate currentDateCheck = new LocalDate();
	// int day = currentDateCheck.getDayOfMonth();
	// int lastMonth = currentDateCheck.getMonthOfYear() - 1;
	// int lastMonthInYear = currentDateCheck.getYear();
	//
	// // checking if month is zero i.e. January, then check for previous year
	// // data for month December.
	// if (lastMonth == 0) {
	// lastMonth = 12;
	// lastMonthInYear = lastMonthInYear - 1;
	// }
	//
	// LocalDate currentDate = new LocalDate();
	// currentDate = currentDate.minusMonths(6);
	// SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	//
	// FacilityUserMapping facilityUserMapping = facilityUserMappingRepository.findByUser(user);
	// Facility facility = facilityUserMapping.getFacility();
	//
	// List<DataSubmission> datas = dataSubmissionRepository.findTop6ByFacilityOrderBySubmissionDateDesc(facility);
	// JSONArray dataArray = new JSONArray();
	//
	// for (DataSubmission dataSubmission : datas) {
	// JSONObject jsonObject = new JSONObject();
	// jsonObject.put("lastModified", formatter.format(dataSubmission.getLastEditedDate()));
	// jsonObject.put("actionTakenType", dataSubmission.getActionDetailType().toString());
	// Month monthName = Month.of(dataSubmission.getDataEnteredForMonth());
	// jsonObject.put("dataSubmittedFor", monthName + " " + dataSubmission.getDataEnteredForYear());
	//
	// jsonObject.put("facility_id", dataSubmission.getFacility().getFacilityId());
	// jsonObject.put("dataForMonth", dataSubmission.getDataEnteredForMonth());
	// jsonObject.put("dataForYear", dataSubmission.getDataEnteredForYear());
	//
	// if ((dataSubmission.getDataEnteredForMonth() == lastMonth && dataSubmission.getDataEnteredForYear() ==
	// lastMonthInYear && dataSubmission.getDataPublished() == false) && (day <= agency.getLastDayForDataEdit()))
	// jsonObject.put("availableViewType", "edit");
	// else
	// jsonObject.put("availableViewType", "preview");
	//
	// dataArray.add(jsonObject);
	// }
	//
	// Collections.reverse(dataArray);
	//
	// JSONObject jsonData = new JSONObject();
	// jsonData.put("data", dataArray);
	// return jsonData;
	// }
	//
	// public boolean checkIfDataEntryDoneForMonth() {
	// UserModel userModel = (UserModel) stateManager.getValue(Constants.Web.USER_PRINCIPAL);
	// User user = userRepository.findByUserId(userModel.getUserId());
	// LocalDate currentDate = new LocalDate();
	//
	// int month = currentDate.getMonthOfYear() - 1;
	// int year = currentDate.getYear();
	//
	// // checking if month is zero i.e. January, then check for previous year data for month December.
	// if (month == 0) {
	// month = 12;
	// year = year - 1;
	// }
	//
	// FacilityUserMapping facilityUserMapping = facilityUserMappingRepository.findByUser(user);
	//
	// Facility facility = facilityUserMapping.getFacility();
	//
	// List<FaciltyDataDistrictAndTimeperiodWise> datas =
	// faciltyDataDistrictAndTimeperiodWiseRepository.findByFacilityAndMonthAndYear(facility, month, year);
	//
	// return datas != null && !datas.isEmpty();
	//
	// }

	/**
	 * Since two users can be mapped to same facility,we get user and find its mapping facility then check against the
	 * (current month - 1) timeperiod data. Two scenarios may occur : (a) if data has been submitted by user for
	 * facility , all the indicators and its previous entered values are returned to user to edit the form if the access
	 * date is less than equals to last date for data edit (b) If data is not submitted yet. Then we check whether
	 * current date is less than equal to last data for data entry ,we return a blank indicator form,else throw an
	 * exception saying <code>DataEntryDateExceededException</code>
	 * 
	 */
	// @SuppressWarnings("unchecked")
	// @org.springframework.transaction.annotation.Transactional
	// public JSONObject getIndicatorsJsonForDataEntry() {
	//
	// UserModel userModel = (UserModel) stateManager.getValue(Constants.Web.USER_PRINCIPAL);
	// User user = userRepository.findByUserId(userModel.getUserId());
	// Agency agency = user.getAgency();
	// LocalDate currentDate = new LocalDate();
	//
	// int day = currentDate.getDayOfMonth();
	// int month = currentDate.getMonthOfYear() - 1;
	// int year = currentDate.getYear();
	//
	// // checking if month is zero i.e. January, then check for previous year data for month December.
	// if (month == 0) {
	// month = 12;
	// year = year - 1;
	// }
	//
	// Month monthName = Month.of(month);
	//
	// JSONObject jsonObject = new JSONObject();
	//
	// Map<String, List<Map<String, Object>>> dataEntryJsonMap = new LinkedHashMap<String, List<Map<String, Object>>>();
	//
	// List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	//
	// Role role = new Role();
	// role.setRoleId(userModel.getRoleId());
	//
	// FacilityUserMapping facilityUserMapping = facilityUserMappingRepository.findByUser(user);
	//
	// Facility facility = facilityUserMapping.getFacility();
	//
	// List<FaciltyDataDistrictAndTimeperiodWise> datas =
	// faciltyDataDistrictAndTimeperiodWiseRepository.findByFacilityAndMonthAndYear(facility, month, year);
	//
	// if (day <= agency.getLastDayForDataEntry()) {
	// jsonObject.put("isDataSubmissionAllowed", true);
	// } else if (day > agency.getLastDayForDataEntry() && day <= agency.getLastDayForDataEdit()) {
	//
	// if (datas == null || datas.isEmpty()) {
	// jsonObject.put("isDataSubmissionAllowed", false);
	// jsonObject.put("message", errorMessageSource.getMessage(Constants.Web.DATA_ENTRY_DATE_EXCEEDED, null, null));
	// } else {
	// jsonObject.put("isDataSubmissionAllowed", true);
	// }
	// } else {
	// jsonObject.put("isDataSubmissionAllowed", false);
	// }
	//
	// // no data entry has been done.note during submission we have to check if data is submitted in before 15.
	//
	// if (datas == null || datas.isEmpty()) {
	//
	// LocalDate currentDateToday = new LocalDate();
	// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	//
	// Date dt = null;
	// try {
	// dt = df.parse(currentDateToday.getYear() + "-" + currentDateToday.getMonthOfYear() + "-01");
	// } catch (ParseException e1) {
	// throw new IllegalArgumentException(e1);
	// }
	//
	// List<IndicatorRoleMapping> inroleMapping = indicatorRoleMappingRepository.findByRoleAndAgency(role.getRoleId(),
	// agency.getAgencyId());
	//
	// for (IndicatorRoleMapping indicatorRoleMapping : inroleMapping) {
	//
	//
	// Indicator indicator = indicatorRoleMapping.getIndicator();
	//
	// Date dtIndicator;
	// try {
	// dtIndicator = df.parse(indicator.getDisplayToDeoFromYear() + "-" + indicator.getDisplayToDeoFromMonth() + "-01");
	// } catch (ParseException e) {
	// throw new IllegalArgumentException(e);
	// }
	//
	// // check if current month and year is greater than equals to display month and year
	// if (dt.equals(dtIndicator) || dt.after(dtIndicator)) {
	//
	// List<Indicator> num_den = indicator.getNumeratorDenominator();
	// Indicator numerator = null, denominator = null;
	// for (Indicator indicator2 : num_den) {
	// if (indicator2.getIndicatorType().equals(IndicatorType.NUMERATOR)) {
	// numerator = indicator2;
	// } else if (indicator2.getIndicatorType().equals(IndicatorType.DENOMINATOR)) {
	// denominator = indicator2;
	// }
	// }
	//
	// Map<String, Object> mapData = new LinkedHashMap<String, Object>();
	// mapData.put("coreAreaId", null);
	// mapData.put("coreAreaName", null);
	// mapData.put("indicatorId", indicator.getIndicatorId());
	// mapData.put("indicatorName", indicator.getIndicatorName());
	// mapData.put("numeratorName", numerator.getIndicatorName());
	// mapData.put("numeratorValue", null);
	// mapData.put("denominatorName", denominator.getIndicatorName());
	// mapData.put("denominatorValue", null);
	// mapData.put("isRequired", true);
	// mapData.put("percentage", null);
	// mapData.put("isEnable", true);
	// mapData.put("createdDate", null);
	// mapData.put("timePeriod", null);
	// mapData.put("submissionId", null);
	//
	// mapData.put("isProfile", null);
	// mapData.put("isLr", null);
	// mapData.put("indicatorOrder", null);
	//
	// mapData.put("cssClass", null);
	// mapData.put("live", null);
	// mapData.put("indicatorFacilityTimeperiodId", null);
	//
	// dataList.add(mapData);
	//
	// }
	//
	// }
	// jsonObject.put("submit", "true");
	// jsonObject.put("edit", "false");
	// dataEntryJsonMap.put("dataEntry", dataList);
	// } else {
	//
	// // there is data that has been entered.
	// for (FaciltyDataDistrictAndTimeperiodWise faciltyDataDistrictAndTimeperiodWise : datas) {
	//
	// Indicator indicator = faciltyDataDistrictAndTimeperiodWise.getIndicator();
	//
	// List<Indicator> num_den = indicator.getNumeratorDenominator();
	//
	// Indicator numerator = null, denominator = null;
	//
	// for (Indicator indicator2 : num_den) {
	// if (indicator2.getIndicatorType().equals(IndicatorType.NUMERATOR)) {
	// numerator = indicator2;
	// } else if (indicator2.getIndicatorType().equals(IndicatorType.DENOMINATOR)) {
	// denominator = indicator2;
	// }
	// }
	// Map<String, Object> data = new LinkedHashMap<String, Object>();
	//
	// data.put("coreAreaId", null);
	// data.put("coreAreaName", null);
	// data.put("indicatorId", indicator.getIndicatorId());
	// data.put("indicatorName", indicator.getIndicatorName());
	// data.put("numeratorName", numerator.getIndicatorName());
	// data.put("numeratorValue", faciltyDataDistrictAndTimeperiodWise.getNumerator());
	// data.put("denominatorName", denominator.getIndicatorName());
	// data.put("denominatorValue", faciltyDataDistrictAndTimeperiodWise.getDenominator());
	// data.put("isRequired", true);
	//
	// data.put("percentage", String.valueOf(faciltyDataDistrictAndTimeperiodWise.getData()));
	// data.put("isEnable", true);
	// data.put("createdDate", null);
	// data.put("timePeriod", null);
	// data.put("submissionId", null);
	//
	// data.put("isProfile", null);
	// data.put("isLr", null);
	// data.put("indicatorOrder", null);
	//
	// data.put("cssClass", null);
	// data.put("live", null);
	// data.put("indicatorFacilityTimeperiodId", null);
	//
	// dataList.add(data);
	//
	// }
	//
	// dataEntryJsonMap.put("dataEntry", dataList);
	//
	// jsonObject.put("submit", "false");
	// jsonObject.put("edit", "true");
	// }
	//
	// jsonObject.put("indTypeIndicatorModelMap", dataEntryJsonMap);
	// jsonObject.put("timeperiodMonth", monthName.toString());
	// jsonObject.put("timeperiodYear", year);
	//
	// JSONObject userDetails = new JSONObject();
	// userDetails.put("username", user.getUserName());
	// userDetails.put("userType", user.getRole().getRoleName());
	// userDetails.put("location", user.getUserAreaMappings().get(0).getArea().getAreaName().concat(" -
	// ").concat(user.getUserAreaMappings().get(0).getArea().getAreaCode()));
	//
	// jsonObject.put("userDetails", userDetails);
	// return jsonObject;
	// }

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public JSONObject getPreviewData(Integer facilityId, Integer month, Integer year) {
		// there is data that has been entered.
		UserModel userModel = (UserModel) stateManager.getValue(Constants.Web.USER_PRINCIPAL);
		User user = userRepository.findByUserId(userModel.getUserId());

		Month monthName = Month.of(month);

		JSONObject jsonObject = new JSONObject();

		Map<String, List<Map<String, Object>>> dataEntryJsonMap = new LinkedHashMap<String, List<Map<String, Object>>>();

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		Role role = new Role();
		role.setRoleId(userModel.getRoleId());

		FacilityUserMapping facilityUserMapping = facilityUserMappingRepository.findByUser(user);

		Facility facility = facilityUserMapping.getFacility();

		List<FaciltyDataDistrictAndTimeperiodWise> datas = faciltyDataDistrictAndTimeperiodWiseRepository.findByFacilityAndMonthAndYear(facility, month, year);

		for (FaciltyDataDistrictAndTimeperiodWise faciltyDataDistrictAndTimeperiodWise : datas) {

			Indicator indicator = faciltyDataDistrictAndTimeperiodWise.getIndicator();

			List<Indicator> num_den = indicator.getNumeratorDenominator();

			Indicator numerator = null, denominator = null;

			for (Indicator indicator2 : num_den) {
				if (indicator2.getIndicatorType().equals(IndicatorType.NUMERATOR)) {
					numerator = indicator2;
				} else if (indicator2.getIndicatorType().equals(IndicatorType.DENOMINATOR)) {
					denominator = indicator2;
				}
			}
			Map<String, Object> data = new LinkedHashMap<String, Object>();

			data.put("coreAreaId", null);
			data.put("coreAreaName", null);
			data.put("indicatorId", indicator.getIndicatorId());
			data.put("indicatorName", indicator.getIndicatorName());
			data.put("numeratorName", numerator.getIndicatorName());
			data.put("numeratorValue", faciltyDataDistrictAndTimeperiodWise.getNumerator());
			data.put("denominatorName", denominator.getIndicatorName());
			data.put("denominatorValue", faciltyDataDistrictAndTimeperiodWise.getDenominator());
			data.put("isRequired", true);
			if (faciltyDataDistrictAndTimeperiodWise.getDenominator() == 0)
				data.put("percentage", "-");
			else
				data.put("percentage", String.valueOf(faciltyDataDistrictAndTimeperiodWise.getData()));
			data.put("isEnable", true);
			data.put("createdDate", null);
			data.put("timePeriod", null);
			data.put("submissionId", null);

			data.put("isProfile", null);
			data.put("isLr", null);
			data.put("indicatorOrder", null);

			data.put("cssClass", null);
			data.put("live", null);
			data.put("indicatorFacilityTimeperiodId", null);

			dataList.add(data);

		}
		dataEntryJsonMap.put("dataEntry", dataList);

		jsonObject.put("indTypeIndicatorModelMap", dataEntryJsonMap);
		jsonObject.put("timeperiodMonth", monthName.toString());
		jsonObject.put("timeperiodYear", year);

		return jsonObject;

	}

	@SuppressWarnings("unchecked")
	@org.springframework.transaction.annotation.Transactional
	public JSONObject getIndicatorsJsonForDataEntry() {

		UserModel userModel = (UserModel) stateManager.getValue(Constants.Web.USER_PRINCIPAL);
		User user = userRepository.findByUserId(userModel.getUserId());
		Agency agency = user.getAgency();
		LocalDate currentDate = new LocalDate();

		int day = currentDate.getDayOfMonth();
		int month = currentDate.getMonthOfYear() - 1;
		int year = currentDate.getYear();

		// checking if month is zero i.e. January, then check for previous year data for month December.
		if (month == 0) {
			month = 12;
			year = year - 1;
		}

		Month monthName = Month.of(month);

		JSONObject jsonObject = new JSONObject();

		Map<String, List<Map<String, Object>>> dataEntryJsonMap = new LinkedHashMap<String, List<Map<String, Object>>>();

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		Role role = new Role();
		role.setRoleId(userModel.getRoleId());

		FacilityUserMapping facilityUserMapping = facilityUserMappingRepository.findByUser(user);

		Facility facility = facilityUserMapping.getFacility();

		List<FaciltyDataDistrictAndTimeperiodWise> datas = faciltyDataDistrictAndTimeperiodWiseRepository.findByFacilityAndMonthAndYear(facility, month, year);

		jsonObject.put("isDataSubmissionAllowed", true);

		// no data entry has been done.note during submission we have to check if data is submitted in before 15.

		if (datas == null || datas.isEmpty()) {

			LocalDate currentDateToday = new LocalDate();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			Date dt = null;
			try {
				dt = df.parse(currentDateToday.getYear() + "-" + currentDateToday.getMonthOfYear() + "-01");
			} catch (ParseException e1) {
				throw new IllegalArgumentException(e1);
			}

			List<IndicatorRoleMapping> inroleMapping = indicatorRoleMappingRepository.findByRoleAndAgency(role.getRoleId(), agency.getAgencyId());

			for (IndicatorRoleMapping indicatorRoleMapping : inroleMapping) {

				Indicator indicator = indicatorRoleMapping.getIndicator();

				Date dtIndicator;
				try {
					dtIndicator = df.parse(indicator.getDisplayToDeoFromYear() + "-" + indicator.getDisplayToDeoFromMonth() + "-01");
				} catch (ParseException e) {
					throw new IllegalArgumentException(e);
				}

				// check if current month and year is greater than equals to display month and year
				if (dt.equals(dtIndicator) || dt.after(dtIndicator)) {

					List<Indicator> num_den = indicator.getNumeratorDenominator();
					Indicator numerator = null, denominator = null;
					for (Indicator indicator2 : num_den) {
						if (indicator2.getIndicatorType().equals(IndicatorType.NUMERATOR)) {
							numerator = indicator2;
						} else if (indicator2.getIndicatorType().equals(IndicatorType.DENOMINATOR)) {
							denominator = indicator2;
						}
					}

					Map<String, Object> mapData = new LinkedHashMap<String, Object>();
					mapData.put("coreAreaId", null);
					mapData.put("coreAreaName", null);
					mapData.put("indicatorId", indicator.getIndicatorId());
					mapData.put("indicatorName", indicator.getIndicatorName());
					mapData.put("numeratorName", numerator.getIndicatorName());
					mapData.put("numeratorValue", null);
					mapData.put("denominatorName", denominator.getIndicatorName());
					mapData.put("denominatorValue", null);
					mapData.put("isRequired", true);
					mapData.put("percentage", null);
					mapData.put("isEnable", true);
					mapData.put("createdDate", null);
					mapData.put("timePeriod", null);
					mapData.put("submissionId", null);

					mapData.put("isProfile", null);
					mapData.put("isLr", null);
					mapData.put("indicatorOrder", null);

					mapData.put("cssClass", null);
					mapData.put("live", null);
					mapData.put("indicatorFacilityTimeperiodId", null);

					dataList.add(mapData);

				}

			}
			jsonObject.put("submit", "true");
			jsonObject.put("edit", "false");
			dataEntryJsonMap.put("dataEntry", dataList);
		} else {

			// there is data that has been entered.
			for (FaciltyDataDistrictAndTimeperiodWise faciltyDataDistrictAndTimeperiodWise : datas) {

				Indicator indicator = faciltyDataDistrictAndTimeperiodWise.getIndicator();

				List<Indicator> num_den = indicator.getNumeratorDenominator();

				Indicator numerator = null, denominator = null;

				for (Indicator indicator2 : num_den) {
					if (indicator2.getIndicatorType().equals(IndicatorType.NUMERATOR)) {
						numerator = indicator2;
					} else if (indicator2.getIndicatorType().equals(IndicatorType.DENOMINATOR)) {
						denominator = indicator2;
					}
				}
				Map<String, Object> data = new LinkedHashMap<String, Object>();

				data.put("coreAreaId", null);
				data.put("coreAreaName", null);
				data.put("indicatorId", indicator.getIndicatorId());
				data.put("indicatorName", indicator.getIndicatorName());
				data.put("numeratorName", numerator.getIndicatorName());
				data.put("numeratorValue", faciltyDataDistrictAndTimeperiodWise.getNumerator());
				data.put("denominatorName", denominator.getIndicatorName());
				data.put("denominatorValue", faciltyDataDistrictAndTimeperiodWise.getDenominator());
				data.put("isRequired", true);

				if (faciltyDataDistrictAndTimeperiodWise.getDenominator() == 0)
					data.put("percentage", "-");
				else
					data.put("percentage", String.valueOf(faciltyDataDistrictAndTimeperiodWise.getData()));
				data.put("isEnable", true);
				data.put("createdDate", null);
				data.put("timePeriod", null);
				data.put("submissionId", null);

				data.put("isProfile", null);
				data.put("isLr", null);
				data.put("indicatorOrder", null);

				data.put("cssClass", null);
				data.put("live", null);
				data.put("indicatorFacilityTimeperiodId", null);

				dataList.add(data);

			}

			dataEntryJsonMap.put("dataEntry", dataList);

			jsonObject.put("submit", "false");
			jsonObject.put("edit", "true");
		}

		jsonObject.put("indTypeIndicatorModelMap", dataEntryJsonMap);
		jsonObject.put("timeperiodMonth", monthName.toString());
		jsonObject.put("timeperiodYear", year);

		JSONObject userDetails = new JSONObject();

		userDetails.put("username", user.getUserName());
		userDetails.put("userType", user.getRole().getRoleName());
		// userDetails.put("location", user.getUserAreaMappings().get(0).getArea().getAreaName().concat(" -
		// ").concat(user.getUserAreaMappings().get(0).getArea().getAreaCode()));

		userDetails.put("location", facility.getFaciltyName());

		jsonObject.put("userDetails", userDetails);
		return jsonObject;
	}

	@SuppressWarnings("unchecked")
	@org.springframework.transaction.annotation.Transactional
	public boolean saveIndicatorsJsonFromDataEntry(List<DataEntryModel> jsonRecieved) {

		UserModel userModel = (UserModel) stateManager.getValue(Constants.Web.USER_PRINCIPAL);

		User user = userRepository.findByUserId(userModel.getUserId());

		Date todaysDate = new Date();

		LocalDate currentDate = new LocalDate();

		int day = currentDate.getDayOfMonth();
		int month = currentDate.getMonthOfYear() - 1;
		int year = currentDate.getYear();

		// checking if month is zero i.e. January, then check for previous year
		// data for month December.
		if (month == 0) {
			month = 12;
			year = year - 1;
		}

		Role role = new Role();
		role.setRoleId(userModel.getRoleId());

		FacilityUserMapping facilityUserMapping = facilityUserMappingRepository.findByUser(user);

		Facility facility = facilityUserMapping.getFacility();

		Agency agency = user.getAgency();

//		if (day <= agency.getLastDayForDataEdit()) {
			// only save data

			List<FaciltyDataDistrictAndTimeperiodWise> facilityDatas = new ArrayList<>();

			for (DataEntryModel dataEntryModel : jsonRecieved) {

				int numeratorValue = dataEntryModel.getNumeratorValue();
				int denominatorValue = dataEntryModel.getDenominatorValue();

				if (numeratorValue > denominatorValue) {
					throw new IllegalArgumentException("Numerator cannot be greater than denominator");
				}
			}

			for (DataEntryModel dataEntryModel : jsonRecieved) {

				int indicatorId = dataEntryModel.getIndicatorId();
				int numeratorValue = dataEntryModel.getNumeratorValue();
				int denominatorValue = dataEntryModel.getDenominatorValue();

				double percentage = Double.parseDouble(dataEntryModel.getPercentage().trim());

				Indicator indicator = new Indicator();
				indicator.setIndicatorId(indicatorId);

				FaciltyDataDistrictAndTimeperiodWise faciltyDataDistrictAndTimeperiodWise = faciltyDataDistrictAndTimeperiodWiseRepository.findByIndicatorAndFacilityAndMonthAndYear(indicator, facility, month, year);

				if (faciltyDataDistrictAndTimeperiodWise == null) {

					faciltyDataDistrictAndTimeperiodWise = new FaciltyDataDistrictAndTimeperiodWise();
					faciltyDataDistrictAndTimeperiodWise.setFacility(facility);
					faciltyDataDistrictAndTimeperiodWise.setIndicator(indicator);
					faciltyDataDistrictAndTimeperiodWise.setMonth(month);
					faciltyDataDistrictAndTimeperiodWise.setYear(year);
					faciltyDataDistrictAndTimeperiodWise.setData(percentage);
					faciltyDataDistrictAndTimeperiodWise.setDenominator(denominatorValue);
					faciltyDataDistrictAndTimeperiodWise.setNumerator(numeratorValue);
					faciltyDataDistrictAndTimeperiodWise.setLastModifiedDate(todaysDate);
					faciltyDataDistrictAndTimeperiodWise.setCreatedDate(todaysDate);

					// Persisting data taking that unit is Percentage and
					// Subgroup is Total
					faciltyDataDistrictAndTimeperiodWise.setUnit(unitRepository.findByUnitType(UnitType.PERCENTAGE));
					faciltyDataDistrictAndTimeperiodWise.setSubgroup(subgroupRepository.findAllByOrderBySubgroupValueIdAsc().get(0));

					DataEntryActionHistory history = new DataEntryActionHistory();
					history.setActionDetailType(ActionDetailType.DATA_SUBMITTED);
					history.setPrevNumerator(null);
					history.setPrevDenominator(null);
					history.setPrevPercentage(null);
					history.setCurrNumerator(numeratorValue + "");
					history.setCurrDenominator(denominatorValue + "");
					history.setCurrPercentage(percentage + "");
					history.setCreatedDate(todaysDate);

					history.setFaciltyDataDistrictAndTimeperiodWise(faciltyDataDistrictAndTimeperiodWise);

					List<DataEntryActionHistory> historys = new ArrayList<>();
					historys.add(history);

					faciltyDataDistrictAndTimeperiodWise.setDataEntryHistorys(historys);

					facilityDatas.add(faciltyDataDistrictAndTimeperiodWise);

					faciltyDataDistrictAndTimeperiodWise.setActionDetailType(ActionDetailType.DATA_SUBMITTED);
					faciltyDataDistrictAndTimeperiodWise = faciltyDataDistrictAndTimeperiodWiseRepository.save(faciltyDataDistrictAndTimeperiodWise);
				} else {
					List<DataEntryActionHistory> historys = faciltyDataDistrictAndTimeperiodWise.getDataEntryHistorys();

					if ((faciltyDataDistrictAndTimeperiodWise.getNumerator() != numeratorValue) || (faciltyDataDistrictAndTimeperiodWise.getNumerator() != denominatorValue)) {

						DataEntryActionHistory history = new DataEntryActionHistory();
						history.setActionDetailType(ActionDetailType.DATA_EDITED);
						history.setPrevNumerator(faciltyDataDistrictAndTimeperiodWise.getNumerator() + "");
						history.setPrevDenominator(faciltyDataDistrictAndTimeperiodWise.getDenominator() + "");
						history.setPrevPercentage(faciltyDataDistrictAndTimeperiodWise.getData() + "");
						history.setCurrNumerator(numeratorValue + "");
						history.setCurrDenominator(denominatorValue + "");
						history.setCurrPercentage(percentage + "");
						history.setCreatedDate(todaysDate);
						history.setFaciltyDataDistrictAndTimeperiodWise(faciltyDataDistrictAndTimeperiodWise);
						historys.add(history);
					}
					faciltyDataDistrictAndTimeperiodWise.setDataEntryHistorys(historys);
					faciltyDataDistrictAndTimeperiodWise.setData(percentage);
					faciltyDataDistrictAndTimeperiodWise.setDenominator(denominatorValue);
					faciltyDataDistrictAndTimeperiodWise.setNumerator(numeratorValue);
					faciltyDataDistrictAndTimeperiodWise.setLastModifiedDate(todaysDate);
					faciltyDataDistrictAndTimeperiodWise.setActionDetailType(ActionDetailType.DATA_EDITED);
				}
			}

			DataSubmission dataSubmission = dataSubmissionRepository.findByFacilityAndDataEnteredForMonthAndDataEnteredForYear(facility, month, year);
			if (dataSubmission == null) {
				dataSubmission = new DataSubmission();
				dataSubmission.setDataEnteredForMonth(month);
				dataSubmission.setDataEnteredForYear(year);
				dataSubmission.setFacility(facility);
				dataSubmission.setSubmissionDate(todaysDate);
				dataSubmission.setLastEditedDate(todaysDate);
				dataSubmission.setActionDetailType(ActionDetailType.DATA_SUBMITTED);
				dataSubmission.setNoOfTimeDataEdited(0);
				dataSubmission.setDataPublished(false);
				dataSubmissionRepository.save(dataSubmission);

			} else {
				dataSubmission.setLastEditedDate(todaysDate);
				dataSubmission.setActionDetailType(ActionDetailType.DATA_EDITED);
				dataSubmission.setNoOfTimeDataEdited(dataSubmission.getNoOfTimeDataEdited() + 1);
			}

//			if (day > agency.getLastDayForDataEntry() && day <= agency.getLastDayForDataEdit()) {
//				// submit data and run instant aggregation
//				// update the values of indicators
//				// calculate the index for that agency
//
//				aggregationService.aggregateDataByAgency(agency, year, month);
//				aggregationService.createIndex(agency, year, month);
//
//				// instant
//			}

//		} else {
//			// exception check
//			throw new DataEntryDateExceededException("Data entry cannot be done anymore date of month");
//
//		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sdrc.scpstamilnadu.service.DataEntryService#returnSubmissionManagementDetails() This method will return
	 * last 6 months submission history.Moreover.The last submitted entry,will have option edit unless, last date of
	 * data edit has not expired or data has not been published.
	 */
	@SuppressWarnings("unchecked")
	@org.springframework.transaction.annotation.Transactional
	public JSONObject returnSubmissionManagementDetails() {
		UserModel userModel = (UserModel) stateManager.getValue(Constants.Web.USER_PRINCIPAL);

		User user = userRepository.findByUserId(userModel.getUserId());

		Agency agency = user.getAgency();

		LocalDate currentDateCheck = new LocalDate();
		int day = currentDateCheck.getDayOfMonth();
		int lastMonth = currentDateCheck.getMonthOfYear() - 1;
		int lastMonthInYear = currentDateCheck.getYear();

		// checking if month is zero i.e. January, then check for previous year
		// data for month December.
		if (lastMonth == 0) {
			lastMonth = 12;
			lastMonthInYear = lastMonthInYear - 1;
		}

		LocalDate currentDate = new LocalDate();
		currentDate = currentDate.minusMonths(6);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		FacilityUserMapping facilityUserMapping = facilityUserMappingRepository.findByUser(user);
		Facility facility = facilityUserMapping.getFacility();

		List<DataSubmission> datas = dataSubmissionRepository.findTop6ByFacilityOrderBySubmissionDateDesc(facility);
		JSONArray dataArray = new JSONArray();

		for (DataSubmission dataSubmission : datas) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("lastModified", formatter.format(dataSubmission.getLastEditedDate()));
			jsonObject.put("actionTakenType", dataSubmission.getActionDetailType().toString());
			Month monthName = Month.of(dataSubmission.getDataEnteredForMonth());
			jsonObject.put("dataSubmittedFor", monthName + " " + dataSubmission.getDataEnteredForYear());

			jsonObject.put("facility_id", dataSubmission.getFacility().getFacilityId());
			jsonObject.put("dataForMonth", dataSubmission.getDataEnteredForMonth());
			jsonObject.put("dataForYear", dataSubmission.getDataEnteredForYear());

			// if ((dataSubmission.getDataEnteredForMonth() == lastMonth && dataSubmission.getDataEnteredForYear() ==
			// lastMonthInYear && dataSubmission.getDataPublished() == false) && (day <=
			// agency.getLastDayForDataEdit()))
			// jsonObject.put("availableViewType", "edit");
			// else
			// jsonObject.put("availableViewType", "preview");

			if ((dataSubmission.getDataEnteredForMonth() == lastMonth && dataSubmission.getDataEnteredForYear() == lastMonthInYear))
				jsonObject.put("availableViewType", "edit");
			else
				jsonObject.put("availableViewType", "preview");

			dataArray.add(jsonObject);
		}

		Collections.reverse(dataArray);

		JSONObject jsonData = new JSONObject();
		jsonData.put("data", dataArray);
		return jsonData;
	}

	public boolean checkIfDataEntryDoneForMonth() {
		UserModel userModel = (UserModel) stateManager.getValue(Constants.Web.USER_PRINCIPAL);
		User user = userRepository.findByUserId(userModel.getUserId());
		LocalDate currentDate = new LocalDate();

		int month = currentDate.getMonthOfYear() - 1;
		int year = currentDate.getYear();

		// checking if month is zero i.e. January, then check for previous year data for month December.
		if (month == 0) {
			month = 12;
			year = year - 1;
		}

		FacilityUserMapping facilityUserMapping = facilityUserMappingRepository.findByUser(user);

		Facility facility = facilityUserMapping.getFacility();

		List<FaciltyDataDistrictAndTimeperiodWise> datas = faciltyDataDistrictAndTimeperiodWiseRepository.findByFacilityAndMonthAndYear(facility, month, year);

		return datas != null && !datas.isEmpty();

	}
	
	
	
	
	
	//---------------------------------------------------------FOr Mobile Device------------------------------------------------
	
	@SuppressWarnings("unchecked")
	@org.springframework.transaction.annotation.Transactional
	public boolean saveIndicatorsJsonFromDataEntryFromMobile(List<DataEntryModel> jsonRecieved,User user) {

		

		Date todaysDate = new Date();

		LocalDate currentDate = new LocalDate();

		int day = currentDate.getDayOfMonth();
		int month = currentDate.getMonthOfYear() - 1;
		int year = currentDate.getYear();

		// checking if month is zero i.e. January, then check for previous year
		// data for month December.
		if (month == 0) {
			month = 12;
			year = year - 1;
		}

		Role role = new Role();
		role.setRoleId(user.getRole().getRoleId());

		FacilityUserMapping facilityUserMapping = facilityUserMappingRepository.findByUser(user);

		Facility facility = facilityUserMapping.getFacility();

		Agency agency = user.getAgency();

//		if (day <= agency.getLastDayForDataEdit()) {
			// only save data

			List<FaciltyDataDistrictAndTimeperiodWise> facilityDatas = new ArrayList<>();

			for (DataEntryModel dataEntryModel : jsonRecieved) {

				int numeratorValue = dataEntryModel.getNumeratorValue();
				int denominatorValue = dataEntryModel.getDenominatorValue();

				if (numeratorValue > denominatorValue) {
					throw new IllegalArgumentException("Numerator cannot be greater than denominator");
				}
			}

			for (DataEntryModel dataEntryModel : jsonRecieved) {

				int indicatorId = dataEntryModel.getIndicatorId();
				int numeratorValue = dataEntryModel.getNumeratorValue();
				int denominatorValue = dataEntryModel.getDenominatorValue();

				double percentage = Double.parseDouble(dataEntryModel.getPercentage().trim());

				Indicator indicator = new Indicator();
				indicator.setIndicatorId(indicatorId);

				FaciltyDataDistrictAndTimeperiodWise faciltyDataDistrictAndTimeperiodWise = faciltyDataDistrictAndTimeperiodWiseRepository.findByIndicatorAndFacilityAndMonthAndYear(indicator, facility, month, year);

				if (faciltyDataDistrictAndTimeperiodWise == null) {

					faciltyDataDistrictAndTimeperiodWise = new FaciltyDataDistrictAndTimeperiodWise();
					faciltyDataDistrictAndTimeperiodWise.setFacility(facility);
					faciltyDataDistrictAndTimeperiodWise.setIndicator(indicator);
					faciltyDataDistrictAndTimeperiodWise.setMonth(month);
					faciltyDataDistrictAndTimeperiodWise.setYear(year);
					faciltyDataDistrictAndTimeperiodWise.setData(percentage);
					faciltyDataDistrictAndTimeperiodWise.setDenominator(denominatorValue);
					faciltyDataDistrictAndTimeperiodWise.setNumerator(numeratorValue);
					faciltyDataDistrictAndTimeperiodWise.setLastModifiedDate(todaysDate);
					faciltyDataDistrictAndTimeperiodWise.setCreatedDate(todaysDate);

					// Persisting data taking that unit is Percentage and
					// Subgroup is Total
					faciltyDataDistrictAndTimeperiodWise.setUnit(unitRepository.findByUnitType(UnitType.PERCENTAGE));
					faciltyDataDistrictAndTimeperiodWise.setSubgroup(subgroupRepository.findAllByOrderBySubgroupValueIdAsc().get(0));

					DataEntryActionHistory history = new DataEntryActionHistory();
					history.setActionDetailType(ActionDetailType.DATA_SUBMITTED);
					history.setPrevNumerator(null);
					history.setPrevDenominator(null);
					history.setPrevPercentage(null);
					history.setCurrNumerator(numeratorValue + "");
					history.setCurrDenominator(denominatorValue + "");
					history.setCurrPercentage(percentage + "");
					history.setCreatedDate(todaysDate);

					history.setFaciltyDataDistrictAndTimeperiodWise(faciltyDataDistrictAndTimeperiodWise);

					List<DataEntryActionHistory> historys = new ArrayList<>();
					historys.add(history);

					faciltyDataDistrictAndTimeperiodWise.setDataEntryHistorys(historys);

					facilityDatas.add(faciltyDataDistrictAndTimeperiodWise);

					faciltyDataDistrictAndTimeperiodWise.setActionDetailType(ActionDetailType.DATA_SUBMITTED);
					faciltyDataDistrictAndTimeperiodWise = faciltyDataDistrictAndTimeperiodWiseRepository.save(faciltyDataDistrictAndTimeperiodWise);
				} else {
					List<DataEntryActionHistory> historys = faciltyDataDistrictAndTimeperiodWise.getDataEntryHistorys();

					if ((faciltyDataDistrictAndTimeperiodWise.getNumerator() != numeratorValue) || (faciltyDataDistrictAndTimeperiodWise.getNumerator() != denominatorValue)) {

						DataEntryActionHistory history = new DataEntryActionHistory();
						history.setActionDetailType(ActionDetailType.DATA_EDITED);
						history.setPrevNumerator(faciltyDataDistrictAndTimeperiodWise.getNumerator() + "");
						history.setPrevDenominator(faciltyDataDistrictAndTimeperiodWise.getDenominator() + "");
						history.setPrevPercentage(faciltyDataDistrictAndTimeperiodWise.getData() + "");
						history.setCurrNumerator(numeratorValue + "");
						history.setCurrDenominator(denominatorValue + "");
						history.setCurrPercentage(percentage + "");
						history.setCreatedDate(todaysDate);
						history.setFaciltyDataDistrictAndTimeperiodWise(faciltyDataDistrictAndTimeperiodWise);
						historys.add(history);
					}
					faciltyDataDistrictAndTimeperiodWise.setDataEntryHistorys(historys);
					faciltyDataDistrictAndTimeperiodWise.setData(percentage);
					faciltyDataDistrictAndTimeperiodWise.setDenominator(denominatorValue);
					faciltyDataDistrictAndTimeperiodWise.setNumerator(numeratorValue);
					faciltyDataDistrictAndTimeperiodWise.setLastModifiedDate(todaysDate);
					faciltyDataDistrictAndTimeperiodWise.setActionDetailType(ActionDetailType.DATA_EDITED);
				}
			}

			DataSubmission dataSubmission = dataSubmissionRepository.findByFacilityAndDataEnteredForMonthAndDataEnteredForYear(facility, month, year);
			if (dataSubmission == null) {
				dataSubmission = new DataSubmission();
				dataSubmission.setDataEnteredForMonth(month);
				dataSubmission.setDataEnteredForYear(year);
				dataSubmission.setFacility(facility);
				dataSubmission.setSubmissionDate(todaysDate);
				dataSubmission.setLastEditedDate(todaysDate);
				dataSubmission.setActionDetailType(ActionDetailType.DATA_SUBMITTED);
				dataSubmission.setNoOfTimeDataEdited(0);
				dataSubmission.setDataPublished(false);
				dataSubmissionRepository.save(dataSubmission);

			} else {
				dataSubmission.setLastEditedDate(todaysDate);
				dataSubmission.setActionDetailType(ActionDetailType.DATA_EDITED);
				dataSubmission.setNoOfTimeDataEdited(dataSubmission.getNoOfTimeDataEdited() + 1);
			}

//			if (day > agency.getLastDayForDataEntry() && day <= agency.getLastDayForDataEdit()) {
//				// submit data and run instant aggregation
//				// update the values of indicators
//				// calculate the index for that agency
//
//				aggregationService.aggregateDataByAgency(agency, year, month);
//				aggregationService.createIndex(agency, year, month);
//
//				// instant
//			}

//		} else {
//			// exception check
//			throw new DataEntryDateExceededException("Data entry cannot be done anymore date of month");
//
//		}

		return true;
	}
	
	
}
