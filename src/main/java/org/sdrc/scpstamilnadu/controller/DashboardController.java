package org.sdrc.scpstamilnadu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.sdrc.core.Authorize;
import org.sdrc.scpstamilnadu.domain.Agency;
import org.sdrc.scpstamilnadu.model.DataCollectionModel;
import org.sdrc.scpstamilnadu.model.LineSeries;
import org.sdrc.scpstamilnadu.model.UserModel;
import org.sdrc.scpstamilnadu.model.ValueObject;
import org.sdrc.scpstamilnadu.repository.AgencyRepository;
import org.sdrc.scpstamilnadu.service.DashboardService;
import org.sdrc.scpstamilnadu.util.Constants;
import org.sdrc.scpstamilnadu.util.StateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Azaruddin (azaruddin@sdrc.co.in)
 *
 */

@Controller
public class DashboardController {

	private final DashboardService dashboardService;

	@Autowired
	private AgencyRepository agencyRepository;

	@Autowired
	private StateManager stateManager;

	@Autowired
	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	@RequestMapping(value = { "/api/indicators" }, method = { RequestMethod.GET })
	@ResponseBody
	public List<ValueObject> fetchIndicators(@RequestParam(required = false) String sector, HttpServletRequest re) throws Exception {
		List<ValueObject> valueObjects = new ArrayList<>();
		if (sector != null) {
			valueObjects = dashboardService.fetchIndicators(sector);
		}
		return valueObjects;
	}

	@RequestMapping(value = { "/api/sources" }, method = { RequestMethod.GET })
	@ResponseBody
	public List<ValueObject> fetchSources(@RequestParam(required = false) String iusnid, HttpServletRequest re) throws Exception {
		List<ValueObject> valueObjects = new ArrayList<>();
		if (iusnid != null) {
			valueObjects = dashboardService.fetchSources(iusnid);
		}
		return valueObjects;
	}

	@RequestMapping(value = { "/api/timeperiod" }, method = { RequestMethod.GET })
	@ResponseBody
	public List<ValueObject> fetchUtTimeperiod(@RequestParam(required = false) String iusnid, @RequestParam(required = false) String sourceNid) throws Exception {

		List<ValueObject> valueObjects = new ArrayList<>();
		try {
			if (iusnid != null && sourceNid != null) {
				valueObjects = dashboardService.fetchUtTimeperiod(Integer.parseInt(iusnid), Integer.parseInt(sourceNid));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueObjects;
	}

	@RequestMapping(value = { "/api/sectors" }, method = { RequestMethod.GET })
	@ResponseBody
	public JSONArray fetchAllSectors() throws Exception {
		return dashboardService.fetchAllSectors();
	}

	@RequestMapping(value = "/api/data", method = RequestMethod.GET)
	@ResponseBody
	public DataCollectionModel fetchData(@RequestParam(required = false) String indicatorId, @RequestParam(required = false) String sourceNid, @RequestParam(required = false) String areaId, @RequestParam(required = false) String timeperiodId, @RequestParam(required = false) Integer childLevel) throws Exception {

		DataCollectionModel valList = new DataCollectionModel();
		if (indicatorId != null && sourceNid != null && timeperiodId != null) {
			valList = dashboardService.fetchData(indicatorId, sourceNid, areaId, timeperiodId, childLevel);
		}
		return valList;
	}

	@RequestMapping(value = "/api/exportPDF", method = RequestMethod.POST)
	@ResponseBody
	public String exportPDF(@RequestBody List<String> svgs, @RequestParam(required = false) String indicatorId, @RequestParam(required = false) String sourceNid, @RequestParam(required = false) String areaId, @RequestParam(required = false) String timeperiodId, @RequestParam(required = false) Integer childLevel) {
		return dashboardService.exportPDF(svgs, indicatorId, sourceNid, areaId, timeperiodId, childLevel);
	}

	@RequestMapping(value = "/api/lineData", method = RequestMethod.GET)
	@ResponseBody
	public List<List<LineSeries>> fetchLineData(@RequestParam(required = false) Integer iusNid, @RequestParam(required = false) Integer areaNid) throws Exception {

		return dashboardService.fetchChartData(iusNid, areaNid);
	}

	@RequestMapping(value = "exportLineChart", method = RequestMethod.POST)
	@ResponseBody
	public String exportToPDFLine(@RequestBody List<String> svgs, @RequestParam(value = "iusNid", required = false) Integer iusNid, @RequestParam(value = "areaNid", required = false) Integer areaNid, HttpServletRequest re) {

		return dashboardService.exportPDFLine(svgs, iusNid, areaNid);
	}

	@RequestMapping(value = "/downloadPDF", method = RequestMethod.POST)
	public void downLoad(@RequestParam("fileName") String name, HttpServletResponse response) throws IOException {
		InputStream inputStream;
		String fileName = "";
		try {
			fileName = name.replaceAll("%3A", ":").replaceAll("%2F", "/").replaceAll("%5C", "/").replaceAll("%2C", ",").replaceAll("\\+", " ").replaceAll("%22", "").replaceAll("%3F", "?").replaceAll("%3D", "=");
			inputStream = new FileInputStream(fileName);
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", new java.io.File(fileName).getName());
			response.setHeader(headerKey, headerValue);
			response.setContentType("application/octet-stream"); // for all file
																	// type
			ServletOutputStream outputStream = response.getOutputStream();
			FileCopyUtils.copy(inputStream, outputStream);
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			new File(fileName).delete();
		}
	}

	@Authorize(feature = "dashboard", permission = "edit")
	@RequestMapping("/api/publishData")
	@ResponseBody
	public Map<String, String> publishData() {
		Map<String, String> status = new HashMap<>();
		try {
			if (dashboardService.publishData()) {
				status.put("status", "200");
				status.put("message", "Data published successfully !");
			} else {
				status.put("status", "0");
				status.put("message", "Failed to published data !");
			}
		} catch (Exception e) {
			e.printStackTrace();
			status.put("status", "0");
			status.put("message", "Failed to published data !");
		}
		return status;
	}

	@RequestMapping("/index")
	public ModelAndView getIndexPage(@RequestParam(required = false) String agencyId, HttpServletResponse res, HttpServletRequest re) {
		ModelAndView view = new ModelAndView("index");
		return view;
	}

	@RequestMapping("/dashboard")
	public ModelAndView getDashboard(@RequestParam(required = false) String agencyId, HttpSession session, HttpServletResponse res, HttpServletRequest re) {

		ModelAndView view = new ModelAndView("dashboard");
		view.addObject("showPublish", false);
		UserModel userModel = ((UserModel) stateManager.getValue(Constants.Web.USER_PRINCIPAL));
		if (userModel == null) {
			view.addObject("showPublish", false);
		} else {
			boolean isPublished = dashboardService.displayPublishButton();
			view.addObject("showPublish", isPublished);
		}

		return view;
	}

	@RequestMapping("/api/findMapUrl")
	@ResponseBody
	public JSONObject findMapUrl(HttpServletResponse res, HttpServletRequest re) {

		Agency agency = agencyRepository.findByAgencyId(1);
		try {
			return (JSONObject) new JSONParser().parse(agency.getMapUrl());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
