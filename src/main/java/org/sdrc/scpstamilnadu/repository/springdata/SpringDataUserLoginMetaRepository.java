package org.sdrc.scpstamilnadu.repository.springdata;

import java.sql.Timestamp;

import org.sdrc.scpstamilnadu.domain.UserLoginMeta;
import org.sdrc.scpstamilnadu.repository.UserLoginMetaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;



@RepositoryDefinition(domainClass=UserLoginMeta.class,idClass=Integer.class)
public interface SpringDataUserLoginMetaRepository extends UserLoginMetaRepository {

	@Modifying 
	@Transactional
	@Query("UPDATE UserLoginMeta logInMeta SET logInMeta.loggedOutDateTime = :loggedOutDateTime , "
			+ "logInMeta.isLoggedIn =FALSE WHERE logInMeta.userLogInMetaId = :userLogInMetaId ")
	void updateStatus(@Param("loggedOutDateTime")Timestamp loggedOutDateTime, @Param("userLogInMetaId")long userLogInMetaId);
	
	@Modifying 
	@Transactional
	@Query("UPDATE"
			+ " UserLoginMeta logInMeta SET "
			+ "logInMeta.loggedOutDateTime = :loggedOutDateTime , "
			+ "logInMeta.isLoggedIn =FALSE ")
	void updateStatusForAll(@Param("loggedOutDateTime")Timestamp loggedOutDateTime);
	
}
