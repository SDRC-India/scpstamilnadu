package org.sdrc.scpstamilnadu.repository.springdata;

import org.sdrc.scpstamilnadu.domain.User;
import org.sdrc.scpstamilnadu.repository.UserRepository;
import org.springframework.data.repository.RepositoryDefinition;


@RepositoryDefinition(domainClass=User.class,idClass=Integer.class)
public interface SpringDataUserRepository extends UserRepository{

}
