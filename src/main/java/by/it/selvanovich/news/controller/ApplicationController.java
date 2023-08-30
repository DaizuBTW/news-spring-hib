package by.it.selvanovich.news.controller;

import by.it.selvanovich.news.bean.News;
import by.it.selvanovich.news.bean.Role;
import by.it.selvanovich.news.bean.User;
import by.it.selvanovich.news.bean.UserDetails;
import by.it.selvanovich.news.services.ServiceException;
import by.it.selvanovich.news.services.UserService;
import by.it.selvanovich.news.util.validator.SecurityAccess;
import by.it.selvanovich.news.util.validator.ValidatorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
public class ApplicationController {

    private static final String JSP_USERNAME_PARAM = "username";
    private static final String JSP_PASSWORD_PARAM = "password";
    private static final String JSP_NAME_PARAM = "name";
    private static final String JSP_SURNAME_PARAM = "surname";

    private static final String JSP_ID_PARAM = "id";
    private static final String JSP_TITLE_PARAM = "title";
    private static final String JSP_BRIEF_PARAM = "brief";
    private static final String JSP_CONTENT_PARAM = "content";
    private static final String JSP_DATE_PARAM = "date";
    private static final String JSP_CATEGORY_PARAM = "category";

    private static final String MAPPING_MAIN = "main";
    private static final String MAPPING_NEWS_LIST = "newsList";
    private static final String MAPPING_USER_LIST = "userList";
    private static final String MAPPING_VIEW_NEWS = "viewNews";
    private static final String MAPPING_ADD_NEWS = "addNews";
    private static final String MAPPING_UPDATE_NEWS = "updateNews";
    private static final String MAPPING_SIGN_IN = "signIn";
    private static final String MAPPING_SIGN_OUT = "signOut";
    private static final String MAPPING_GUEST_INFO = "guestInfo";
    private static final String MAPPING_PARAM = "presentation";
    private static final String ERROR = "error";
    private static final String BASE_LAYOUT = "baseLayout";

    @Autowired
    private UserService userService;
    private final SecurityAccess accessValidation = ValidatorProvider.getInstance().getSecurityAccess();

    @ModelAttribute("user")
    private User insertUserInModel() {
        User user = new User();
        user.setUserDetails(new UserDetails());
        user.setRole(new Role(3, "user"));
        UserDetails userDetails = user.getUserDetails();
        userDetails.setUser(user);

        return user;
    }

    @RequestMapping("/main")
    public String main(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String presentation = request.getParameter(MAPPING_PARAM);

        List<News> newsList = new ArrayList<>();

        if (accessValidation.haveAuthorizedUser(session)) {
            model.addAttribute(MAPPING_PARAM, Objects.requireNonNullElse(presentation, MAPPING_NEWS_LIST));
        } else {
            model.addAttribute(MAPPING_PARAM, MAPPING_GUEST_INFO);
            request.setAttribute("news", newsList);
        }
        return BASE_LAYOUT;
    }

    @RequestMapping("/signIn")
    public String doSignIn(HttpServletRequest request, Model model) {

        String username = request.getParameter(JSP_USERNAME_PARAM);
        String password = request.getParameter(JSP_PASSWORD_PARAM);

        try {
            String role = "guest";
            User user = userService.authorization(username, password);
            if (user != null) {
                role = user.getRole().getTitle();
            }

            if (!role.equals("guest")) {
                request.getSession(true).setAttribute("user", "active");
                request.getSession().setAttribute("role", role);
                request.getSession().setAttribute("username", username);
                request.getSession().setAttribute("name", user.getUserDetails().getName());
                request.getSession().setAttribute("surname", user.getUserDetails().getSurname());

                return "redirect:" + MAPPING_MAIN;
            } else {
                request.getSession(true).setAttribute("user", "not active");
                model.addAttribute(ERROR, "local.error.name.login_error");

                return ERROR;
            }
        } catch (ServiceException e) {
            e.printStackTrace();

            return ERROR;
        }
    }

    @RequestMapping("/signOut")
    public String doSignOut(HttpServletRequest request) {

        request.getSession(true).setAttribute("user", "not active");
        request.getSession().setAttribute("role", null);

        return "redirect:" + MAPPING_MAIN;
    }

    @RequestMapping("/registration1")
    public String registration(HttpServletRequest request) {
        String username = request.getParameter(JSP_USERNAME_PARAM);
        String password = request.getParameter(JSP_PASSWORD_PARAM);
        String name = request.getParameter(JSP_NAME_PARAM);
        String surname = request.getParameter(JSP_SURNAME_PARAM);

        try {
            User user = new User();
            UserDetails userDetails = new UserDetails(name, surname);
            Role role = new Role(3, "user");

            user.setUsername(username);
            user.setPassword(password);
            user.setUserDetails(userDetails);
            user.setRole(role);

            if (userService.registration(user)) {
                request.getSession(true).setAttribute("user", "active");
                request.getSession().setAttribute("role", "user");
                request.getSession().setAttribute("username", username);
                request.getSession().setAttribute("name", name);
                request.getSession().setAttribute("surname", surname);
                System.out.println("Success!");

            } else {
                request.setAttribute("error", "local.error.name.reg_error");
                System.out.println("errrrrr");
            }
            return "redirect:" + MAPPING_MAIN;
        } catch (ServiceException e) {
            request.getSession().setAttribute("errorMessage", "message");
            e.printStackTrace();

            return ERROR;
        }
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String signUp(@ModelAttribute("user") User user, HttpServletRequest request) throws ServiceException {
        if (userService.registration(user)) {
            request.getSession(true).setAttribute("user", "active");
            request.getSession().setAttribute("role", "user");
            request.getSession().setAttribute("username", user.getUsername());
            request.getSession().setAttribute("name", user.getUserDetails().getName());
            request.getSession().setAttribute("surname", user.getUserDetails().getSurname());
            System.out.println("Success!");

        } else {
            request.setAttribute("error", "local.error.name.reg_error");
            System.out.println("errrrrr");
        }
        return "redirect:" + MAPPING_MAIN;
    }

    @RequestMapping(value = "/userlist")
    private String userList(HttpServletRequest request, Model model) {

        String presentation = request.getParameter(MAPPING_PARAM);
        model.addAttribute(MAPPING_PARAM, Objects.requireNonNullElse(presentation, MAPPING_USER_LIST));
        try {
            request.setAttribute("users", userService.getUserList());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return BASE_LAYOUT;
    }
}
