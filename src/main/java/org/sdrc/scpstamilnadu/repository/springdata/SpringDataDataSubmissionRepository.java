package org.sdrc.scpstamilnadu.repository.springdata;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.DataSubmission;
import org.sdrc.scpstamilnadu.repository.DataSubmissionRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;


@RepositoryDefinition(domainClass=DataSubmission.class,idClass=Integer.class)
public interface SpringDataDataSubmissionRepository extends DataSubmissionRepository{

	@Query("SELECT S FROM DataSubmission S WHERE S.dataEnteredForMonth = :month AND S.dataEnteredForYear = :year AND S.noOfTimeDataEdited > 0")
	public List<DataSubmission> findAllByDataEnteredForMonthAndDataEnteredForYearAndNoOfTimeDataEditedGreaterThanZero(@Param("month")Integer month, @Param("year") Integer year);

	@Query(value="select distinct(concat(data_entered_for_month,',',data_entered_for_year)) from data_submission",nativeQuery=true)
	public List<String> findByDistinctDataEnteredForMonthAndDataEnteredForYear();

}
