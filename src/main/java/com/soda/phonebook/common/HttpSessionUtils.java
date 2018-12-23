package com.soda.phonebook.common;

import javax.servlet.http.HttpSession;

import com.soda.phonebook.domain.User;
import com.soda.phonebook.security.SessionConstants;

public class HttpSessionUtils {
	
	public static final String USER_SESSION_KEY = SessionConstants.LOGIN_USER;
	
	public static boolean isLoginUser(HttpSession session) {
		Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
		if(sessionedUser == null) {
			return false;
		}
		return true;
	}
	
	public static User getUserFromSession(HttpSession session) {
		if (!isLoginUser(session)) {
			return null;
		}
		return (User) session.getAttribute(USER_SESSION_KEY);
	}
}
