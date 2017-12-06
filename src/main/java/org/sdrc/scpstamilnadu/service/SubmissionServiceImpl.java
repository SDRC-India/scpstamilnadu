package org.sdrc.scpstamilnadu.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bushe.swing.event.EventBus;
import org.json.JSONObject;
import org.json.XML;
import org.opendatakit.briefcase.model.BriefcaseFormDefinition;
import org.opendatakit.briefcase.model.DocumentDescription;
import org.opendatakit.briefcase.model.FormStatus;
import org.opendatakit.briefcase.model.FormStatusEvent;
import org.opendatakit.briefcase.model.ServerConnectionInfo;
import org.opendatakit.briefcase.model.TerminationFuture;
import org.opendatakit.briefcase.util.AggregateUtils;
import org.opendatakit.briefcase.util.ServerUploader.SubmissionResponseAction;
import org.sdrc.scpstamilnadu.domain.Indicator;
import org.sdrc.scpstamilnadu.domain.Program_XForm_Mapping;
import org.sdrc.scpstamilnadu.domain.User;
import org.sdrc.scpstamilnadu.domain.User_Program_XForm_Mapping;
import org.sdrc.scpstamilnadu.domain.XForm;
import org.sdrc.scpstamilnadu.model.CollectUserModel;
import org.sdrc.scpstamilnadu.model.DataEntryModel;
import org.sdrc.scpstamilnadu.model.PostSubmissionModel;
import org.sdrc.scpstamilnadu.model.XFormModel;
import org.sdrc.scpstamilnadu.repository.AreaRepository;
import org.sdrc.scpstamilnadu.repository.CollectUserRepository;
import org.sdrc.scpstamilnadu.repository.FacilityUserMappingRepository;
import org.sdrc.scpstamilnadu.repository.IndicatorRepository;
import org.sdrc.scpstamilnadu.repository.User_Program_XForm_MappingRepository;
import org.sdrc.scpstamilnadu.repository.XFormRepository;
import org.sdrc.scpstamilnadu.thread.PostSubmissionThread;
import org.sdrc.scpstamilnadu.util.Constants;
import org.sdrc.scpstamilnadu.util.DomainToModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.itextpdf.text.pdf.codec.Base64;

/**
 * This class is responsible for the submission of forms
 * 
 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
 * @author Sarita
 * @since version 1.0.0
 *
 */
@Service
public class SubmissionServiceImpl implements SubmissionService {
	@Autowired
	private FacilityUserMappingRepository facilityUserMappingRepository;
	
	@Autowired
	private XFormRepository xFormRepository;

	@Autowired
	private CollectUserRepository collectUserRepository;

	@Autowired
	private User_Program_XForm_MappingRepository user_Program_XForm_MappingRepository;

	@Autowired
	private ResourceBundleMessageSource messages;

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private DomainToModelConverter domainToModelConverter;

	@Autowired
	private ServletContext context;

	@Autowired
	private ResourceBundleMessageSource applicationMessageSource;
	
	@Autowired
	private IndicatorRepository indicatorRepository;
	
	
	@Autowired
	private DataEntryService dataEntryService;

	private static final Logger logger = Logger.getLogger(SubmissionServiceImpl.class);

	/**
	 * This method will be responsible for returning all file names that are sent from the mobile app
	 * 
	 * @return files All file names that are sent from the mobile app
	 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
	 * @since version 1.0.0.0
	 */
	public List<String> allFilesFromMobileApp(String folderName) {

		List<String> files = new ArrayList<String>();
		File folder = new File(messages.getMessage(Constants.Odk.ODK_SERVER_DIRECTORY, null, null) + "\\" + messages.getMessage(Constants.Odk.ODK_DEFAULT_UPLOAD_FOLDER_NAME, null, null) + "\\" + folderName);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				files.add(listOfFiles[i].getName());
			}
		}
		return files;
	}

	private Integer validateUserFormMapping(XForm xForm, CollectUserModel collectUserModel) {
		// see whether the user has this particular form assigned or not
		List<User_Program_XForm_Mapping> User_Program_XForm_Mappings = user_Program_XForm_MappingRepository.findByUser(collectUserModel.getUsername());

		for (User_Program_XForm_Mapping user_Program_XForm_Mapping : User_Program_XForm_Mappings) {
			// get program with xForm mapping
			Program_XForm_Mapping program_XForm_Mapping = user_Program_XForm_Mapping.getProgram_XForm_Mapping();
			if (program_XForm_Mapping != null) {
				if (program_XForm_Mapping.getxForm().getFormId() == xForm.getFormId()) {

					return user_Program_XForm_Mapping.getUserProgramXFomrMappingId();
				}
			}
		}
		return null;

	}

	/**
	 * This method is responsible for extracting form id from the xml file.
	 * 
	 * @return formId
	 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
	 * @since version 1.0.0.0
	 */
	private Map<String, String> getFormIdFromXML(String folderName, String fileName) {
		Map<String, String> map = new HashMap<>(1);
		try {
			String formId = null;
			File inputFile = new File(messages.getMessage(Constants.Odk.ODK_SERVER_DIRECTORY, null, null) + "\\" + messages.getMessage(Constants.Odk.ODK_DEFAULT_UPLOAD_FOLDER_NAME, null, null) + "\\" + folderName + "\\" + fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			NodeList nList2 = doc.getChildNodes();
			String xFormId = nList2.item(0).getNodeName();
			formId = nList2.item(0).getAttributes().item(0).getTextContent();
			map.put(formId, xFormId);
			return map;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error("Exception in getting form id from XML, Exception message : ", e);
			return null;
		}
	}

	/**
	 * This method will persist the record in the database
	 * 
	 * @param areaId
	 *            Area id for which we are submitting the data
	 * @param fileName
	 *            The submission file name
	 */

	/**
	 * This method is responsible for extracting form id from the xml file.
	 * 
	 * @return formId
	 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
	 * @since version 1.0.0.0
	 */
	/*
	 * private String getInstanceIdFromXML(String fileNamePath) { try { String instanceId = null; File inputFile = new
	 * File(fileNamePath); DocumentBuilderFactory dbFactory = DocumentBuilderFactory .newInstance(); DocumentBuilder
	 * dBuilder = dbFactory.newDocumentBuilder(); org.w3c.dom.Document doc = dBuilder.parse(inputFile);
	 * doc.getDocumentElement().normalize(); instanceId = doc.getElementsByTagName("instanceID").item(0)
	 * .getTextContent(); instanceId = instanceId.replace(":", ""); return instanceId; } catch
	 * (ParserConfigurationException | SAXException | IOException e) { logger.
	 * error("Exception in getting instance id from XML, Exception message : " + e.getMessage()); return null; } }
	 */

	public boolean upload(String folderName, String fileName, String formId, Map<String, List<File>> formFilesMap, ServerConnectionInfo serverConnectionInfo, DocumentDescription documentDescription, TerminationFuture terminationFuture, XFormModel xFormModel, CollectUserModel collectUserModel, int userProgramXFormMappingId, String xFormIdFromOdk) {
		try {
			URI uri = null;

			// validate the URI from ui otherwise exception will occur
			uri = AggregateUtils.testServerConnectionWithHeadRequest(serverConnectionInfo, "formUpload");

			File file = new File(messages.getMessage(Constants.Odk.ODK_SERVER_DIRECTORY, null, null) + "\\" + messages.getMessage(Constants.Odk.ODK_DEFAULT_UPLOAD_FOLDER_NAME, null, null) + "\\" + folderName + "\\" + fileName);
			File dFile = new File(messages.getMessage(Constants.Odk.ODK_SERVER_DIRECTORY, null, null) + "\\" + messages.getMessage(Constants.Odk.ODK_DEFAULT_FORMS_FOLDER_NAME, null, null) + "\\" + formId + "\\" + messages.getMessage(Constants.Odk.ODK_DEFAULT_DESTINATIONFILE_FOLDER_NAME, null, null) + "\\" + formId + ".xml");

			BriefcaseFormDefinition lfd = null;
			File f = new File(messages.getMessage(Constants.Odk.ODK_SERVER_DIRECTORY, null, null) + "\\" + messages.getMessage(Constants.Odk.ODK_DEFAULT_FORMS_FOLDER_NAME, null, null) + "\\" + formId + "\\" + messages.getMessage(Constants.Odk.ODK_DEFAULT_DESTINATIONFILE_FOLDER_NAME, null, null));
			lfd = new BriefcaseFormDefinition(f, dFile);

			FormStatus formToTransfer = new FormStatus(FormStatus.TransferType.UPLOAD, lfd);
			SubmissionResponseAction action = null;

			if (!file.exists()) {
				String msg = "Submission file not found: " + file.getAbsolutePath();
				formToTransfer.setStatusString(msg, false);
				EventBus.publish(new FormStatusEvent(formToTransfer));
			}

			boolean uploadSuccess = AggregateUtils.uploadFilesToServer(serverConnectionInfo, uri, "xml_submission_file", file, formFilesMap.get(fileName), documentDescription, action, terminationFuture, formToTransfer);

			if (uploadSuccess) {
				// to be fetched from properties file

				PostSubmissionModel postSubmissionModel = new PostSubmissionModel();
				xFormModel.setxFormIdByODK(xFormIdFromOdk);
				postSubmissionModel.setxFormModel(xFormModel);
				postSubmissionModel.setFormFilesMap(formFilesMap);
				postSubmissionModel.setCollectUserModel(collectUserModel);

				// need the following for persisting data in the
				// PlanningSchedule table
				postSubmissionModel.setUserProgramXFormMappingId(userProgramXFormMappingId);
				postSubmissionModel.setSubmissionFileString(messages.getMessage(Constants.Odk.ODK_SERVER_DIRECTORY, null, null) + "\\" + messages.getMessage(Constants.Odk.ODK_DEFAULT_UPLOAD_FOLDER_NAME, null, null) + "\\" + folderName + "\\" + fileName);

				// Starting the thread
				PostSubmissionThread postSubmissionThread = (PostSubmissionThread) appContext.getBean("postSubmissionThread");
				postSubmissionThread.setPostSubmissionModel(postSubmissionModel);
				postSubmissionThread.start();
			}

			return uploadSuccess;
		} catch (Exception e) {
			logger.error("Exception in uploading process, Exception message :{} ", e);
			// e.printStackTrace();
			return false;
		}
	}

	/**
	 * The following method will extract the media file names from the xml file and convert it into File then return
	 * them
	 * 
	 * @param xmlFileName2
	 * @return media files
	 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
	 * @since version 1.0.0.0
	 */
	private Map<String, List<File>> extractMediaFiles(String folderName, String xmlFileName) {
		List<File> files = new ArrayList<File>();
		Map<String, List<File>> formFilesMap = new HashMap<String, List<File>>();

		try {

			File inputFile = new File(messages.getMessage(Constants.Odk.ODK_SERVER_DIRECTORY, null, null) + "\\" + messages.getMessage(Constants.Odk.ODK_DEFAULT_UPLOAD_FOLDER_NAME, null, null) + "\\" + folderName + "\\" + xmlFileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			String xmlString = "";

			FileInputStream fis = null;
			try {
				fis = new FileInputStream(inputFile);
				int content;
				while ((content = fis.read()) != -1) {
					// convert to char and display it
					xmlString += (char) content;
				}

			} catch (IOException e) {
				// e.printStackTrace();
				logger.error("Exception while creating file : ", e);
			} finally {
				try {
					if (fis != null)
						fis.close();
				} catch (IOException ex) {
					// ex.printStackTrace();
					logger.error("Exception while creating file : ", ex);
				}
			}

			List<String> imgList = getImageList(xmlString);

			for (String imageFileName : imgList) {
				try {
					File imageFile = new File(messages.getMessage(Constants.Odk.ODK_SERVER_DIRECTORY, null, null) + "\\" + messages.getMessage(Constants.Odk.ODK_DEFAULT_UPLOAD_FOLDER_NAME, null, null) + "\\" + folderName + "\\" + imageFileName);
					if (imageFile.exists())
						files.add(imageFile);
				} catch (NullPointerException e) {
					// e.printStackTrace();
					logger.error("Exception while creating imageFile file : ", e);
				}
			}
			formFilesMap.put(xmlFileName, files);
			return formFilesMap;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// e.printStackTrace();
			logger.error("Exception while creating imageFile file : ", e);
			return formFilesMap;
		}
	}

	/**
	 * This method will take xml file content as string and extract the jpg images and return as list of strings
	 * 
	 * @param xmlString
	 *            This parameter will bring the xml file content as string
	 * @return List of image file name
	 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
	 * @since version 1.0.0.0
	 */

	private List<String> getImageList(String xmlString) {
		List<String> imageFileNames = new ArrayList<String>();

		String array[] = xmlString.split(".jpg<");

		for (int i = 0; i < array.length - 1; i++) {
			String imgFile = null;
			for (int j = array[i].length() - 1; j >= 0; j--) {
				if (array[i].charAt(j) == '>') {
					imgFile = array[i].substring(j + 1) + ".jpg";
					break;
				}
			}
			imageFileNames.add(imgFile);
		}
		return imageFileNames;
	}

	

	// send submission attached mail to user

	@SuppressWarnings("unchecked")
	@Override
	public int uploadForm(HttpServletRequest request, String deviceID, CollectUserModel collectUserModel) {
		try {

			if (messages.getMessage(Constants.Odk.SUBMISSION_CREATE_FOLDER, null, null) == null) {
				throw new RuntimeException(Constants.Odk.SUBMISSION_CREATE_FOLDER + " property not found.");
			}

			if (messages.getMessage(Constants.Odk.ODK_SERVER_DIRECTORY, null, null) == null) {
				throw new RuntimeException(Constants.Odk.ODK_SERVER_DIRECTORY + " property not found.");
			}

			if (messages.getMessage(Constants.Odk.ODK_DEFAULT_UPLOAD_FOLDER_NAME, null, null) == null) {
				throw new RuntimeException(Constants.Odk.ODK_DEFAULT_UPLOAD_FOLDER_NAME + " property not found.");
			}

			SimpleDateFormat sdf = new SimpleDateFormat(messages.getMessage(Constants.Odk.SUBMISSION_CREATE_FOLDER, null, null));
			String folderName = collectUserModel.getUserId().toString() + "_" + sdf.format(new Date());

			if ((new File(messages.getMessage(Constants.Odk.ODK_SERVER_DIRECTORY, null, null) + File.separator + messages.getMessage(Constants.Odk.ODK_DEFAULT_UPLOAD_FOLDER_NAME, null, null) + File.separator + folderName)).mkdir()) {
				logger.info("Folder created");
				// Keeping all files from mobile device
				/*
				 * new MultipartRequest(request, new File(messages.getMessage(Constants.Odk.ODK_SERVER_DIRECTORY, null,
				 * null) + "\\" + messages.getMessage(Constants.Odk.ODK_DEFAULT_UPLOAD_FOLDER_NAME, null, null) + "\\" +
				 * folderName).toString(), Integer.parseInt(messages.getMessage(Constants.Odk.MAX_POST_SIZE, null,
				 * null)));
				 */

				//////////////////
				// cast request
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				// You can get your file from request
				CommonsMultipartFile multipartFile = null; // multipart file class depends on which class you use
															// assuming you are using
															// org.springframework.web.multipart.commons.CommonsMultipartFile

				Iterator<String> iterator = multipartRequest.getFileNames();

				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					// create multipartFile array if you upload multiple files
					multipartFile = (CommonsMultipartFile) multipartRequest.getFile(key);
					String path = (messages.getMessage(Constants.Odk.ODK_SERVER_DIRECTORY, null, null) + File.separator + messages.getMessage(Constants.Odk.ODK_DEFAULT_UPLOAD_FOLDER_NAME, null, null) + File.separator + folderName).toString();
					File convFile = new File(path, multipartFile.getOriginalFilename());
					multipartFile.transferTo(convFile);

					// Read the XML File and insert contents into local db
				}

				/////////////////////////

				// Set respective files in respective places
				List<String> xmlFileNames = new ArrayList<>();
				List<String> mediaFileNames = new ArrayList<>();

				User user = collectUserRepository.findByUserId(collectUserModel.getUserId());
				for (String fileName : allFilesFromMobileApp(folderName)) {
					File inputFile = new File(messages.getMessage(Constants.Odk.ODK_SERVER_DIRECTORY, null, null) + "\\" + messages.getMessage(Constants.Odk.ODK_DEFAULT_UPLOAD_FOLDER_NAME, null, null) + "\\" + folderName + "\\" + fileName);

					if (fileName.substring(fileName.length() - 4, fileName.length()).equals(".xml")) {
						xmlFileNames.add(fileName);
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
						StringBuilder responseStrBuilder = new StringBuilder();

						String inputStr;
						while ((inputStr = bufferedReader.readLine()) != null) {
							responseStrBuilder.append(inputStr);
						}
						//parsed xml to json 
						JSONObject jsonObject = XML.toJSONObject(responseStrBuilder.toString());

						System.out.println("XML File content to JSON  ::" + jsonObject.toString());
						
						String formId = null;
						DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
						org.w3c.dom.Document doc = dBuilder.parse(inputFile);
						doc.getDocumentElement().normalize();
						NodeList nList2 = doc.getChildNodes();		
						JSONObject formDetailsJson =	jsonObject.getJSONObject(nList2.item(0).getNodeName());
						List<String> commaSeparatedIndicatorNames = (List<String>) formDetailsJson.keySet().stream().collect(Collectors.toList());
						
						//Map<String, Object> keyValuePair = new ObjectMapper().readValue(formDetailsJson.toString(), HashMap.class);
						
						System.out.println("Indicators Names filtered out are :"+commaSeparatedIndicatorNames);
						
						List<DataEntryModel> dataEntryModels = new ArrayList<>();
						List<Indicator> indicators = indicatorRepository.findAllByIndicatorNameIn(commaSeparatedIndicatorNames,user.getRole().getRoleId());
						
						indicators.forEach(indicator->{

								DataEntryModel model = new DataEntryModel();
								model.setIndicatorId(indicator.getIndicatorId());
								model.setDenominatorValue(Integer.parseInt(formDetailsJson.get(indicator.getDenominatorXpath()).toString()));
								model.setNumeratorValue(Integer.parseInt(formDetailsJson.get(indicator.getNumeratorXpath()).toString()));
								model.setPercentage(formDetailsJson.get(indicator.getPercentageXPath()).toString());
								
								
								dataEntryModels.add(model);
							
						});
//				
						boolean isSaved =	dataEntryService.saveIndicatorsJsonFromDataEntryFromMobile(dataEntryModels,user);
						
						// Querying Database to fetch all mapped xpath Names.

					} else
						mediaFileNames.add(fileName);
				}

				// Do the upload work
				for (String fileName : xmlFileNames) {
					Map<String, String> map = getFormIdFromXML(folderName, fileName);

					if (map != null) {

						String formId = null;
						String xFormIdFromODK = null;

						for (Map.Entry<String, String> entry : map.entrySet()) {
							formId = entry.getKey();
							xFormIdFromODK = entry.getValue();
							break;
						}

						if (formId != null || xFormIdFromODK != null) {

							XForm xForm = xFormRepository.findByXFormIdAndIsLiveTrue(formId);
							XFormModel xFormModel = domainToModelConverter.toXFormModel(xForm);
							if (xForm != null) {

								String decodedPasswordString = new String(Base64.decode(xForm.getPassword()));// xForm.getPassword()
																												// can
																												// not
																												// be
																												// null
								// because the password field in xForm table will not allow null value entry
								TerminationFuture terminationFuture = new TerminationFuture();
								DocumentDescription description = new DocumentDescription("Submission upload failed.  Detailed error: ", "Submission upload failed.", "submission (" + 0 + " of " + 1 + ")", terminationFuture);
								ServerConnectionInfo serverConnectionInfo = new ServerConnectionInfo(xForm.getOdkServerURL() + "submission?deviceID=" + deviceID, xForm.getUsername(), decodedPasswordString.toCharArray());

								Map<String, List<File>> formFilesMap = extractMediaFiles(folderName, fileName);
								Integer upxm_id = validateUserFormMapping(xForm, collectUserModel);
								if (upxm_id != null) {

									if (upload(folderName, fileName, formId, formFilesMap, serverConnectionInfo, description, terminationFuture, xFormModel, collectUserModel, upxm_id, xFormIdFromODK)) {
										// xml
										// we have to read xml here and insert into transaction table.

										logger.info("Submission success for filename : " + folderName + "\\" + fileName);
										return 0;
									} else {
										logger.error("Form upload failure for filename : " + folderName + "\\" + fileName);
										return 500;
									}
								} else {
									logger.warn("Submission failure, unassigned form : " + folderName + "\\" + fileName);
									return 406;
								}
							} else {
								logger.warn("No xForm found in the database in this file name : " + folderName + "\\" + fileName);
								return 500;
							}
						} else {
							logger.warn("Could not extract form id from this file : " + folderName + "\\" + fileName);
							return 500;
						}

					} else {
						logger.warn("Could not extract form id from this file : " + folderName + "\\" + fileName);
						return 500;
					}
				}

				return 0;

			} else {

				logger.error("Could not create folder name :" + folderName);
				return 500;
			}

		} catch (Exception e) {
			 e.printStackTrace();
			logger.error("Exception while creating folder :{} ", e);
			return 500;
		}

	}

	/**
	 * @author Sarita Panigrahi(sarita@sdrc.co.in)
	 * @param xFormModel
	 * @return This method will create a map with all the choice id and names of the xls file
	 */
	private Map<String, Map<String, String>> getListNameLabelMapFromChoiceSheet(XFormModel xFormModel) {
		Map<String, String> nameLabelMap = new HashMap<String, String>();
		Map<String, Map<String, String>> listNameLabelMap = new HashMap<String, Map<String, String>>();

		String inputFilePath = context.getRealPath(applicationMessageSource.getMessage("readFile.filepath", null, null)) + "\\" + xFormModel.getxFormId() + ".xlsx";
		FileInputStream fileInputStream = null;
		XSSFWorkbook xssfWorkbook = null;
		try {
			fileInputStream = new FileInputStream(inputFilePath);
			xssfWorkbook = new XSSFWorkbook(fileInputStream);
		} catch (IOException e) {
			// e.printStackTrace();
			logger.error("Exception while creating xssfWorkbook : ", e);
		}
		XSSFSheet choicesSheet = xssfWorkbook.getSheet("choices");

		for (int i = 1; i <= choicesSheet.getLastRowNum(); i++) {
			XSSFRow row = choicesSheet.getRow(i);

			if (null != row) {
				XSSFCell listNameCell = row.getCell(0);
				XSSFCell nameCell = row.getCell(1);
				XSSFCell labelCell = row.getCell(2);

				if (null != listNameCell && null != labelCell && null != nameCell) {
					String nameVal = nameCell.getCellType() == Cell.CELL_TYPE_STRING ? nameCell.getStringCellValue() : Integer.toString(((Double) nameCell.getNumericCellValue()).intValue());

					String labelVal = labelCell.getCellType() == Cell.CELL_TYPE_STRING ? labelCell.getStringCellValue() : Integer.toString(((Double) labelCell.getNumericCellValue()).intValue());

					if (listNameLabelMap.containsKey(listNameCell.getStringCellValue())) {

						listNameLabelMap.get(listNameCell.getStringCellValue()).put(nameVal.trim(), labelVal);
					} else {
						nameLabelMap = new HashMap<String, String>();

						nameLabelMap.put(nameVal.trim(), labelVal);

						listNameLabelMap.put(listNameCell.getStringCellValue().trim(), nameLabelMap);
					}
				}

			}

		}

		return listNameLabelMap;
	}

	@Override
	public void postSubmissionWork(PostSubmissionModel postSubmissionModel) {
		// TODO Auto-generated method stub

	}

}
