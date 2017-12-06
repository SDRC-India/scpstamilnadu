package org.sdrc.scpstamilnadu.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONArray;

public interface ReportService {

	public File generateEditReport(Integer month, Integer year) throws FileNotFoundException, IOException;

	public JSONArray getAllMonthYearForAvalableData();
}
