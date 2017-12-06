package org.sdrc.scpstamilnadu.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.sdrc.scpstamilnadu.domain.User;
import org.sdrc.scpstamilnadu.model.FormsToUpdate;
import org.sdrc.scpstamilnadu.model.ModelToCollectApplication;
import org.sdrc.scpstamilnadu.model.ProgramXFormsModel;
import org.sdrc.scpstamilnadu.service.AuthenticateService;
import org.sdrc.scpstamilnadu.service.UserService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.pdf.codec.Base64;

/**
 * This controller class will handle all the request that are coming for login and XForm assignment and all.
 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
 * @since version 1.0.0.0
 *
 */

@Controller
public class AuthenticateController implements AuthenticationProvider{
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticateController.class);
	
	@Autowired
	private AuthenticateService authenticateService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageDigestPasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "authenticate", method = RequestMethod.POST)
	public @ResponseBody List<ProgramXFormsModel> authenticate(@RequestParam("userString") String userString,HttpServletResponse response) throws IOException{
		
		byte[] decodedUserString = Base64.decode(userString);		
		String decodedUserStringString = new String (decodedUserString);		
		String splitArray[] = decodedUserStringString.split(":");
		
		if(splitArray.length == 2){
			String username = splitArray[0];
			String password = splitArray[1];
			try{
				

				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
				String encodedPassword = passwordEncoder.encodePassword(token.getName(),(String)token.getCredentials());
			    this.authenticate(token);
				return authenticateService.getProgramWithXFormsList(username, encodedPassword);				
			}catch(Exception e){
				logger.error("unauthorized server hit for login User String : " + userString);
				response.sendError(org.apache.http.HttpStatus.SC_UNAUTHORIZED);
			}			
			
		}else{
			logger.error("invalid string length : " + userString);
			response.sendError(org.apache.http.HttpStatus.SC_UNAUTHORIZED);
		}			
		return null;
	}
	/**
	 * This controller class will update all the form and all media files.
	 * @author Subhadarshani Patra (subhadarshani@sdrc.co.in)
	 * @since version 1.0.0.0
	 *
	 */
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public ModelToCollectApplication update(@RequestBody FormsToUpdate formsToUpdate,HttpServletResponse response) throws IOException{
		
		byte[] decodedUserString = Base64.decode(formsToUpdate.getUserString());		
		String decodedUserStringString = new String (decodedUserString);		
		String splitArray[] = decodedUserStringString.split(":");
		
		if(splitArray.length == 2){
			String username = splitArray[0];
			String password = splitArray[1];
			try{
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
				String encodedPassword = passwordEncoder.encodePassword(token.getName(),(String)token.getCredentials());
			    this.authenticate(token);
			    return authenticateService.getModelToCollectApplication(formsToUpdate.getFormsToDownloadMediafiles(),username, encodedPassword);
			}catch(Exception e){
				logger.error("unauthorized server hit for login User String : " + "");
				response.sendError(org.apache.http.HttpStatus.SC_UNAUTHORIZED);
			}			
			
		}else{
			logger.error("invalid string length : " + "");
			response.sendError(org.apache.http.HttpStatus.SC_UNAUTHORIZED);
		}		
		
		
		return authenticateService.getModelToCollectApplication(null, null, null);
	}
	
		@Override
		public Authentication authenticate(Authentication authentication)
				throws AuthenticationException {
			
			User collectUserModel = userService.findByUserName(authentication.getName());
			String encodedPassword = passwordEncoder.encodePassword(authentication.getName(),(String)authentication.getCredentials());
			if (collectUserModel == null ||!collectUserModel.getPassword().equals(encodedPassword)){
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



