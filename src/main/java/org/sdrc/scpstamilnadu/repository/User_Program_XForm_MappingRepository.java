package org.sdrc.scpstamilnadu.repository;

import java.sql.Timestamp;
import java.util.List;

import org.sdrc.scpstamilnadu.domain.User_Program_XForm_Mapping;
import org.springframework.transaction.annotation.Transactional;

/**
 * This repository will help us to get data from User Program-XForm mapping table.
 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
 * @author Sarita
 * @since version 1.0.0.0
 *
 */
public interface User_Program_XForm_MappingRepository{
	
	
	/**
	 * This method will take the user id and give all the forms and programs assigned to the user.  
	 * @param username username of the Collect Android app user
	 * @param password password of the Collect Android app user
	 * @return List<org.sdrc.collect.domain.User_Program_XForm_Mapping>
	 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
	 * @since version 1.0.0.0
	 */
	
	List<User_Program_XForm_Mapping>  findByUser(String username);
	
	List<User_Program_XForm_Mapping> findByIsLiveTrue();
	
	@Transactional
	List<User_Program_XForm_Mapping> save(Iterable<User_Program_XForm_Mapping> entities);

	void updateIsLiveByUserId(Integer userId);

	List<User_Program_XForm_Mapping> findByXFormIdAndIsLiveTrue(Integer formId);
	

	List<User_Program_XForm_Mapping> findByProgramIdAndIsLiveTrue(Integer programId);

	void updateIsLiveByUserProgramXFomrMappingId(Integer upxmId,
			String updatedBy, Timestamp updatedDate);
	
	List<Integer> findByCollectUserId(Integer userId);
	/**
	 * Find single record by the id
	 * @param userProgramXFomrMappingId the primary key
	 * @return single record of User_Program_XForm_Mapping
	 */
	User_Program_XForm_Mapping findByUserProgramXFomrMappingId(int userProgramXFomrMappingId);

//	void updateIsLiveByXFormId(Integer xFormId, String updatedBy,
//			Timestamp updatedDate);

//	void updateIsLiveByProgramId(Integer programId, String updatedBy,
//			Timestamp updatedDate);
	/**
	 *  It will return the checklist Names assinged to user
	 * @param userId
	 * @return List<String>
	 *
	 */
	List<String> findByIsLiveTrueAndUserId(Integer userId);

	void deleteByCollectUserUserId(Integer userId);

	void save(User_Program_XForm_Mapping mapping);
}
