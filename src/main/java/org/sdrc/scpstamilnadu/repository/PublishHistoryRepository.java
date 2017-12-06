package org.sdrc.scpstamilnadu.repository;

import org.sdrc.scpstamilnadu.domain.PublishHistory;

public interface PublishHistoryRepository {
	
	PublishHistory findByDataBeingPublishedForMonthAndDataBeingPublishedForYear(Integer month,Integer year);

	PublishHistory save(PublishHistory history);

}
