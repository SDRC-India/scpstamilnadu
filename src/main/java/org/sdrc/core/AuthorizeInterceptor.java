package org.sdrc.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdrc.scpstamilnadu.model.FeaturePermissionMappingModel;
import org.sdrc.scpstamilnadu.model.UserAreaModel;
import org.sdrc.scpstamilnadu.model.UserModel;
import org.sdrc.scpstamilnadu.util.Constants;
import org.sdrc.scpstamilnadu.util.StateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * @author Azaruddin (azaruddin@sdrc.co.in)
 *
 */

@Component
public class AuthorizeInterceptor extends HandlerInterceptorAdapter {
	private final StateManager stateManager;
	private final ResourceBundleMessageSource errorMessageSource;
	
	private final Logger _log = LoggerFactory.getLogger(AuthorizeInterceptor.class);

	@Autowired
	public AuthorizeInterceptor(StateManager stateManager,
			ResourceBundleMessageSource errorMessageSource) {
		this.stateManager = stateManager;
		this.errorMessageSource = errorMessageSource;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		
		if (!(handler instanceof HandlerMethod))
			return true;

		Authorize authorize = ((HandlerMethod) handler)
				.getMethodAnnotation(Authorize.class);

		if (authorize == null)
			return true;

		UserModel user = (UserModel) stateManager.getValue(Constants.Web.USER_PRINCIPAL);
		if (user == null)
			throw new AccessDeniedException(errorMessageSource.getMessage(
					Constants.Web.ACCESS_DENIED, null, null));
		
		List<String> feature = new ArrayList<String>();
		feature =	Arrays.asList(authorize.feature().split(","));
		String permission = authorize.permission();

		_log.info("Annotation has been given feature : {} , permission : {}",feature,permission);
		
		List<UserAreaModel> userAreaModels = user != null ? user.getUserAreaModels() : null;
		
		if (null != userAreaModels) {
			for (UserAreaModel userAreaModel : userAreaModels) {
				if (userAreaModel.getUserRoleFeaturePermissionMappings() != null) {
					for (int i = 0; i < userAreaModel.getUserRoleFeaturePermissionMappings().size(); i++) {
						FeaturePermissionMappingModel fpMapping = userAreaModel.getUserRoleFeaturePermissionMappings()
								.get(i).getRoleFeaturePermissionSchemeModel().getFeaturePermissionMapping();
				
						_log.info("User has been given feature : {} , permission : {}",fpMapping.getFeature().getFeatureName(),fpMapping.getPermission().getPermissionName());
					
						if (feature.contains(fpMapping.getFeature().getFeatureName())
								&& permission.equals(fpMapping.getPermission().getPermissionName())) {
							return true;
						}
					}
				}
			}
		}
		
		throw new AccessDeniedException(errorMessageSource.getMessage(
				Constants.Web.ACCESS_DENIED, null, null));
	}

}
