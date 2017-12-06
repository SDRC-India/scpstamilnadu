package org.sdrc.scpstamilnadu.repository.springdata;

import org.sdrc.scpstamilnadu.domain.PublishHistory;
import org.sdrc.scpstamilnadu.repository.PublishHistoryRepository;
import org.springframework.data.repository.RepositoryDefinition;


@RepositoryDefinition(domainClass=PublishHistory.class,idClass=Integer.class)
public interface SpringDataPublishHistoryRepository extends PublishHistoryRepository{

}
