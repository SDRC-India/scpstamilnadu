package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * This repository interface will help us to get the data from CollectUser
 * table.
 * 
 * @author Sarita
 * @author Subrata
 * @since version 1.0.0.0
 */

public interface CollectUserRepository {

	@Transactional
	User save(User addUser);


	User findByUserNameAndPassword(String username, String password);

	User findByUserName(String username);


	User findByUserId(Integer userId);

}
