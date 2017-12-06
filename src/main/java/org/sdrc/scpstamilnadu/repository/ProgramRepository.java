package org.sdrc.scpstamilnadu.repository;

import java.sql.Timestamp;
import java.util.List;

import org.sdrc.scpstamilnadu.domain.Program;
import org.springframework.transaction.annotation.Transactional;

/**
 * This repository interface will help us to get the data from Program table.
 * 
 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
 * @author Sarita
 * @since version 1.0.0.0
 */

public interface ProgramRepository {

	/**
	 * This method will take the primary key as argument and give one row of
	 * record.
	 * 
	 * @param programId
	 *            The primary key as argument
	 * @return org.sdrc.collect.domain.Program, object of this domain class
	 * @since version 1.0.0.0
	 */
	Program findByProgramId(int programId);

	@Transactional
	void save(Program program);

	List<Program> findAllByIsLiveTrue();
	
    Program findByProgramNameAndIsLiveTrue(String programName);

	
//	 *@added by Sarita
//	void updateLiveByProgramId(Integer programId);

//	 *@added by Sarita
	void updateLiveByProgramId(Integer programId, String updatedBy,
			Timestamp updatedDat);

//	 *@added by Sarita
	void updateProgramNameByProgramId(Integer programId, String programName,
			String updatedBy, Timestamp updatedDat);

}
