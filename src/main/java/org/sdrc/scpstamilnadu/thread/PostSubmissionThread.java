package org.sdrc.scpstamilnadu.thread;

import org.apache.log4j.Logger;
import org.sdrc.scpstamilnadu.model.PostSubmissionModel;
import org.sdrc.scpstamilnadu.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * This thread will do all the job that needs to be done after submission of a
 * particular form
 * 
 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
 * @since version 1.0.0.0
 */
@Component
@Scope("prototype")
public class PostSubmissionThread extends Thread {

	private static final Logger logger = Logger.getLogger(PostSubmissionThread.class);
	
	private PostSubmissionModel postSubmissionModel;

	public PostSubmissionModel getPostSubmissionModel() {
		return postSubmissionModel;
	}

	public void setPostSubmissionModel(PostSubmissionModel postSubmissionModel) {
		this.postSubmissionModel = postSubmissionModel;
	}

	@Autowired
	private SubmissionService submissionService;

	@Override
	public void run() {

		try {
			logger.info("IN POST SUBMISSION THREAD...");
			submissionService.postSubmissionWork(getPostSubmissionModel());
			logger.info("POST SUBMISSION THREAD COMPLETED...");
		} catch (Exception e) {
			logger.error("ERROR : Exception while calling postSubmissionWork ", e);
		}
		

	}

}
