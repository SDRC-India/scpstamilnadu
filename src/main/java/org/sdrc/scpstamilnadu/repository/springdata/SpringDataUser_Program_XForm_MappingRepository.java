package org.sdrc.scpstamilnadu.repository.springdata;

import java.sql.Timestamp;
import java.util.List;

import org.sdrc.scpstamilnadu.domain.User_Program_XForm_Mapping;
import org.sdrc.scpstamilnadu.repository.User_Program_XForm_MappingRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
 * @author Sarita
 * @author Subrata
 * @since version 1.0.0.0
 *
 */
public interface SpringDataUser_Program_XForm_MappingRepository extends User_Program_XForm_MappingRepository, Repository<User_Program_XForm_Mapping, Integer>{
	
	/**
	 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
	 */
	@Override
	@Query("SELECT upxm FROM User_Program_XForm_Mapping upxm WHERE upxm.collectUser.userName = :username"
			+ " AND upxm.isLive = True AND upxm.collectUser.isActive = True AND upxm.program_XForm_Mapping.isLive = True"
			+ " AND upxm.program_XForm_Mapping.program.isLive = True AND upxm.program_XForm_Mapping.xForm.isLive = True")
	List<User_Program_XForm_Mapping> findByUser(@Param("username") String username);
	
//	@Override
//	@Transactional
//	@Modifying
//	@Query("UPDATE User_Program_XForm_Mapping upxm SET upxm.isLive = FALSE, upxm.updatedBy = :updatedBy, upxm.updatedDate = :updatedDate "
//			+ "WHERE upxm.program_XForm_Mapping.xForm.id= :xFormId")
//	void updateIsLiveByXFormId(@Param("xFormId") Integer xFormId, @Param("updatedBy") String updatedBy, @Param("updatedDate") Timestamp updatedDate);
	
	@Override
	@Transactional
	@Modifying
	@Query("UPDATE User_Program_XForm_Mapping upxm SET upxm.isLive = FALSE WHERE upxm.collectUser.userId= :userId")
	void updateIsLiveByUserId(@Param("userId") Integer userId);
	
	@Override
	@Query("SELECT upxm FROM User_Program_XForm_Mapping upxm WHERE upxm.program_XForm_Mapping.xForm.formId = :formId AND upxm.isLive = TRUE")
	List<User_Program_XForm_Mapping> findByXFormIdAndIsLiveTrue(@Param("formId") Integer formId);
	
	@Override
	@Query("SELECT upxm FROM User_Program_XForm_Mapping upxm WHERE upxm.program_XForm_Mapping.program.programId = :programId AND upxm.isLive = TRUE")
	List<User_Program_XForm_Mapping> findByProgramIdAndIsLiveTrue(@Param("programId") Integer programId);
	
//	@Override
//	@Transactional
//	@Modifying
//	@Query("UPDATE User_Program_XForm_Mapping upxm SET upxm.isLive = FALSE, upxm.updatedBy = :updatedBy, upxm.updatedDate = :updatedDate "
//			+ "WHERE upxm.program_XForm_Mapping.program.programId= :programId")
//	void updateIsLiveByProgramId(@Param("programId") Integer programId, @Param("updatedBy") String updatedBy, @Param("updatedDate") Timestamp updatedDate);
	
	@Override
	@Transactional
	@Modifying
	@Query("UPDATE User_Program_XForm_Mapping upxm SET upxm.isLive = FALSE, upxm.updatedBy = :updatedBy, upxm.updatedDate = :updatedDate "
			+ "WHERE upxm.userProgramXFomrMappingId= :upxmId")
	void updateIsLiveByUserProgramXFomrMappingId(@Param("upxmId") Integer upxmId, @Param("updatedBy") String updatedBy, @Param("updatedDate") Timestamp updatedDate);
	
	@Override
	@Query("SELECT upxm.program_XForm_Mapping.programXFormMappingId FROM User_Program_XForm_Mapping upxm WHERE upxm.collectUser.userId = :userId "
			+ "ORDER BY upxm.program_XForm_Mapping.programXFormMappingId")
	List<Integer> findByCollectUserId(@Param("userId") Integer username);
	
	@Override
	@Query("SELECT xform.xFormId FROM User_Program_XForm_Mapping upxm "
			+ " JOIN upxm.program_XForm_Mapping pxm  "
			+ " JOIN pxm.xForm xform "
			+ "WHERE upxm.collectUser.userId= :userId "
			+ "AND upxm.isLive=true "
			+ "AND pxm.programXFormMappingId=upxm.program_XForm_Mapping.programXFormMappingId "
			+ "AND pxm.isLive=true "
			+ "AND xform.formId=pxm.xForm.formId "
			+ "AND xform.isLive=true")
	List<String> findByIsLiveTrueAndUserId(@Param("userId") Integer userId);
	
	@Override
	@Modifying
	@Transactional
	@Query("DELETE FROM User_Program_XForm_Mapping upxm WHERE upxm.collectUser.userId = :userId")
	void deleteByCollectUserUserId(@Param("userId") Integer userId);
}
