package org.sdrc.scpstamilnadu.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Month;
import java.time.Year;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.sdrc.scpstamilnadu.domain.DataSubmission;
import org.sdrc.scpstamilnadu.repository.DataSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private DataSubmissionRepository dataSubmissionRepository;

	@Override
	public File generateEditReport(Integer month, Integer year) throws IOException, FileNotFoundException {

		// Fetching Data from server
		List<DataSubmission> submissions = dataSubmissionRepository.findAllByDataEnteredForMonthAndDataEnteredForYearAndNoOfTimeDataEditedGreaterThanZero(month, year);

		File file = File.createTempFile("report", ".xlsx");
		FileOutputStream fos = null;
		XSSFWorkbook workbook = null;

		try {
			workbook = new XSSFWorkbook();

			XSSFCellStyle headStyle = workbook.createCellStyle();
			headStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(165, 165, 165)));
			headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			headStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			headStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			headStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			headStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);

			Font boldFont = workbook.createFont();
			/* set the weight of the font */
			boldFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			/* attach the font to the style created earlier */

			headStyle.setFont(boldFont);

			XSSFCellStyle topHeadStyle = workbook.createCellStyle();
			topHeadStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(165, 165, 165)));
			topHeadStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			topHeadStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			topHeadStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			topHeadStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			topHeadStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			topHeadStyle.setFont(boldFont);
			topHeadStyle.setAlignment(HorizontalAlignment.CENTER);

			XSSFCellStyle bindingCellStyle = workbook.createCellStyle();
			bindingCellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(165, 165, 165)));
			bindingCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			bindingCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			bindingCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			bindingCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			bindingCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);

			XSSFCellStyle planeCellStyle = workbook.createCellStyle();
			planeCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			planeCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			planeCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			planeCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			XSSFCellStyle tempstyles = null;

			XSSFSheet sheet = workbook.createSheet("Report");

			XSSFRow row = sheet.createRow(0);

			XSSFCell cell0head = row.createCell(0);
			cell0head.setCellValue((String) "AUDIT REPORT : " + Month.of(month).name().concat(" ").concat(year + ""));
			cell0head.setCellStyle(topHeadStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, // first row (0-based)
					0, // last row (0-based)
					0, // first column (0-based)
					4 // last column (0-based)
			));
			sheet.autoSizeColumn(cell0head.getColumnIndex());

			XSSFRow row2 = sheet.createRow(2);

			XSSFCell cell20val = row2.createCell(0);
			cell20val.setCellValue((String) "SL NO");
			cell20val.setCellStyle(headStyle);
			sheet.autoSizeColumn(cell20val.getColumnIndex());

			XSSFCell cell21val = row2.createCell(1);
			cell21val.setCellValue((String) "AREA");
			cell21val.setCellStyle(headStyle);
			sheet.autoSizeColumn(cell21val.getColumnIndex());

			XSSFCell cell22val = row2.createCell(2);
			cell22val.setCellValue((String) "FACILITY TYPE");
			cell22val.setCellStyle(headStyle);
			sheet.autoSizeColumn(cell22val.getColumnIndex());

			XSSFCell cell23val = row2.createCell(3);
			cell23val.setCellValue((String) "TIMEPERIOD");
			cell23val.setCellStyle(headStyle);
			sheet.autoSizeColumn(cell23val.getColumnIndex());

			XSSFCell cell24val = row2.createCell(4);
			cell24val.setCellValue((String) "NO.OF TIMES EDITED");
			cell24val.setCellStyle(headStyle);
			sheet.autoSizeColumn(cell24val.getColumnIndex());

			int startDataWritingRow = 3, index = 1;
			for (DataSubmission dataSubmission : submissions) {
				System.out.println("dataSubmissionId::" + dataSubmission.getDataSubmissionId());
				tempstyles = (startDataWritingRow % 2 == 0 ? planeCellStyle : bindingCellStyle);

				XSSFRow rowData = sheet.createRow(startDataWritingRow++);

				cell20val = rowData.createCell(0);
				cell20val.setCellValue((Integer) (index++));
				cell20val.setCellStyle(tempstyles);
				sheet.autoSizeColumn(cell20val.getColumnIndex());

				cell21val = rowData.createCell(1);
				cell21val.setCellValue((String) dataSubmission.getFacility().getDistrict().getAreaName());
				cell21val.setCellStyle(tempstyles);
				sheet.autoSizeColumn(cell21val.getColumnIndex());

				cell22val = rowData.createCell(2);
				cell22val.setCellValue((String) dataSubmission.getFacility().getFacilityUserMapping().getUser().getRole().getRoleName());
				cell22val.setCellStyle(tempstyles);
				sheet.autoSizeColumn(cell22val.getColumnIndex());

				cell23val = rowData.createCell(3);
				cell23val.setCellValue((String) Month.of(month).name().concat(" ").concat(year + ""));
				cell23val.setCellStyle(tempstyles);
				sheet.autoSizeColumn(cell23val.getColumnIndex());

				cell24val = rowData.createCell(4);
				cell24val.setCellValue((Integer) dataSubmission.getNoOfTimeDataEdited());
				cell24val.setCellStyle(tempstyles);
				sheet.autoSizeColumn(cell24val.getColumnIndex());

			}

			fos = new FileOutputStream(file);
			workbook.write(fos);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
					if (file.exists())
						file.delete();
					throw new RuntimeException("IO error occured.File cannot be created.", e);
				}
			}
		}

		return file;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getAllMonthYearForAvalableData() {

		List<String> lists = dataSubmissionRepository.findByDistinctDataEnteredForMonthAndDataEnteredForYear();

		JSONArray datas = new JSONArray();

		for (String dataSubmission : lists) {

			boolean isInList = false;
			String[] arr = dataSubmission.split(",");

			for (Object object : datas) {
				JSONObject o = (JSONObject) object;
				if (Integer.valueOf(o.get("month").toString()) == Integer.parseInt(arr[0].toString())) {
					isInList = true;
				}
			}
			if (!isInList) {
				JSONObject data = new JSONObject();
				data.put("month", Integer.parseInt(arr[0].toString()));
				data.put("monthString", Month.of(Integer.valueOf(arr[0].toString())).toString());

				JSONArray yearArray = new JSONArray();

				for (String findingYears : lists) {
					String[] arrYear = findingYears.split(",");
					if (Integer.parseInt(arr[0].toString()) == Integer.parseInt(arrYear[0].toString())) {
						JSONObject yearObject = new JSONObject();
						yearObject.put("year", Integer.parseInt(arrYear[1].toString()));
						yearObject.put("yearString", Integer.valueOf(arrYear[1].toString()).toString());
						yearArray.add(yearObject);
					}
				}
				data.put("years", yearArray);
				datas.add(data);
			}
		}

		return datas;

	}

}
