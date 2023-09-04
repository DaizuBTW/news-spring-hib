package by.it.selvanovich.news.controller;

import by.it.selvanovich.news.bean.News;
import by.it.selvanovich.news.bean.Role;
import by.it.selvanovich.news.bean.User;
import by.it.selvanovich.news.bean.UserDetails;
import by.it.selvanovich.news.services.NewsService;
import by.it.selvanovich.news.services.ServiceException;
import by.it.selvanovich.news.services.UserService;
import by.it.selvanovich.news.util.validator.SecurityAccess;
import by.it.selvanovich.news.util.validator.ValidatorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static by.it.selvanovich.news.constants.JSPConstants.*;
import static by.it.selvanovich.news.constants.UserConstants.*;


@Controller
public class ApplicationController {

    @Autowired
    private UserService userService;
    @Autowired
    private NewsService newsService;
    private final SecurityAccess accessValidation = ValidatorProvider.getInstance().getSecurityAccess();

    @ModelAttribute("user")
    private User insertUserInModel() {
        User user = new User();
        user.setUserDetails(new UserDetails());
        user.setRole(new Role(3, USER_ROLE_USER));
        UserDetails userDetails = user.getUserDetails();
        userDetails.setUser(user);

        return user;
    }

    @ModelAttribute("news")
    private News insertNewsInModel() {
        News news = new News();
        news.setUser(new User());
        return news;
    }

    @RequestMapping("/main")
    public String main(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        try {

            if (accessValidation.haveAuthorizedUser(session)) {
                return "redirect:" + NEWS_LIST;
            } else {
                model.addAttribute(MAPPING_PARAM, GUEST_INFO);
                request.setAttribute(NEWS_LIST, newsService.getLatestList(5));
                return BASE_LAYOUT;
            }
        } catch (ServiceException e) {
            return "redirect:" + ERROR;
        }
    }

    @RequestMapping("/newsList")
    public String goToNewsList(HttpServletRequest request, Model model) {

        List<News> newsList;

        HttpSession session = request.getSession();

        try {
            if (accessValidation.haveAuthorizedUser(session)) {
                newsList = newsService.getList();
                model.addAttribute(MAPPING_PARAM, NEWS_LIST);
                request.setAttribute(NEWS_LIST, newsList);
                request.getSession().setAttribute(ERROR, null);
                return BASE_LAYOUT;
            } else {
                return "redirect:" + MAIN;
            }
        } catch (ServiceException e) {
            return "redirect:" + ERROR;
        }
    }

    @RequestMapping("/viewNews")
    public String viewNews(HttpServletRequest request, Model model) {
        String id = request.getParameter(JSP_ID_PARAM);

        HttpSession session = request.getSession();

        try {
            if (accessValidation.haveAuthorizedUser(session)) {
                News news = newsService.findById(Integer.parseInt(id));
                request.getSession().setAttribute(ATTRIBUTE_NEWS, news);
                model.addAttribute(MAPPING_PARAM, VIEW_NEWS);
                request.getSession().setAttribute(ERROR, null);
                return BASE_LAYOUT;
            } else {
                return "redirect:" + NEWS_LIST;
            }
        } catch (ServiceException e) {
            return "redirect:" + ERROR;
        }
    }

    @RequestMapping("/addNews")
    public String goToAddNews(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();

        if (accessValidation.haveAdminPermissions(session)) {
            model.addAttribute(MAPPING_PARAM, ADD_NEWS);
            request.getSession().setAttribute(ERROR, null);
            return BASE_LAYOUT;
        } else {
            request.getSession(true).setAttribute(ERROR, "local.error.name.access_error");
            return "redirect:" + MAIN;
        }

    }

    @RequestMapping("/doAddNews")
    public String doAddNews(@ModelAttribute("news") News news, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userId = Integer.parseInt(request.getParameter(USER_USER_ID_PARAM));
        news.getUser().setId(userId);
        try {
            if (accessValidation.haveAdminPermissions(session)) {
                newsService.addNews(news);
                request.getSession().setAttribute(ERROR, null);
            } else {
                request.getSession(true).setAttribute(ERROR, "local.error.name.access_error");
            }
            return "redirect:" + MAIN;
        } catch (ServiceException e) {
            return "redirect:" + ERROR;
        }


    }

    @RequestMapping("/updateNews")
    public String goToUpdateNews(HttpServletRequest request, Model model) {

        String id = request.getParameter(JSP_ID_PARAM);
        System.out.println(id);

        HttpSession session = request.getSession();

        try {
            if (accessValidation.haveAdminPermissions(session)) {
                News news = newsService.findById(Integer.parseInt(id));
                request.getSession().setAttribute(ATTRIBUTE_NEWS, news);
                request.getSession().setAttribute(ERROR, null);
                model.addAttribute(MAPPING_PARAM, UPDATE_NEWS);
                return BASE_LAYOUT;
            } else {
                request.getSession(true).setAttribute(ERROR, "local.error.name.access_error");
                return "redirect:" + MAIN;
            }
        } catch (ServiceException e) {
            return "redirect:" + ERROR;
        }
    }

    @RequestMapping("/doUpdateNews")
    public String doUpdateNews(@ModelAttribute("news") News news, HttpServletRequest request) {

        int userId = Integer.parseInt(request.getParameter(USER_USER_ID_PARAM));
        news.getUser().setId(userId);

        HttpSession session = request.getSession();

        try {
            if (accessValidation.haveAdminPermissions(session)) {
                if (newsService.update(news)) {
                    request.getSession().setAttribute(ERROR, null);
                    return "redirect:" + VIEW_NEWS + "?id=" + news.getId();
                } else {
                    request.getSession(true).setAttribute(ERROR, "local.error.name.news_error");
                    return "redirect:" + NEWS_LIST;
                }
            } else {
                request.getSession(true).setAttribute(ERROR, "local.error.name.access_error");
                return "redirect:" + MAIN;
            }
        } catch (ServiceException e) {
            return "redirect:" + ERROR;
        }
    }

    @RequestMapping("doDeleteNews")
    public String doDeleteNews(HttpServletRequest request, @RequestParam("id") String[] idArr) {
        int[] newsIds = Stream.of(idArr).mapToInt(Integer::parseInt).toArray();

        HttpSession session = request.getSession();

        try {
            if (accessValidation.haveAdminPermissions(session)) {
                newsService.delete(newsIds);
                return "redirect:" + NEWS_LIST;
            } else {
                request.setAttribute(ERROR, "local.error.name.access_error");

                return BASE_LAYOUT;
            }
        } catch (ServiceException e) {
            return "redirect:" + ERROR;
        }
    }

    @RequestMapping("/signIn")
    public String doSignIn(HttpServletRequest request) {
        String username = request.getParameter(USER_USERNAME_PARAM);
        String password = request.getParameter(USER_PASSWORD_PARAM);

        try {
            User user = userService.authorization(username, password);
            String role = USER_ROLE_GUEST;
            if (user != null) {
                role = user.getRole().getTitle();
            }

            if (!role.equals(USER_ROLE_GUEST)) {
                request.getSession(true).setAttribute(ATTRIBUTE_USER_STATUS, ATTRIBUTE_ACTIVE);
                request.getSession().setAttribute(USER_ROLE_PARAM, role);
                request.getSession().setAttribute(ATTRIBUTE_USER, user);
                request.getSession().setAttribute(ERROR, null);
                return "redirect:" + MAIN;
            } else {
                request.getSession(true).setAttribute(ATTRIBUTE_USER_STATUS, ATTRIBUTE_NOT_ACTIVE);
                request.getSession().setAttribute(ERROR, "local.error.name.login_error");
                return "redirect:" + MAIN + "#signin";
            }
        } catch (ServiceException e) {
            return "redirect:" + ERROR;
        }
    }

    @RequestMapping("/signOut")
    public String doSignOut(HttpServletRequest request) {
        request.getSession(true).setAttribute(ATTRIBUTE_USER_STATUS, ATTRIBUTE_NOT_ACTIVE);
        request.getSession().setAttribute(USER_ROLE_PARAM, null);

        return "redirect:" + MAIN;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String signUp(@ModelAttribute("user") User user, HttpServletRequest request) {
        try {
            if (userService.registration(user)) {
                request.getSession(true).setAttribute(ATTRIBUTE_USER_STATUS, ATTRIBUTE_ACTIVE);
                request.getSession(true).setAttribute(ATTRIBUTE_USER, user);
                request.getSession().setAttribute(ERROR, null);
                return "redirect:" + MAIN;
            } else {
                request.getSession(true).setAttribute(ERROR, "local.error.name.reg_error");
                return "redirect:" + MAIN + "#registration";
            }

        } catch (ServiceException e) {
            return "redirect:" + ERROR;
        }
    }

    @RequestMapping(value = "/userlist")
    private String goToUserList(HttpServletRequest request, Model model) {

        String presentation = request.getParameter(MAPPING_PARAM);
        model.addAttribute(MAPPING_PARAM, Objects.requireNonNullElse(presentation, USER_LIST));
        try {
            request.setAttribute(ATTRIBUTE_USERS, userService.getUserList());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return BASE_LAYOUT;
    }

    @RequestMapping("/localization")
    public String localization(@RequestParam(required = false, value = "local") String local, HttpServletRequest request) {
        request.getSession(true).setAttribute(ATTRIBUTE_LOCAL, local);

        return "redirect:" + MAIN;
    }

    @RequestMapping("/error")
    public String goToErrorPage() {
        return ERROR;
    }
}
