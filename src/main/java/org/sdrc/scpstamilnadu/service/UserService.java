package org.sdrc.scpstamilnadu.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.sdrc.scpstamilnadu.domain.User;
import org.sdrc.scpstamilnadu.domain.UserLoginMeta;
import org.sdrc.scpstamilnadu.model.CollectUserModel;
import org.sdrc.scpstamilnadu.model.UserModel;

/**
 * 
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */
public interface UserService {

	UserModel getUserData(String username, String password);

	long saveUserLoginMeta(String ipAddress, Integer userId, String userAgent, String sessionID);

	void updateLoggedOutStatus(long userLoginMeta, Timestamp loggedOutDateTime);

	List<UserLoginMeta> findActiveUserLoginMeta(Integer userId);
	
	public boolean forgotPassword(String username) throws MessagingException;

	Map<String,String> validateOtp(String username, String otp);

	Map<String,String> resetPassword(String otp, String password, String username);

	User findByUserName(String name);
	
	
	CollectUserModel getCollectUserModel(String username);
}
