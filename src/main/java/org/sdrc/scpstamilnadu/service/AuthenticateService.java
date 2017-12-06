package org.sdrc.scpstamilnadu.service;

import java.util.List;

import org.sdrc.scpstamilnadu.model.FormsToDownloadMediafiles;
import org.sdrc.scpstamilnadu.model.MediaFilesToUpdate;
import org.sdrc.scpstamilnadu.model.ModelToCollectApplication;
import org.sdrc.scpstamilnadu.model.ProgramXFormsModel;




/**
 * This Service Interface is responsible for Authenticate the user and give respective data to user
 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
 * @author Subhadarshani Patra<subhadarshani@sdrc.co.in>
 * @since version 1.0.0.0
 *
 */

public interface AuthenticateService {

	List<ProgramXFormsModel> getProgramWithXFormsList(String username, String password);
	
	ModelToCollectApplication getModelToCollectApplication(List<FormsToDownloadMediafiles> list,String username,String password);
	List<MediaFilesToUpdate> getMediaFilesToUpdate(List<FormsToDownloadMediafiles> list);

}
