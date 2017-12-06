package org.sdrc.scpstamilnadu.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sdrc.core.IndicatorClassificationType;
import org.sdrc.core.IndicatorType;
import org.sdrc.core.UnitType;
import org.sdrc.scpstamilnadu.domain.Agency;
import org.sdrc.scpstamilnadu.domain.Area;
import org.sdrc.scpstamilnadu.domain.Data;
import org.sdrc.scpstamilnadu.domain.Facility;
import org.sdrc.scpstamilnadu.domain.FacilityUserMapping;
import org.sdrc.scpstamilnadu.domain.Indicator;
import org.sdrc.scpstamilnadu.domain.IndicatorClassification;
import org.sdrc.scpstamilnadu.domain.IndicatorClassificationIndicatorUnitSubgroupMapping;
import org.sdrc.scpstamilnadu.domain.IndicatorUnitSubgroup;
import org.sdrc.scpstamilnadu.domain.Program_XForm_Mapping;
import org.sdrc.scpstamilnadu.domain.Role;
import org.sdrc.scpstamilnadu.domain.RoleFeaturePermissionScheme;
import org.sdrc.scpstamilnadu.domain.Subgroup;
import org.sdrc.scpstamilnadu.domain.Timeperiod;
import org.sdrc.scpstamilnadu.domain.Unit;
import org.sdrc.scpstamilnadu.domain.User;
import org.sdrc.scpstamilnadu.domain.UserAreaMapping;
import org.sdrc.scpstamilnadu.domain.UserRoleFeaturePermissionMapping;
import org.sdrc.scpstamilnadu.domain.User_Program_XForm_Mapping;
import org.sdrc.scpstamilnadu.model.ProgramWithXFormsModel;
import org.sdrc.scpstamilnadu.repository.AgencyRepository;
import org.sdrc.scpstamilnadu.repository.AreaRepository;
import org.sdrc.scpstamilnadu.repository.DataEntryRepository;
import org.sdrc.scpstamilnadu.repository.FacilityRepository;
import org.sdrc.scpstamilnadu.repository.FacilityUserMappingRepository;
import org.sdrc.scpstamilnadu.repository.IndicatorClassificationRepository;
import org.sdrc.scpstamilnadu.repository.IndicatorClassification_Ius_Mapping_Repository;
import org.sdrc.scpstamilnadu.repository.IndicatorRepository;
import org.sdrc.scpstamilnadu.repository.IndicatorUnitSubgroupRepository;
import org.sdrc.scpstamilnadu.repository.Program_XForm_MappingRepository;
import org.sdrc.scpstamilnadu.repository.RoleFeaturePermissionRepository;
import org.sdrc.scpstamilnadu.repository.RoleRepository;
import org.sdrc.scpstamilnadu.repository.SubgroupRepository;
import org.sdrc.scpstamilnadu.repository.TimePeriodRepository;
import org.sdrc.scpstamilnadu.repository.UnitRepository;
import org.sdrc.scpstamilnadu.repository.UserAreaMappingRepository;
import org.sdrc.scpstamilnadu.repository.UserRepository;
import org.sdrc.scpstamilnadu.repository.UserRoleFeaturePermissionMappingRepository;
import org.sdrc.scpstamilnadu.repository.User_Program_XForm_MappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

	@Autowired
	private IndicatorUnitSubgroupRepository indicatorUnitSubgroupRepository;

	@Autowired
	private IndicatorRepository indicatorRepository;

	@Autowired
	private IndicatorClassificationRepository indicatorClassificationRepository;

	@Autowired
	private IndicatorClassification_Ius_Mapping_Repository indicatorClassification_Ius_Mapping_Repository;

	@Autowired
	private SubgroupRepository subgroupRepository;
	@Autowired
	private UnitRepository unitRepository;

	@Autowired
	private AreaRepository areaRepository;

	@Autowired
	private AgencyRepository agencyRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FacilityRepository facilityRepository;

	@Autowired
	private TimePeriodRepository timePeriodRepository;

	@Autowired
	private DataEntryRepository dataEntryRepository;

	@Autowired
	private FacilityUserMappingRepository facilityUserMappingRepository;

	@Autowired
	private UserAreaMappingRepository userAreaMappingRepository;

	@Autowired
	private RoleFeaturePermissionRepository roleFeaturePermissionRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleFeaturePermissionMappingRepository userRoleFeaturePermissionMappingRepository;

	@Autowired
	private Program_XForm_MappingRepository program_XForm_MappingRepository;

	@Autowired
	private User_Program_XForm_MappingRepository user_Program_XForm_MappingRepository;

	@Override
	@Transactional
	public boolean configureIUSTable() {

		// Generate IUS Mapping For Percentage Indicator

		List<Agency> agencys = agencyRepository.findAll();

		for (Agency agency : agencys) {

			List<Indicator> indicators = indicatorRepository.findAllByAgencyAndIndicatorTypeOrderByIndicatorIdAsc(agency, IndicatorType.ACTUAL_INDICATOR);

			List<Subgroup> subgroups = subgroupRepository.findAllByOrderBySubgroupValueIdAsc();

			Unit unit = unitRepository.findByUnitType(UnitType.PERCENTAGE);

			for (Subgroup subgroup : subgroups) {

				for (Indicator indicator : indicators) {

					IndicatorUnitSubgroup indicatorUnitSubgroup = new IndicatorUnitSubgroup();

					indicatorUnitSubgroup.setIndicator(indicator);
					indicatorUnitSubgroup.setSubgroup(subgroup);
					indicatorUnitSubgroup.setUnit(unit);
					// indicatorUnitSubgroup.setAgency(agency);

					indicatorUnitSubgroup = indicatorUnitSubgroupRepository.save(indicatorUnitSubgroup);

				}

			}
		}

		// Generating IUS mapping for Index Indicator

		for (Agency agency : agencys) {

			List<Indicator> indicators = indicatorRepository.findAllByAgencyAndIndicatorTypeOrderByIndicatorIdAsc(agency, IndicatorType.INDEX_INDICATOR);

			List<Subgroup> subgroups = subgroupRepository.findAllByOrderBySubgroupValueIdAsc();

			Unit unit = unitRepository.findByUnitType(UnitType.INDEX);

			for (Subgroup subgroup : subgroups) {

				for (Indicator indicator : indicators) {

					IndicatorUnitSubgroup indicatorUnitSubgroup = new IndicatorUnitSubgroup();

					indicatorUnitSubgroup.setIndicator(indicator);
					indicatorUnitSubgroup.setSubgroup(subgroup);
					indicatorUnitSubgroup.setUnit(unit);
					// indicatorUnitSubgroup.setAgency(agency);

					indicatorUnitSubgroup = indicatorUnitSubgroupRepository.save(indicatorUnitSubgroup);

				}

			}
		}

		// Generating IUS mapping for Overall Indicator

		for (Agency agency : agencys) {

			List<Indicator> indicators = indicatorRepository.findAllByAgencyAndIndicatorTypeOrderByIndicatorIdAsc(agency, IndicatorType.OVERALL);

			List<Subgroup> subgroups = subgroupRepository.findAllByOrderBySubgroupValueIdAsc();

			Unit unit = unitRepository.findByUnitType(UnitType.INDEX);

			for (Subgroup subgroup : subgroups) {

				for (Indicator indicator : indicators) {

					IndicatorUnitSubgroup indicatorUnitSubgroup = new IndicatorUnitSubgroup();

					indicatorUnitSubgroup.setIndicator(indicator);
					indicatorUnitSubgroup.setSubgroup(subgroup);
					indicatorUnitSubgroup.setUnit(unit);
					// indicatorUnitSubgroup.setAgency(agency);

					indicatorUnitSubgroup = indicatorUnitSubgroupRepository.save(indicatorUnitSubgroup);

				}

			}
		}
		// Generate IC_IUS mapping for all IUS

		List<IndicatorUnitSubgroup> ius = indicatorUnitSubgroupRepository.findAllByOrderByIndicatorUnitSubgroupIdAsc();

		for (IndicatorUnitSubgroup indicatorUnitSubgroup : ius) {

			Indicator in = indicatorUnitSubgroup.getIndicator();

			IndicatorClassificationIndicatorUnitSubgroupMapping mapping = new IndicatorClassificationIndicatorUnitSubgroupMapping();

			mapping.setIndicatorClassification(in.getIndicatorClassification());
			mapping.setIndicatorUnitSubgroup(indicatorUnitSubgroup);

			indicatorClassification_Ius_Mapping_Repository.save(mapping);

		}

		return true;
	}

	@Override
	@Transactional
	public boolean genPassword() {

		List<User> users = userRepository.findAll();
		String password = null;
		MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("MD5");
		for (User user : users) {
			System.out.println("username :" + user.getUserName());
			password = encoder.encodePassword(user.getUserName(), user.getPlainPassword(user));
			System.out.println("generated password :" + password);
			user.setPassword(password);
		}

		return false;
	}

	@Override
	@Transactional
	public boolean genPasswordForCommisioner() {

		User user = userRepository.findByUserId(1393);
		String password = null;
		MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("MD5");

		System.out.println("username :" + user.getUserName());
		password = encoder.encodePassword("comm_tamilnadu", user.getUserName() + "_" + "8");
		System.out.println("generated password :" + password);
		user.setPassword(password);

		return true;
	}

	@Override
	@Transactional
	public boolean createCommisioner() {

		Area district = areaRepository.findByAreaId(2);
		Role role = roleRepository.findByRoleId(8);

		User user = new User();
		user.setAgency(new Agency(1));
		user.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
		user.setEmail(null);
		user.setInvalidAttempts((short) 0);
		user.setIsActive(true);

		user.setName(role.getRoleName().concat("_").concat(district.getAreaName().toUpperCase()));
		user.setUserName(role.getRoleName().toLowerCase().concat("_").concat(district.getAreaName().toLowerCase()));
		user.setPassword(user.generatedEncodedPassword(user.getUserName(), user.getUserName().concat("_").concat(role.getRoleId() + "")));
		user.setRole(role);
		user.setOtp(null);
		user.setOtpGeneratedDateTime(null);

		user = userRepository.save(user);
		String password = null;

		MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("MD5");
		password = encoder.encodePassword(user.getUserName(), user.getPlainPassword(user));
		user.setPassword(password);

		UserAreaMapping areaMapping = new UserAreaMapping();
		areaMapping.setArea(district);
		areaMapping.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
		areaMapping.setIsActive(true);
		areaMapping.setUser(user);

		areaMapping = userAreaMappingRepository.save(areaMapping);

		List<RoleFeaturePermissionScheme> roleFeature = roleFeaturePermissionRepository.findByRole(role);

		for (RoleFeaturePermissionScheme roleFeaturePermissionScheme : roleFeature) {

			UserRoleFeaturePermissionMapping userRoleFeaturePermissionMapping = new UserRoleFeaturePermissionMapping();
			userRoleFeaturePermissionMapping.setRoleFeaturePermissionScheme(roleFeaturePermissionScheme);
			userRoleFeaturePermissionMapping.setUserAreaMapping(areaMapping);

			userRoleFeaturePermissionMappingRepository.save(userRoleFeaturePermissionMapping);

		}

		return true;
	}

	@Override
	@Transactional
	public boolean generateEncyptedAgencyId() {

		List<Agency> agencies = agencyRepository.findAll();

		String password = null;
		MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("MD5");

		for (Agency agency : agencies) {

			agency.setEncryptedAgencyId(encoder.encodePassword(agency.getAgencyName(), agency.getAgencyName() + "_" + agency.getAgencyName()));
		}

		return false;
	}

	@Override
	@Transactional
	public boolean generateUserRoleFeaturePermissionMapping() {

		List<User> users = userRepository.findAll();

		List<RoleFeaturePermissionScheme> rolesAndFeatures = roleFeaturePermissionRepository.findAll();

		for (User user : users) {

			UserAreaMapping userAreaMapping = user.getUserAreaMappings().get(0);

			for (RoleFeaturePermissionScheme roleFeaturePermissionScheme : rolesAndFeatures) {

				if (roleFeaturePermissionScheme.getRole().getRoleId() == user.getRole().getRoleId()) {

					UserRoleFeaturePermissionMapping userRole = new UserRoleFeaturePermissionMapping();

					userRole.setRoleFeaturePermissionScheme(roleFeaturePermissionScheme);
					userRole.setUserAreaMapping(userAreaMapping);
					userRole.setUpdatedBy("Azar");
					userRole.setUpdatedDate(new java.sql.Timestamp(new Date().getTime()));

					userRoleFeaturePermissionMappingRepository.save(userRole);
				}
			}

		}
		return true;

	}

	@Override
	@Transactional
	public boolean generateFacilityUserMapping() {

		List<User> users = userRepository.findAll();

		List<Facility> facilities = facilityRepository.findAll();

		List<FacilityUserMapping> list = new ArrayList<FacilityUserMapping>();

		for (Facility facility : facilities) {

			for (User user : users) {

				if (user.getUserAreaMappings().get(0).getArea().getAreaId() == facility.getDistrict().getAreaId()) {

					FacilityUserMapping fum = new FacilityUserMapping();
					fum.setFacility(facility);
					fum.setUser(user);
					list.add(fum);

					users.remove(user);
					break;
				}

			}

		}

		if (!list.isEmpty()) {
			facilityUserMappingRepository.save(list);
		}

		return true;

	}

	public boolean generateUserRoleMapping() {

		return false;
	}

	@Override
	@Transactional
	public boolean generateFacilitiesFromUserNos() {

		List<User> users = userRepository.findAll();

		List<Facility> facilities = new ArrayList<Facility>();
		for (User user : users) {

			Area area = user.getUserAreaMappings().get(0).getArea();

			Facility facility = new Facility();
			facility.setDistrict(area);
			facility.setFaciltyName(user.getName() + "_" + area.getAreaName() + "_" + area.getLevel());

			facilities.add(facility);

			facility = facilityRepository.save(facility);

			FacilityUserMapping mapping = new FacilityUserMapping();
			mapping.setFacility(facility);
			mapping.setUser(user);

			facilityUserMappingRepository.save(mapping);

		}

		return true;
	}

	@Override
	@Transactional
	public boolean generateIcIusMapping() {

		List<IndicatorUnitSubgroup> ius = indicatorUnitSubgroupRepository.findAllByOrderByIndicatorUnitSubgroupIdAsc();

		for (IndicatorUnitSubgroup indicatorUnitSubgroup : ius) {

			Indicator in = indicatorUnitSubgroup.getIndicator();

			IndicatorClassificationIndicatorUnitSubgroupMapping mapping = new IndicatorClassificationIndicatorUnitSubgroupMapping();

			mapping.setIndicatorClassification(in.getIndicatorClassification());
			mapping.setIndicatorUnitSubgroup(indicatorUnitSubgroup);

			indicatorClassification_Ius_Mapping_Repository.save(mapping);

		}

		return true;
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public boolean generatePasswordForCommissioner() {

		User user = userRepository.findByUserId(1392);
		String password = null;
		MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("MD5");
		password = encoder.encodePassword(user.getUserName(), user.getUserName() + "_" + user.getRole().getRoleId());
		user.setPassword(password);
		return false;
	}

	@Override
	@Transactional
	public boolean generateIndicatorRoleMapping() {

		Agency agency = new Agency();
		agency.setAgencyId(1);

		List<Indicator> allIndicatorsOfAgency = indicatorRepository.findAllByAgencyAndIndicatorType(agency, IndicatorType.ACTUAL_INDICATOR);

		for (Indicator indicator : allIndicatorsOfAgency) {

		}

		return false;
	}

	@Override
	@Transactional
	public boolean readAndInsertDataFromExcelFileForAgency() throws InvalidFormatException, IOException {

		File file = new File("E:\\scps\\TN_SCPS_Excel_Template_r5.xlsx");

		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));

		XSSFSheet sheet = workbook.getSheetAt(0);
		int agencyId = 1;

		for (int row = 0; row < sheet.getLastRowNum(); row++) {
			XSSFRow xssfRow = sheet.getRow(row);
			System.out.println("Row :" + row);
			// Starting cells

			Iterator<Cell> cellIterator = xssfRow.cellIterator();
			int cols = 0;
			Timeperiod timePeriod = null;
			Indicator indicator = null;
			IndicatorClassification icSector = null;
			IndicatorClassification icSubSector = null;
			IndicatorClassification source = null;
			Subgroup subgroup = null;
			IndicatorClassificationIndicatorUnitSubgroupMapping icius;
			Unit unit = null;

			IndicatorUnitSubgroup ius = null;
			String areaCode = null;
			String dataValue = null;

			Cell cell = null;
			while (cellIterator.hasNext()) {
				cell = cellIterator.next();
				switch (cols) {
				case 0:
					if (cell.getStringCellValue() == null || cell.getStringCellValue().isEmpty()) {
						return true;
					}
					timePeriod = timePeriodRepository.findByTimePeriod(cell.getStringCellValue().trim());
					try {

						if (timePeriod == null) {
							System.out.println("cell value==:" + cell.getStringCellValue());
							System.out.println("month::" + cell.getStringCellValue().split("\\.")[1]);
							System.out.println("year::" + cell.getStringCellValue().split("\\.")[0]);
							int month = Integer.parseInt(cell.getStringCellValue().split("\\.")[1]);
							int year = Integer.parseInt(cell.getStringCellValue().split("\\.")[0]);
							String monthString = "";
							if (month >= 10) {
								monthString = month + "";
							} else {
								monthString = "0" + month;
							}
							Year y = Year.of(year);
							Month m = Month.of(month);

							String dayString = "";
							if (m.length(y.isLeap()) < 10) {
								dayString = "0" + m.length(y.isLeap());
							} else {
								dayString = m.length(y.isLeap()) + "";
							}

							timePeriod = new Timeperiod();
							timePeriod.setPeriodicity("1");
							try {
								timePeriod.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + monthString + "-01"));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								timePeriod.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + monthString + "-" + dayString));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							timePeriod.setTimePeriod(cell.getStringCellValue());
							timePeriod = timePeriodRepository.save(timePeriod);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 1:
					areaCode = cell.getStringCellValue().trim();
					break;
				case 3:
					icSector = indicatorClassificationRepository.findByNameAndParentIsNull(cell.getStringCellValue());
					break;
				case 4:
					icSubSector = indicatorClassificationRepository.findByNameAndParent(cell.getStringCellValue(), icSector);
					break;

				case 5:
					if (cell.getStringCellValue().isEmpty()) {
						System.out.println("Rows::" + sheet.getLastRowNum());
						System.out.println("Indicator Name is Empty");
						return true;
					}
					indicator = indicatorRepository.findByIndicatorNameAndAgencyAgencyId(cell.getStringCellValue(), agencyId);
					if (indicator == null) {
						// indicator = new Indicator();
						// indicator.setAgency(new Agency(agencyId));
						// indicator.setDisplayToDeoFromMonth(4);
						// indicator.setDisplayToDeoFromYear(2017);
						// indicator.setHighIsGood(true);
						// indicator.setIndicatorClassification(icSubSector);
						// indicator.setIndicatorMetadata(null);
						// indicator.setIndicatorName(cell.getStringCellValue());
						// indicator.setIndicatorType(IndicatorType.ACTUAL_INDICATOR);
						// indicator.setPublishMonth(3);
						// indicator.setPublishYear(2017);
						//
						// indicator = indicatorRepository.save(indicator);

						System.out.println("Indicator Name :" + cell.getStringCellValue());
						System.out.println("Row : :" + row);

						throw new IllegalArgumentException(cell.getStringCellValue());
					}
					break;
				case 6:
					dataValue = cell.getStringCellValue();
					break;
				case 7:
					System.out.println("Unit Name :" + cell.getStringCellValue());
					unit = unitRepository.findByUnitName(cell.getStringCellValue());
					break;
				case 8:
					System.out.println("Subgroup::::fromexcel::::" + cell.getStringCellValue().trim());
					subgroup = subgroupRepository.findBySubgroupVal(cell.getStringCellValue().trim());
					break;
				case 9:
					source = indicatorClassificationRepository.findByNameAndParentIsNotNull(cell.getStringCellValue());
					if (source == null) {
						source = new IndicatorClassification();
						source.setIcOrder(2);
						source.setIndex(false);
						source.setIndicatorClassificationType(IndicatorClassificationType.SR);
						source.setName(cell.getStringCellValue());
						source.setParent(new IndicatorClassification(18));
					}

					break;
				}

				cols++;

			}

			// start persisting

			ius = new IndicatorUnitSubgroup();
			ius.setIndicator(indicator);
			ius.setSubgroup(subgroup);
			ius.setUnit(unit);
			System.out.println("------------------------------------------------");
			System.out.println("Row ::" + row);
			System.out.println("INdicator Name ::" + indicator.getIndicatorName());
			System.out.println("Subgroup Name ::" + subgroup.getSubgroupVal());
			System.out.println("Sector ::" + icSector.getName());
			System.out.println("SubSector ::" + icSubSector.getName());
			System.out.println("Source ::" + source.getName());
			System.out.println("Action Unit Retrived from excel :" + cell.getStringCellValue());
			System.out.println("Unit ::" + unit.getUnitName());
			if (indicatorUnitSubgroupRepository.findByIndicatorAndUnitAndSubgroup(indicator, unit, subgroup) == null)
				ius = indicatorUnitSubgroupRepository.save(ius);
			else
				ius = indicatorUnitSubgroupRepository.findByIndicatorAndUnitAndSubgroup(indicator, unit, subgroup);

			icius = new IndicatorClassificationIndicatorUnitSubgroupMapping();
			icius.setIndicatorClassification(icSubSector);
			icius.setIndicatorUnitSubgroup(ius);

			if (indicatorClassification_Ius_Mapping_Repository.findByIndicatorClassificationAndIndicatorUnitSubgroup(icSubSector, ius) == null)
				icius = indicatorClassification_Ius_Mapping_Repository.save(icius);
			else
				icius = indicatorClassification_Ius_Mapping_Repository.findByIndicatorClassificationAndIndicatorUnitSubgroup(icSubSector, ius);

			Data data = new Data();

			Area area = areaRepository.findByAreaCode(areaCode);

			data.setArea(area);
			data.setDenominator(0);
			data.setIndicator(indicator);
			data.setIndicatorUnitSubgroup(ius);
			data.setNumerator(0);
			data.setPercentage(new Double(dataValue));
			data.setPublished(true);
			data.setSource(source);
			data.setSubgroup(subgroup);
			data.setTimePeriod(timePeriod);
			data.setUnit(unit);

			dataEntryRepository.save(data);

		}
		return true;
	}

	@Transactional
	public boolean createFacilitiesFromExcel() throws InvalidFormatException, IOException {

		File file = new File("E:\\DATA\\SCPS-TAMILNADU\\Details of the Registered CCIs (1).xlsx");

		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet sheet = workbook.getSheetAt(1);
		int cci_user_count = 0;
		int saa_user_count = 0;
		for (int row = 0; row < sheet.getLastRowNum(); row++) {
			XSSFRow xssfRow = sheet.getRow(row);
			// Starting cells
			Iterator<Cell> cellIterator = xssfRow.cellIterator();
			int cols = 0;
			Cell cell = null;

			String districtName = null;
			String rollName = null;
			String facilityName = null;
			String cciType = null;
			String servingDistricts = null;

			while (cellIterator.hasNext()) {
				cell = cellIterator.next();

				switch (cols) {
				case 0:

					break;
				case 1:
					facilityName = cell.getStringCellValue();
					break;
				case 2:
					cciType = cell.getStringCellValue();
					break;
				case 3:
					servingDistricts = cell.getStringCellValue();
					break;
				case 4:
					rollName = cell.getStringCellValue();
					break;
				case 5:
					districtName = cell.getStringCellValue();
					break;
				}
				cols++;
			}

			Area area = areaRepository.findByAreaNameAndParentAreaId(districtName, 2);
			if (area == null) {
				System.out.println("Area Name::::" + districtName);
				createOtherUser();
				return true;
			}
			Facility facility = new Facility();
			facility.setDistrict(area);
			facility.setFaciltyName(facilityName);
			facility.setServingDistricts(servingDistricts);
			facility.setCciType(cciType);

			Role role = roleRepository.findByRoleName(rollName.trim());
			if (role == null) {
				throw new RuntimeException("Role cannot be null");
			}

			User user = new User();
			user.setAgency(new Agency(1));
			user.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
			user.setEmail(null);
			user.setInvalidAttempts((short) 0);
			user.setIsActive(true);
			System.out.println("Role Name ::" + role.getRoleName());
			System.out.println("Area Name ::" + area.getAreaName().toUpperCase());
			if (role.getRoleCode().toString().equalsIgnoreCase("CCI")) {
				user.setName(role.getRoleName().concat("_").concat(area.getAreaName().toUpperCase()).concat("_").concat(Integer.toString((++cci_user_count))));
				user.setUserName(role.getRoleName().toLowerCase().concat("_").concat(area.getAreaName().toLowerCase()).concat("_").concat(Integer.toString((cci_user_count))));
				user.setPassword(user.generatedEncodedPassword(user.getUserName(), user.getUserName().concat("_").concat(Integer.toString(role.getRoleId()))));
			} else if (role.getRoleCode().toString().equalsIgnoreCase("SAA")) {
				user.setName(role.getRoleName().concat("_").concat(area.getAreaName().toUpperCase()).concat("_").concat(Integer.toString((++saa_user_count))));
				user.setUserName(role.getRoleName().toLowerCase().concat("_").concat(area.getAreaName().toLowerCase()).concat("_").concat(Integer.toString((saa_user_count))));
				user.setPassword(user.generatedEncodedPassword(user.getUserName(), user.getUserName().concat("_").concat(Integer.toString(role.getRoleId()))));

			} else {
				user.setName(role.getRoleName().concat("_").concat(area.getAreaName().toUpperCase()));
				user.setUserName(role.getRoleName().toLowerCase().concat("_").concat(area.getAreaName().toLowerCase()));
				user.setPassword(user.generatedEncodedPassword(user.getUserName(), user.getUserName().concat("_").concat(role.getRoleId() + "")));
			}
			user.setRole(role);
			user.setOtp(null);
			user.setOtpGeneratedDateTime(null);
			user = userRepository.save(user);
			Facility facilityDomain = facilityRepository.save(facility);

			FacilityUserMapping facilityUserMapping = new FacilityUserMapping();
			facilityUserMapping.setFacility(facilityDomain);
			facilityUserMapping.setUser(user);

			facilityUserMapping = facilityUserMappingRepository.save(facilityUserMapping);

			UserAreaMapping userAreaMapping = new UserAreaMapping();
			userAreaMapping.setArea(area);
			userAreaMapping.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
			userAreaMapping.setIsActive(true);
			userAreaMapping.setUser(user);

			userAreaMapping = userAreaMappingRepository.save(userAreaMapping);

			List<RoleFeaturePermissionScheme> roleFeature = roleFeaturePermissionRepository.findByRole(role);

			for (RoleFeaturePermissionScheme roleFeaturePermissionScheme : roleFeature) {

				UserRoleFeaturePermissionMapping userRoleFeaturePermissionMapping = new UserRoleFeaturePermissionMapping();
				userRoleFeaturePermissionMapping.setRoleFeaturePermissionScheme(roleFeaturePermissionScheme);
				userRoleFeaturePermissionMapping.setUserAreaMapping(userAreaMapping);

				userRoleFeaturePermissionMappingRepository.save(userRoleFeaturePermissionMapping);

			}
		}

		return true;
	}

	@Transactional
	public boolean createOtherUser() {

		// create all users for all districts of Tamil Nadu For All Roles Leaving CCI and SAA
		List<String> rols = new ArrayList<String>();
		rols.add("JJB");
		rols.add("CWC");
		rols.add("DCPU");
		rols.add("SJPU");
		List<Role> roles = roleRepository.findAllByRoleNameIn(rols);

		List<Area> districts = areaRepository.findAllByParentAreaId(2);

		for (Role role : roles) {

			for (Area district : districts) {

				User user = new User();
				user.setAgency(new Agency(1));
				user.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
				user.setEmail(null);
				user.setInvalidAttempts((short) 0);
				user.setIsActive(true);

				user.setName(role.getRoleName().concat("_").concat(district.getAreaName().toUpperCase()));
				user.setUserName(role.getRoleName().toLowerCase().concat("_").concat(district.getAreaName().toLowerCase()));
				user.setPassword(user.generatedEncodedPassword(user.getUserName(), user.getUserName().concat("_").concat(role.getRoleId() + "")));
				user.setRole(role);
				user.setOtp(null);
				user.setOtpGeneratedDateTime(null);

				user = userRepository.save(user);

				Facility facility = new Facility();
				facility.setDistrict(district);
				facility.setFaciltyName(role.getRoleName().toLowerCase().concat("_").concat(district.getAreaName().toLowerCase()));
				facility.setServingDistricts(district.getAreaName());
				facility.setCciType(null);

				Facility facilityDomain = facilityRepository.save(facility);

				FacilityUserMapping facilityUserMapping = new FacilityUserMapping();
				facilityUserMapping.setFacility(facilityDomain);
				facilityUserMapping.setUser(user);

				facilityUserMapping = facilityUserMappingRepository.save(facilityUserMapping);

				UserAreaMapping userAreaMapping = new UserAreaMapping();
				userAreaMapping.setArea(district);
				userAreaMapping.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
				userAreaMapping.setIsActive(true);
				userAreaMapping.setUser(user);

				userAreaMapping = userAreaMappingRepository.save(userAreaMapping);

				List<RoleFeaturePermissionScheme> roleFeature = roleFeaturePermissionRepository.findByRole(role);

				for (RoleFeaturePermissionScheme roleFeaturePermissionScheme : roleFeature) {

					UserRoleFeaturePermissionMapping userRoleFeaturePermissionMapping = new UserRoleFeaturePermissionMapping();
					userRoleFeaturePermissionMapping.setRoleFeaturePermissionScheme(roleFeaturePermissionScheme);
					userRoleFeaturePermissionMapping.setUserAreaMapping(userAreaMapping);

					userRoleFeaturePermissionMappingRepository.save(userRoleFeaturePermissionMapping);

				}
			}

		}

		return true;

	}

	@Override
	@Transactional
	public boolean createUserProgramXformMapping() {

		List<User> users = userRepository.findAllByOrderByUserIdAsc();

		List<User_Program_XForm_Mapping> ps = new ArrayList<>();
		Program_XForm_Mapping ccipmapping = program_XForm_MappingRepository.findByProgramXFormMappingId(1);
		Program_XForm_Mapping cwcpmapping = program_XForm_MappingRepository.findByProgramXFormMappingId(2);
		Program_XForm_Mapping dcpupmapping = program_XForm_MappingRepository.findByProgramXFormMappingId(3);
		Program_XForm_Mapping jjbpmapping = program_XForm_MappingRepository.findByProgramXFormMappingId(4);
		Program_XForm_Mapping sjpupmapping = program_XForm_MappingRepository.findByProgramXFormMappingId(5);
		Program_XForm_Mapping saapmapping = program_XForm_MappingRepository.findByProgramXFormMappingId(6);

		users.forEach(user -> {
			switch (user.getRole().getRoleId()) {

			case 1: {
				User_Program_XForm_Mapping mapping = new User_Program_XForm_Mapping();

				mapping.setCollectUser(user);
				mapping.setProgram_XForm_Mapping(ccipmapping);
				mapping.setIsLive(true);

				user_Program_XForm_MappingRepository.save(mapping);
				ps.add(mapping);
			}
				break;

			case 2: {
				User_Program_XForm_Mapping mapping = new User_Program_XForm_Mapping();

				mapping.setCollectUser(user);
				mapping.setProgram_XForm_Mapping(jjbpmapping);
				mapping.setIsLive(true);

				user_Program_XForm_MappingRepository.save(mapping);
				ps.add(mapping);
			}
				break;

			case 3: {
				User_Program_XForm_Mapping mapping = new User_Program_XForm_Mapping();

				mapping.setCollectUser(user);
				mapping.setProgram_XForm_Mapping(cwcpmapping);
				mapping.setIsLive(true);

				user_Program_XForm_MappingRepository.save(mapping);
				ps.add(mapping);
			}
				break;

			case 4: {
				User_Program_XForm_Mapping mapping = new User_Program_XForm_Mapping();

				mapping.setCollectUser(user);
				mapping.setProgram_XForm_Mapping(dcpupmapping);
				mapping.setIsLive(true);

				user_Program_XForm_MappingRepository.save(mapping);
				ps.add(mapping);
			}
				break;

			case 5: {
				User_Program_XForm_Mapping mapping = new User_Program_XForm_Mapping();

				mapping.setCollectUser(user);
				mapping.setProgram_XForm_Mapping(saapmapping);
				mapping.setIsLive(true);

				user_Program_XForm_MappingRepository.save(mapping);
				ps.add(mapping);
			}
				break;

			case 7: {
				User_Program_XForm_Mapping mapping = new User_Program_XForm_Mapping();

				mapping.setCollectUser(user);
				mapping.setProgram_XForm_Mapping(sjpupmapping);
				mapping.setIsLive(true);

				user_Program_XForm_MappingRepository.save(mapping);
				ps.add(mapping);
			}
				break;

			}

			// user_Program_XForm_MappingRepository.save(ps);

		});

		return true;
	}

	@Transactional
	@Override
	public boolean updateFaciltynames() {

		List<Facility> facilities = facilityRepository.findAllByCciTypeIsNull();

		facilities.forEach(facility -> {

			if (facility.getFaciltyName().toLowerCase().startsWith("sjpu_")) {

				facility.setFaciltyName("Special Juvenile Police Units : ".concat(facility.getDistrict().getAreaName()));

			} else if (facility.getFaciltyName().toLowerCase().startsWith("jjb_")) {
				
				facility.setFaciltyName("Juvenile Justice Board : ".concat(facility.getDistrict().getAreaName()));
			
			} else if (facility.getFaciltyName().toLowerCase().startsWith("cwc_")) {
				
				facility.setFaciltyName("Child Welfare Committee : ".concat(facility.getDistrict().getAreaName()));
			
			} else if (facility.getFaciltyName().toLowerCase().startsWith("dcpu_")) {
			
				facility.setFaciltyName("District Child Protection Unit : ".concat(facility.getDistrict().getAreaName()));
			} 

		});

		return true;
	}

}
