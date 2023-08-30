package by.it.selvanovich.news.util.validator;

import javax.servlet.http.HttpSession;

public interface SecurityAccess {
	
	boolean haveSession(HttpSession session);
	boolean haveAdminPermissions(HttpSession session);
	boolean haveAuthorizedUser(HttpSession session);

}