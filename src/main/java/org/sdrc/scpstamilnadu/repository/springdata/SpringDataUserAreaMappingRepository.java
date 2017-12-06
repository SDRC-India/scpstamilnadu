/**
 * 
 */
package org.sdrc.scpstamilnadu.repository.springdata;

import org.sdrc.scpstamilnadu.domain.UserAreaMapping;
import org.sdrc.scpstamilnadu.repository.UserAreaMappingRepository;
import org.springframework.data.repository.RepositoryDefinition;



/**
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */

@RepositoryDefinition(domainClass=UserAreaMapping.class,idClass=Integer.class)
public interface SpringDataUserAreaMappingRepository extends UserAreaMappingRepository{

}
