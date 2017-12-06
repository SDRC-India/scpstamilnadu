/**
 * 
 */
package org.sdrc.scpstamilnadu.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.sdrc.scpstamilnadu.domain.Area;
import org.sdrc.scpstamilnadu.domain.User;
import org.sdrc.scpstamilnadu.domain.UserLoginMeta;
import org.sdrc.scpstamilnadu.domain.UserRoleFeaturePermissionMapping;
import org.sdrc.scpstamilnadu.exceptions.UserNotFoundException;
import org.sdrc.scpstamilnadu.model.CollectUserModel;
import org.sdrc.scpstamilnadu.model.UserModel;
import org.sdrc.scpstamilnadu.repository.AreaRepository;
import org.sdrc.scpstamilnadu.repository.UserLoginMetaRepository;
import org.sdrc.scpstamilnadu.repository.UserRepository;
import org.sdrc.scpstamilnadu.util.DomainToModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */

@Service
public class UserServiceImpl implements UserService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sdrc.udise.service.UserService#getUserData()
	 */
	@Autowired
	UserRepository userRepository;

	@Autowired
	AreaRepository areaRepository;

	@Autowired
	UserLoginMetaRepository userLoginMetaRepository;

	@Transactional
	public UserModel getUserData(String username, String password) {
		UserModel userModel = null;

		Map<Integer, Area> areaIdDomainMap = new HashMap<Integer, Area>();
		List<Area> areas = areaRepository.findAll();

		for (Area area : areas) {
			areaIdDomainMap.put(area.getAreaId(), area);
		}

		User user = userRepository.findByUserNameAndPassword(username, password);
		if (user != null) {
			userModel = new UserModel();
			userModel.setUserId(user.getUserId());
			userModel.setUsername(user.getUserName());
			userModel.setPassword(user.getPassword());
			userModel.setAreaId(user.getUserAreaMappings().get(0).getArea().getAreaId());
			userModel.setRoleId(user.getUserAreaMappings().get(0).getUserRoleFeaturePermissionMappings().get(0).getRoleFeaturePermissionScheme().getRole().getRoleId());
			userModel.setUserAreaModels(DomainToModelConverter.toUserAreaMappingModel(user.getUserAreaMappings()));
			userModel.setAgencyId(user.getAgency().getAgencyId());

		}
		return userModel;
	}

	@Transactional
	public long saveUserLoginMeta(String ipAddress, Integer userId, String userAgent, String sessionID) {
		User mstUser = userRepository.findByUserId(userId);
		UserLoginMeta userLoginMeta = new UserLoginMeta();
		userLoginMeta.setUserIpAddress(ipAddress);
		userLoginMeta.setUser(mstUser);
		userLoginMeta.setLoggedInDateTime(new Timestamp(new Date().getTime()));
		userLoginMeta.setUserAgent(userAgent);
		userLoginMeta.setLoggedIn(true);
		userLoginMeta.setSeesionID(sessionID.split("=")[1].trim());
		return userLoginMetaRepository.save(userLoginMeta).getUserLogInMetaId();
	}

	// update login meta while signing out

	@Transactional
	public void updateLoggedOutStatus(long userLoginMetaId, Timestamp loggedOutDateTime) {

		// while server start up
		if (userLoginMetaId == -1) {
			userLoginMetaRepository.updateStatusForAll(loggedOutDateTime);
		} else
			userLoginMetaRepository.updateStatus(loggedOutDateTime, userLoginMetaId);
	}

	public List<UserLoginMeta> findActiveUserLoginMeta(Integer userId) {
		return userLoginMetaRepository.findByMstUserUserIdAndIsLoggedInTrue(userId);
	}

	@Transactional
	public boolean forgotPassword(String username) throws MessagingException {
		User user = userRepository.findByUserName(username);
		if (user == null) {
			throw new UserNotFoundException("No such user exists !");
		}

		String numbers = "0123456789";

		// Using random method
		Random rndm_method = new Random();

		char[] otp = new char[6];

		for (int i = 0; i < 6; i++) {
			// Use of charAt() method : to get character value
			// Use of nextInt() as it is scanning the value as int
			otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
		}

		user.setOtp(new String(otp));
		user.setOtpGeneratedDateTime(new Date());
		user.setInvalidAttempts((short) 0);
		new Thread() {

			@Override
			public void run() {
				// Using numeric values

				String userName = "scpsalert@gmail.com";
				String password = "@@scpsalert123";
				Properties props = new Properties();

				props.setProperty("mail.smtp.host", "smtp.gmail.com");
				props.setProperty("mail.smtp.port", "465");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

				javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(userName, password);
					}
				});

				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress(userName));
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));

					message.setSubject("Forgot Password OTP");
					String messageBody = "<html><body><p style='color:#2d3e50; font-size:14px; font-weight:normal; margin:10px 0'>" + "Please <span>find below</span> credentials for accessing the SCPS application.<br>" + "Username: " + user.getUserName() + "<br> OTP: " + new String(otp) + "</p>" + "<br><p> OTP: " + new String(otp) + "</p>"
							+ "<p style='color:#2d3e50; font-size:14px; font-weight:normal; margin:10px 0'>Please  <span >note:</span> We would strongly recommend you to create a new password. </p>" + "</body></html>";
					message.setContent(messageBody, "text/html");
					Transport.send(message);

				} catch (MessagingException ex) {
					ex.printStackTrace();
				}
			}
		}.start();

		return true;

	}

	@Override
	@Transactional
	public Map<String, String> validateOtp(String username, String otp) {
		Map<String, String> map = new HashMap<String, String>();
		User user = userRepository.findByUserName(username);
		if ((user.getOtpGeneratedDateTime().getTime() + 10 * 60000) <= new Date().getTime()) {
			map.put("status", "400");
			map.put("message", "Password reset time expired !");
		}
		if (user.getInvalidAttempts() == 10) {
			map.put("status", "400");
			map.put("message", "Not allowed! No of attempts exceeded limit.i.e " + 10);
			return map;
		}

		if (user.getOtp().equals(otp)) {
			map.put("status", "200");
			map.put("message", "Otp Validated !");
		} else {
			user.setInvalidAttempts((short) (user.getInvalidAttempts() + 1));
			map.put("status", "400");
			map.put("message", (10 - user.getInvalidAttempts()) != 0 ? "Invalid OTP! You have " + (10 - user.getInvalidAttempts()) + " available attempts." : "Invalid OTP ! No of attempts exceeded limit.i.e " + 10);
		}

		return map;
	}

	@Override
	@Transactional
	public Map<String, String> resetPassword(String otp, String password, String username) {

		Map<String, String> map = new HashMap<String, String>();
		User user = userRepository.findByUserName(username);
		if (user.getOtp().equals(otp) && username.equals(user.getUserName()) && user.getInvalidAttempts() <= 10) {
			user.setPassword(user.generatedEncodedPassword(user.getUserName(), password));
		} else if (!user.getOtp().equals(otp)) {
			map.put("status", "400");
			map.put("message", "Invalid OTP! Password can't be generated.");
		} else if (user.getInvalidAttempts() >= 10) {
			map.put("status", "400");
			map.put("message", "Not allowed! No of attempts for OTP validation has exceeded !");
		} else if (!username.equals(user.getUserName())) {
			map.put("status", "400");
			map.put("message", "Invalid UserName !");
		}

		return map;
	}

	@Override
	public User findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	@Override
	@Transactional
	public CollectUserModel getCollectUserModel(String username) {
		User user = userRepository.findByUserName(username); // optimize later

		CollectUserModel collectUserModel = new CollectUserModel();

		collectUserModel.setEmailId(user.getEmail());
		collectUserModel.setLive(user.getIsActive());
		collectUserModel.setName(user.getName());
		collectUserModel.setUsername(user.getUserName());
		collectUserModel.setUserId(user.getUserId());
		List<UserRoleFeaturePermissionMapping> permissions = new ArrayList<>();

		user.getUserAreaMappings().forEach(userAreaMapping -> {

			userAreaMapping.getUserRoleFeaturePermissionMappings().forEach(userAreaMap -> {

				userAreaMap.getRoleFeaturePermissionScheme();
				userAreaMap.getUserRoleFeaturePermissionId();
				userAreaMap.getUserAreaMapping().getArea();
				userAreaMap.getUserAreaMapping().getUser();
				userAreaMap.getUserAreaMapping().getUserRoleFeaturePermissionMappings().forEach(e -> {
					e.getRoleFeaturePermissionScheme();
				});

			});
			;

		});

		user.getUserAreaMappings().forEach(userAreaMapping -> {

			permissions.addAll(userAreaMapping.getUserRoleFeaturePermissionMappings());

		});
		collectUserModel.setUserRoleFeaturePermissionMappings(DomainToModelConverter.toUserRoleFeaturePermissionMappingModels(permissions));

		return collectUserModel;
	}

}
