/**
 * 
 */
package org.sdrc.scpstamilnadu.repository.springdata;

import org.sdrc.scpstamilnadu.domain.UserRoleFeaturePermissionMapping;
import org.sdrc.scpstamilnadu.repository.UserRoleFeaturePermissionMappingRepository;
import org.springframework.data.repository.RepositoryDefinition;


/**
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */

@RepositoryDefinition(domainClass=UserRoleFeaturePermissionMapping.class,idClass=Integer.class)
public interface SpringDataUserRoleFeaturePermissionMappingRepository extends
		UserRoleFeaturePermissionMappingRepository {

}
