package org.sdrc.scpstamilnadu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdrc.scpstamilnadu.domain.User;
import org.sdrc.scpstamilnadu.domain.UserRoleFeaturePermissionMapping;
import org.sdrc.scpstamilnadu.model.CollectUserModel;
import org.sdrc.scpstamilnadu.model.UserRoleFeaturePermissionMappingModel;
import org.sdrc.scpstamilnadu.service.SubmissionService;
import org.sdrc.scpstamilnadu.service.UserService;
import org.sdrc.scpstamilnadu.util.DomainToModelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.pdf.codec.Base64;


/**
 * This class will be responsible for submission code of SDRC Collect application.
 * When the user submit forms from SDRC Collect android app, the control will come here.
 * 
 * This class will process the request and send response to SDRC Collect. 
 *
 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
 * @since version 1.0.0.0
 *
 */


@Controller
public class SubmissionController implements AuthenticationProvider{
	
	private static final Logger logger = LoggerFactory.getLogger(SubmissionController.class);
	
	
	/**
	 * The following variable will be used to use the value from message.properties file(not directly)
	 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
	 * @since version 1.0.0.0
	 */
	
//	@Autowired
//	private ResourceBundleMessageSource messages;
	
	/**
	 * The following variable will be used to do the work of submission
	 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
	 * @since version 1.0.0.0
	 */
	@Autowired
	private SubmissionService submissionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageDigestPasswordEncoder passwordEncoder;
	
//	private CollectUserModel collectUserModel;
	
	
	/**
	 * This following method will be responsible for accepting the get request first from SDRC Collect before submitting the real forms.
	 * We are sending error 204 because the SDRC Collect code needed it for some preprocessing
	 * @param response
	 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
	 * @since version 1.0.0.0
	 */

	@RequestMapping("submission")
	public void submitionGetType(HttpServletResponse response){
		try {
			response.sendError(204);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
	}
	
	/**
	 * The following method will be responsible for submitting the forms to server and sending response to SDRC Collect app 
	 * @param request, response
	 * @throws IOException 
	 * @throws ServletException
	 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
	 * @since version 1.0.0.0 
	 */
	@RequestMapping(value = "submission", method = RequestMethod.POST)
	public void submitionPostType(HttpServletRequest request, HttpServletResponse response, @RequestParam("deviceID") 
		String deviceID,@RequestParam("userString") String userString) throws IOException, ServletException{
		try {
		byte[] decodedUserString = Base64.decode(userString);
		
		String decodedUserStringString = new String (decodedUserString);
		
		String splitArray[] = decodedUserStringString.split(":");
		
		
		if(splitArray.length == 2){
			String username = splitArray[0];
			String password = splitArray[1];
			boolean authenticationFailed = false;
			try{
				String encodedPassword = passwordEncoder.encodePassword(username, password);

				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, encodedPassword);	
				this.authenticate(token);				
			}catch(Exception e){
				logger.error("Authentication : {}",e);
				authenticationFailed = true;				
			}
			CollectUserModel collectUserModel = userService.getCollectUserModel(username);

			if(!authenticationFailed){
					logger.info("Submission request from device Id : " + deviceID);
					int result = submissionService.uploadForm(request, deviceID, collectUserModel);
					if(result == 0){
						response.sendError(201);
					}else{
						response.sendError(result);
					}
			}else{
				logger.error("unauthorized server hit for submission from imei: " + deviceID + " User String : " + userString);
				response.sendError(org.apache.http.HttpStatus.SC_UNAUTHORIZED);
			}
			
		}else{
			logger.error("Invalid string length for submission from imei: " + deviceID + " User String : " + userString);
			response.sendError(org.apache.http.HttpStatus.SC_UNAUTHORIZED);
		}		
		}catch(Exception e) {
			logger.error("Exception : {}",e);
		}
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		User collectUserModel = userService.findByUserName(authentication.getName());
		//String encodedPassword = passwordEncoder.encodePassword(authentication.getName(),(String)authentication.getCredentials());
		if (collectUserModel == null ||!collectUserModel.getPassword().equals((String)authentication.getCredentials())){
			throw new BadCredentialsException("Invalid User!");			
		}
		return new UsernamePasswordAuthenticationToken(authentication.getName(), (String)authentication.getCredentials(), null);
	}


	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}
}
