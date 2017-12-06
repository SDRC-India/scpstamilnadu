package org.sdrc.scpstamilnadu.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sdrc.scpstamilnadu.domain.Agency;
import org.sdrc.scpstamilnadu.domain.Area;
import org.sdrc.scpstamilnadu.domain.IndicatorClassification;
import org.sdrc.scpstamilnadu.domain.Timeperiod;
import org.sdrc.scpstamilnadu.model.AreaDetailsModel;
import org.sdrc.scpstamilnadu.model.IndicatorClassificationModel;
import org.sdrc.scpstamilnadu.model.TimePeriodModel;
import org.sdrc.scpstamilnadu.model.UserModel;
import org.sdrc.scpstamilnadu.model.ValueObject;
import org.sdrc.scpstamilnadu.repository.AgencyRepository;
import org.sdrc.scpstamilnadu.repository.AreaRepository;
import org.sdrc.scpstamilnadu.repository.DataEntryRepository;
import org.sdrc.scpstamilnadu.repository.IndicatorClassificationRepository;
import org.sdrc.scpstamilnadu.repository.TimePeriodRepository;
import org.sdrc.scpstamilnadu.util.Constants;
import org.sdrc.scpstamilnadu.util.FactSheetDataModel;
import org.sdrc.scpstamilnadu.util.FactsheetObject;
import org.sdrc.scpstamilnadu.util.IndicatorObject;
import org.sdrc.scpstamilnadu.util.StateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

@Service
public class FactsheetServiceImpl implements FactsheetService {

	@Autowired
	private AreaRepository utAreaRepository;

	@Autowired
	private TimePeriodRepository timePeriodRepository;

	@Autowired
	private DataEntryRepository utDataRepository;

	@Autowired
	private AgencyRepository agencyRepository;

	@Autowired
	private IndicatorClassificationRepository indicatorClassificationRepository;

	@Autowired
	private ResourceBundleMessageSource applicationMessageSource;

	@Autowired
	StateManager stateManager;

	DecimalFormat df = new DecimalFormat("#.0");

	private static DecimalFormat df2 = new DecimalFormat("#.00");

	@Override
	public Object getPrefetchData(int agencyId) {
		Agency agency = agencyRepository.findByAgencyId(agencyId);

		int stateId = agency.getState().getAreaId();

		Map<String, Object> dataMap = new HashMap<String, Object>();

		List<Area> utAreaEnList = new ArrayList<Area>();
		List<AreaDetailsModel> areaList = new ArrayList<AreaDetailsModel>();

		List<Timeperiod> utTpList = new ArrayList<Timeperiod>();
		List<TimePeriodModel> timePeriodModelList = new ArrayList<TimePeriodModel>();

		List<IndicatorClassification> indicatorClassificationList = new ArrayList<IndicatorClassification>();
		List<IndicatorClassificationModel> sourceList = new ArrayList<IndicatorClassificationModel>();
		List<IndicatorClassificationModel> sectorList = new ArrayList<IndicatorClassificationModel>();
		List<IndicatorClassificationModel> indexList = new ArrayList<IndicatorClassificationModel>();
		List<Object[]> indicatorsList = new ArrayList<Object[]>();

		// return only area that belongs to a state

		Area state = agency.getState();

		utAreaEnList = utAreaRepository.findCountryAndStateByStateId(stateId);

		// Note Since State is hardcoded as index 0 position in UI ,we wil
		// always have to send AT 0 index.
		utAreaEnList.add(0, state);

		UserModel userModel = (UserModel) stateManager.getValue(Constants.Web.USER_PRINCIPAL);
		if (userModel == null || userModel.getRoleId() != 8) {
			// public view of time period
			utTpList = timePeriodRepository.findTimePeriodsPresentForDataOfMyAgencyOnlyPublished(agency.getAgencyId());
		} else if (userModel.getRoleId() == 8) {
			// fetch all time period for commissioner
			utTpList = timePeriodRepository.findTimePeriodsPresentForDataOfMyAgencyAllPublishedAndUnPublished(agency.getAgencyId());
		}
		// return only time periods that have data.//since our time period table is
		// common we can have time periods of multiple agencies

		indicatorClassificationList = indicatorClassificationRepository.findAll();

		// find all indicators of an agency

		indicatorsList = indicatorClassificationRepository.findAllIndicatorsByAgency(agency.getAgencyId());

		for (Area utAreaEn : utAreaEnList) {

			AreaDetailsModel areaDetailsModel = new AreaDetailsModel();

			areaDetailsModel.setArea_NId(utAreaEn.getAreaId());
			areaDetailsModel.setArea_Parent_NId(utAreaEn.getParentAreaId());
			areaDetailsModel.setArea_ID(utAreaEn.getAreaCode());
			areaDetailsModel.setArea_Name(utAreaEn.getAreaName());
			areaDetailsModel.setArea_Level(utAreaEn.getLevel());
			areaDetailsModel.setSelected(false);

			areaList.add(areaDetailsModel);

		}

		for (Timeperiod utTimeperiod : utTpList) {
			TimePeriodModel timePeriodModel = new TimePeriodModel();

			timePeriodModel.setEndDate(utTimeperiod.getEndDate());
			timePeriodModel.setPeriodicity(utTimeperiod.getPeriodicity());
			timePeriodModel.setStartDate(utTimeperiod.getStartDate());
			timePeriodModel.setTimePeriod(utTimeperiod.getTimePeriod());
			timePeriodModel.setTimePeriod_NId(utTimeperiod.getTimeperiodId());
			timePeriodModelList.add(timePeriodModel);
		}
		Collections.reverse(timePeriodModelList);

		int indexId = Integer.parseInt(applicationMessageSource.getMessage("indexId", null, null));

		System.out.println("Index Id from properties file ::" + indexId);

		List<IndicatorObject> indicatorObjects = new ArrayList<IndicatorObject>();
		Map<Integer, List<IndicatorObject>> indicatorMaps = new HashMap<Integer, List<IndicatorObject>>();

		for (Object[] objects : indicatorsList) {
			IndicatorObject indicatorObject = new IndicatorObject();

			indicatorObject.setIc_Name(objects[2].toString());
			indicatorObject.setIc_NId(Integer.parseInt(objects[0].toString()));
			indicatorObject.setIc_parent_NId(Integer.parseInt(objects[1].toString()));
			indicatorObject.setIndicator_Name(objects[4].toString());
			indicatorObject.setIndicator_NId(Integer.parseInt(objects[3].toString()));
			if (indicatorMaps.containsKey(indicatorObject.getIc_parent_NId())) {
				List<IndicatorObject> indicator = indicatorMaps.get(indicatorObject.getIc_parent_NId());
				indicator.add(indicatorObject);
				indicatorMaps.put(indicatorObject.getIc_parent_NId(), indicator);
			} else {
				List<IndicatorObject> indicator = new ArrayList<>();
				indicator.add(indicatorObject);
				indicatorMaps.put(indicatorObject.getIc_parent_NId(), indicator);
			}

			indicatorObjects.add(indicatorObject);
		}

		for (IndicatorClassification classificationsEn : indicatorClassificationList) {
			IndicatorClassificationModel classificationModel = new IndicatorClassificationModel();

			classificationModel.setIc_NId(classificationsEn.getIndicatorClassificationId());
			classificationModel.setIc_Parent_NId(classificationsEn.getParent() == null ? -1 : classificationsEn.getParent().getIndicatorClassificationId());
			classificationModel.setIc_Name(classificationsEn.getName());
			classificationModel.setIc_Type(classificationsEn.getIndicatorClassificationType().toString());

			if (indicatorMaps.containsKey(classificationModel.getIc_NId())) {
				classificationModel.setIndicatorObject(indicatorMaps.get(classificationModel.getIc_NId()));
			}

			if (classificationModel.getIc_Type().equals("SC")) {
				if (classificationModel.getIc_NId() == indexId || classificationModel.getIc_Parent_NId() == indexId) {
					if (classificationModel.getIc_Parent_NId() != -1)
						indexList.add(classificationModel);
				} else
					sectorList.add(classificationModel);
			} else if (classificationModel.getIc_Type().equals("SR") && classificationModel.getIc_Parent_NId() != -1) {
				sourceList.add(classificationModel);
			}
		}

		dataMap.put("AreaList", areaList);
		dataMap.put("TimePeriod", timePeriodModelList);
		dataMap.put("sectorList", sectorList);
		dataMap.put("sourceList", sourceList);
		dataMap.put("indiactorList", indicatorObjects);
		dataMap.put("indexList", indexList);

		return dataMap;
	}

	@Override
	public Object getFactSheetData(FactsheetObject factsheetObject, Agency agency) {

		int agencyId = agency.getAgencyId();

		List<Integer> areaIdList = new ArrayList<Integer>();
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<Object[]> inDataList = new ArrayList<Object[]>();

		List<AreaDetailsModel> districtList = factsheetObject.getDistrictList();
		Integer timePeriod = 0;
		if (factsheetObject.getTimePeriod() != null) {
			timePeriod = factsheetObject.getTimePeriod().getTimePeriod_NId();
		}

		Integer parentSectorId = factsheetObject.getSector().getIc_NId();

		for (AreaDetailsModel areaDetailsModel : districtList) {
			areaIdList.add(areaDetailsModel.getArea_NId());
		}

		List<Object[]> indicatorsList = indicatorClassificationRepository.findAllIndicatorsOfaSector(parentSectorId, agencyId);

		Map<String, String> indicatorMap = new HashMap<String, String>();

		for (Object[] objects : indicatorsList) {

			indicatorMap.put(objects[4].toString(), (objects[2].toString()));
		}

		int indexId = Integer.parseInt(applicationMessageSource.getMessage("indexId", null, null));
		dataList = utDataRepository.findByArea(areaIdList, timePeriod, indexId);
		inDataList = utDataRepository.findInByArea(areaIdList, timePeriod, parentSectorId);

		FactSheetDataModel factSheetDataModel = new FactSheetDataModel();
		List<FactSheetDataModel> sheetDataModels = new ArrayList<FactSheetDataModel>();
		Map<String, String> sectorData = new HashMap<String, String>();
		int areaId = 0;
		int count = 0;
		double sectorAvg = 0;
		for (Object[] object : dataList) {
			if (areaId != Integer.parseInt(object[1].toString())) {
				if (factSheetDataModel.getDistrictInfo() != null) {
					factSheetDataModel.setSectorData(sectorData);
					sheetDataModels.add(factSheetDataModel);
					count = 0;
					sectorAvg = 0;
				}
				sectorData = new HashMap<String, String>();
				factSheetDataModel = new FactSheetDataModel();
				areaId = Integer.parseInt(object[1].toString());
				factSheetDataModel.setDistrictInfo(new ValueObject("district", object[2].toString()));
			}
			count++;
			sectorAvg += Double.parseDouble(object[0].toString());
			sectorData.put(object[4].toString().replaceAll(" ", "").replaceAll(",", ""), Double.parseDouble(object[0].toString()) > 0 ? df2.format(Double.parseDouble(object[0].toString())) : "0.00");
		}
		factSheetDataModel.setSectorData(sectorData);
		sheetDataModels.add(factSheetDataModel);

		FactSheetDataModel factSheetDataModel1 = new FactSheetDataModel();
		List<FactSheetDataModel> sheetDataModels1 = new ArrayList<FactSheetDataModel>();
		Map<String, String> sectorData1 = new HashMap<String, String>();
		int areaId1 = 0;
		for (Object[] object : inDataList) {
			if (areaId1 != Integer.parseInt(object[5].toString())) {
				if (factSheetDataModel1.getDistrictInfo() != null) {
					for (String key : indicatorMap.keySet()) {
						if (!sectorData1.containsKey(key)) {
							sectorData1.put(key + "_" + indicatorMap.get(key), "-");
						} else {
							String value = sectorData1.get(key);
							sectorData1.put(key + "_" + indicatorMap.get(key), value);
							sectorData1.remove(key);
						}

					}

					factSheetDataModel1.setSectorData(sectorData1);
					sheetDataModels1.add(factSheetDataModel1);
				}
				sectorData1 = new HashMap<String, String>();
				factSheetDataModel1 = new FactSheetDataModel();
				areaId1 = Integer.parseInt(object[5].toString());
				factSheetDataModel1.setDistrictInfo(new ValueObject("district", object[6].toString()));
			}
			sectorData1.put(object[4].toString(), Double.parseDouble(object[0].toString()) > 0 ? (Double.parseDouble(object[0].toString()) > 1 ? df.format(Double.parseDouble(object[0].toString())) : "0" + df.format(Double.parseDouble(object[0].toString()))) : "0.0");

		}
		for (String key : indicatorMap.keySet()) {
			if (!sectorData1.containsKey(key)) {
				sectorData1.put(key + "_" + indicatorMap.get(key), "-");
			} else {
				String value = sectorData1.get(key);
				sectorData1.put(key + "_" + indicatorMap.get(key), value);
				sectorData1.remove(key);
			}

		}
		factSheetDataModel1.setSectorData(sectorData1);
		sheetDataModels1.add(factSheetDataModel1);

		Map<String, List<FactSheetDataModel>> finalModelList = new HashMap<String, List<FactSheetDataModel>>();
		finalModelList.put("sectorData", sheetDataModels);
		finalModelList.put("indicatorData", sheetDataModels1);

		return finalModelList;

	}

}
