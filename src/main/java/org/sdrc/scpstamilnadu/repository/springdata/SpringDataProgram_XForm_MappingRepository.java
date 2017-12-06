package org.sdrc.scpstamilnadu.repository.springdata;
import java.sql.Timestamp;
import java.util.List;

import org.sdrc.scpstamilnadu.domain.Program_XForm_Mapping;
import org.sdrc.scpstamilnadu.repository.Program_XForm_MappingRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
 * @author Sarita
 * @since version 1.0.0.0
 *
 */
public interface SpringDataProgram_XForm_MappingRepository extends Program_XForm_MappingRepository, Repository<Program_XForm_Mapping, Integer>{
	
	
	Program_XForm_Mapping findByProgramXFormMappingId(int programXFromMappingId);
	
	@Override
	@Transactional
	@Modifying
	@Query("UPDATE Program_XForm_Mapping pxm SET pxm.isLive = FALSE, pxm.updatedBy = :updatedBy, pxm.updatedDate = :updatedDate WHERE pxm.program.programId = :programId")
	void updateIsLiveByProgramId(@Param("programId") Integer programId, @Param("updatedBy") String updatedBy, @Param("updatedDate") Timestamp updatedDat);
	
	@Override
	@Transactional
	@Modifying
	@Query("UPDATE Program_XForm_Mapping pxm SET pxm.isLive = FALSE, pxm.updatedBy = :updatedBy, pxm.updatedDate = :updatedDate WHERE pxm.xForm.formId = :xFormId")
	void updateIsLiveByXFormId(@Param("xFormId") Integer xFormId, @Param("updatedBy") String updatedBy, @Param("updatedDate") Timestamp updatedDat);
	
	@Override
	@Query("Select pxm from Program_XForm_Mapping pxm where isLive = true and pxm.program.isLive = true and pxm.xForm.isLive = true")
	List<Program_XForm_Mapping> findAllByIsLiveTrue();
	
	@Override
	@Query("SELECT pxm FROM Program_XForm_Mapping pxm WHERE pxm.program.programId = :programId and pxm.xForm.isLive = true")
	List<Program_XForm_Mapping> findByProgramIdAndIsLiveTrue(@Param("programId") int programId);
}
