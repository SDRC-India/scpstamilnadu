package org.sdrc.scpstamilnadu.repository;

import java.util.List;

import org.sdrc.scpstamilnadu.domain.XForm;
import org.springframework.transaction.annotation.Transactional;

public interface XFormRepository {

	@Transactional
	XForm save(XForm xForm);

	XForm findByXFormIdAndIsLiveTrue(String getxFormId);

	XForm findByFormId(Integer id);

	List<XForm> findAllByIsLiveTrue();

	XForm findByxFormIdAndIsLiveTrue(String xFormId);

	void updateIsLiveById(Integer xFormId);

	
	
	List<String> findAllXformNameByIsLiveTrue();

}
