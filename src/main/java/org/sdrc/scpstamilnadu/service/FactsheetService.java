package org.sdrc.scpstamilnadu.service;

import org.sdrc.scpstamilnadu.domain.Agency;
import org.sdrc.scpstamilnadu.util.FactsheetObject;

public interface FactsheetService {

	Object getPrefetchData(int agencyId);

	Object getFactSheetData(FactsheetObject factsheetObject,Agency agency);
}
