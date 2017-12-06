package org.sdrc.scpstamilnadu.repository;

import java.sql.Timestamp;
import java.util.List;

import org.sdrc.scpstamilnadu.domain.Program_XForm_Mapping;
import org.sdrc.scpstamilnadu.domain.User_Program_XForm_Mapping;
import org.springframework.transaction.annotation.Transactional;

/**
 * This repository interface will help us to get the data from Program_XForm_Mapping table.
 * 
 * @author Sarita
 * @since version 1.0.0.0
 */ 

public interface Program_XForm_MappingRepository{
	
	
	Program_XForm_Mapping findByProgramXFormMappingId(int programXFromMappingId);
	
	List<Program_XForm_Mapping> findAllByIsLiveTrue();

	List<Program_XForm_Mapping> findByProgramProgramIdAndIsLiveTrue(Integer programId);


	
	Program_XForm_Mapping findByXFormFormIdAndIsLiveTrue(Integer id);

	void updateIsLiveByXFormId(Integer xFormId, String updatedBy,
			Timestamp updatedDat);

	void updateIsLiveByProgramId(Integer programId, String updatedBy,
			Timestamp updatedDat);

	List<Program_XForm_Mapping> findByProgramIdAndIsLiveTrue(int programId);
	
	Program_XForm_Mapping findByXFormFormId(int formId);


}
