/**
 * 
 */
package org.sdrc.scpstamilnadu.repository;

import java.sql.Timestamp;
import java.util.List;



import org.sdrc.scpstamilnadu.domain.UserLoginMeta;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */
public interface UserLoginMetaRepository {

	@Transactional
	UserLoginMeta save(UserLoginMeta userLoginMeta);

	void updateStatusForAll(Timestamp loggedOutDateTime);

	void updateStatus(Timestamp loggedOutDateTime, long userLoginMetaId);

	List<UserLoginMeta> findByMstUserUserIdAndIsLoggedInTrue(Integer userId);

}
