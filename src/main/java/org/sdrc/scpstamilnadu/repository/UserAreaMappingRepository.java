/**
 * 
 */
package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.UserAreaMapping;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */
public interface UserAreaMappingRepository {

	@Transactional
	<S extends UserAreaMapping> List<S> save(Iterable<S> userAreaMappings);

	List<UserAreaMapping> findAll();
	
	UserAreaMapping save(UserAreaMapping userAreaMapping);
	
}
