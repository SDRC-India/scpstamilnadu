package org.sdrc.scpstamilnadu.util;

/**
 * @author Harsh Pratyush(harsh@sdrc.co.in)
 */
public class DuplicateLoginUserException extends RuntimeException  {

	private static final long serialVersionUID = 1L;

	public DuplicateLoginUserException(String s) {
		super(s);
	}

}
