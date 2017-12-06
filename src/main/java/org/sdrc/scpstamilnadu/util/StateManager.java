package org.sdrc.scpstamilnadu.util;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */
public interface StateManager {

	Object getValue(String key);
	
	void setValue(String Key, Object Value);

	void onApplicationEvent(ApplicationEvent event);

}