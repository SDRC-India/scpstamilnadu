package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.User;

public interface UserRepository {

	public User save(User user);

	public User findByUserId(int userId);

	public User findByUserNameAndPassword(String username, String password);

	public List<User> findAll();

	public User findByUserName(String username);

	public List<User> findAllByOrderByUserIdAsc();


}
