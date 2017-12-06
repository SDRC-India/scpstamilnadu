package org.sdrc.scpstamilnadu.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.sdrc.scpstamilnadu.domain.Agency;
import org.sdrc.scpstamilnadu.model.UserModel;
import org.sdrc.scpstamilnadu.repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class CookieHandler {

	@Autowired
	private AgencyRepository agencyRepository;

	@Autowired
	private StateManager stateManager;

	public String retrieveEncryptedAgencyIdFromCookie(HttpServletRequest re) {
		String encryptedSesisonId = null;
		// check if myagencyId cookie found,if not found throw exception
		Cookie[] cookies = re.getCookies();
		for (Cookie cookie : cookies) {
			String cookieName = cookie.getName();
			if (cookieName.equals("myagencyId")) {
				encryptedSesisonId = cookie.getValue();
			}
		}
		if (encryptedSesisonId == null) {
			throw new RuntimeException("Please attach agencyId in parameters !!");
		}
		return encryptedSesisonId;
	}

	public String retrieveOrSetEncryptedAgencyIdCookie(String agencyId, HttpServletRequest re, HttpServletResponse res) {
		String encryptedSesisonId = null;
		if (agencyId == null) {
			// check if myagencyId cookie found,if not found throw exception
			Cookie[] cookies = re.getCookies();
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if (cookieName.equals("myagencyId")) {
					encryptedSesisonId = cookie.getValue();
				}
			}
			if (encryptedSesisonId == null) {
				throw new RuntimeException("Please attach agencyId in parameters !!");
			}
		} else {
			Agency agency = agencyRepository.findByEncryptedAgencyId(agencyId);
			if (agency != null) {
				UserModel userModel = ((UserModel) stateManager.getValue(Constants.Web.USER_PRINCIPAL));
				if (userModel == null) {
					Cookie agencyCookie = new Cookie("myagencyId", agencyId);
					agencyCookie.setComment("To detect which agencys data to display");
					res.addCookie(agencyCookie);
					encryptedSesisonId = agencyId;
				} else {
					// Since user is logged in we will return encryptedagencyId of the agency to which user belongs
					// although user has provided agencyId in query string.This check is to prevent user
					// from seeing others states data if user is logged.i.e user can pass encryptedagencyId of
					// other state query string while he/she is logged in.
					Cookie agencyCookie = new Cookie("myagencyId", agency.getEncryptedAgencyId());
					agencyCookie.setComment("To detect which agencys data to display");
					res.addCookie(agencyCookie);
					encryptedSesisonId =  agency.getEncryptedAgencyId();
				}
			} else {
				throw new RuntimeException("Invalid agencyId attached in parameters !!");
			}
		}
		return encryptedSesisonId;
	}

	public void setEncryptedAgencyIdInCookieForLoggedInUser(int agencyId, HttpServletResponse res) {
		Agency agency = agencyRepository.findByAgencyId(agencyId);
		Cookie agencyCookie = new Cookie("myagencyId", agency.getEncryptedAgencyId());
		agencyCookie.setComment("To detect which agencys data to display");
		res.addCookie(agencyCookie);
	}

}
