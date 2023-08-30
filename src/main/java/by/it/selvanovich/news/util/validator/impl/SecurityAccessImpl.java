package by.it.selvanovich.news.util.validator.impl;

import by.it.selvanovich.news.util.validator.SecurityAccess;

import javax.servlet.http.HttpSession;
import java.util.Objects;

public class SecurityAccessImpl implements SecurityAccess {
    @Override
    public boolean haveSession(HttpSession session) {
        return session != null;
    }

    @Override
    public boolean haveAdminPermissions(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (this.haveSession(session)){
            return Objects.equals(role, "admin");
        } else {
            return false;
        }
    }

    @Override
    public boolean haveAuthorizedUser(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (this.haveSession(session)){
            return !Objects.equals(role, null);
        } else {
            return false;
        }
    }
}
