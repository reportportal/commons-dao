package com.epam.ta.reportportal.util;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * @author Ivan Budaev
 */
public final class UserUtils {

	private UserUtils(){
		//static only
	}

	/**
	 * Validate email format against RFC822
	 *
	 * @param email Email to be validated
	 * @return TRUE of email is valid
	 */
	public static boolean isEmailValid(String email) {
		return EmailValidator.getInstance().isValid(email);
	}
}
