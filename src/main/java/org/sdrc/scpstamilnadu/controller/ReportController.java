package org.sdrc.scpstamilnadu.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.sdrc.scpstamilnadu.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReportController {

	@Autowired
	ReportService reportService;

	@RequestMapping("/editedReport")
	public void getEditedReportFile(@RequestParam("month") Integer month,@RequestParam("year") Integer year, HttpServletResponse httpServletResponse)  {
		File file =null;
		try {
			file = reportService.generateEditReport(month, year);
			String mimeType;
			mimeType = "application/octet-stream";
			httpServletResponse.setContentType(mimeType);
			httpServletResponse.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
			httpServletResponse.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, httpServletResponse.getOutputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				httpServletResponse.getOutputStream().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (file != null) {
				file.delete();
			}
		}
	}

	@RequestMapping("/initMonthAndYear")
	@ResponseBody
	public JSONArray getAllMonthYear() {

		return reportService.getAllMonthYearForAvalableData();
		
		
	}
	
}
