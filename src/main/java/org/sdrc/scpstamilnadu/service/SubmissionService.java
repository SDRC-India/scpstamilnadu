package org.sdrc.scpstamilnadu.service;

import javax.servlet.http.HttpServletRequest;

import org.sdrc.scpstamilnadu.model.CollectUserModel;
import org.sdrc.scpstamilnadu.model.PostSubmissionModel;



/**
 * This Service Interface is responsible for the submission of forms
 * 
 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
 * @since version 1.0.0
 *
 */
public interface SubmissionService {

	/**
	 * 
	 * This following method will do all the upload work 
	 * @return an in t value, if it is zero then the upload is successful
	 * @param request, the request object from client request
	 * @param deviceID, the client mobile IMEI number
	 * @param username, the clients username
	 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in) on 01-Jun-2017 4:59:15 pm
	 */
	int uploadForm(HttpServletRequest request, String deviceID, CollectUserModel collectUserModel);

	/**
	 * This method will be called from PostSubmissionThread to do job which
	 * needs to be done after form submission
	 * 
	 * @param postSubmissionModel
	 *            The thread will pass this argument to this method which
	 *            contains form details which recently got submitted
	 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
	 */
	void postSubmissionWork(PostSubmissionModel postSubmissionModel);
	
	/**
	 * This method will be responsible for sending notification to user with excel attachment file
	 * 
	 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
	 * @param postSubmissionModel The data which we need while doing the notification work after submission of a form
	 */


}
