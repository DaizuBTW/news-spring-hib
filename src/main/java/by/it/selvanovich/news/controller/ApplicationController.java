package by.it.selvanovich.news.controller;

import by.it.selvanovich.news.bean.News;
import by.it.selvanovich.news.services.ServiceException;
import by.it.selvanovich.news.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ApplicationController {

    private static final String MAPPING_PARAM = "presentation";
    private static final String ERROR = "error";
    private static final String MAPPING_NEWS_LIST = "newsList";
    private static final String MAPPING_BASE_LAYOUT = "baseLayout";

    @Autowired
    UserService userService;

    @RequestMapping("/main")
    public String main(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String presentation = request.getParameter(MAPPING_PARAM);

        List<News> newsList = new ArrayList<>();

        try {

            model.addAttribute(MAPPING_PARAM, MAPPING_NEWS_LIST);
            request.setAttribute("news", newsList);
            return MAPPING_BASE_LAYOUT;

        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    @RequestMapping("signIn")
    public String doSignIn(HttpServletRequest request, Model model) {

    //String username = request.getParameter(JSP_USERNAME_PARAM);
    //String password = request.getParameter(JSP_PASSWORD_PARAM);

    try {

    /*String role = userService.authorization("username", "password");
            User user = userService.getUserDetails(username);

            if (!role.equals("guest")) {
                request.getSession(true).setAttribute("user", "active");
                request.getSession().setAttribute("role", role);
                request.getSession().setAttribute("username", username);
                request.getSession().setAttribute("name", user.getName());
                request.getSession().setAttribute("surname", user.getSurname());

                return "redirect:" + MAPPING_BASE_LAYOUT;
            } else {

                return ERROR;
            }*/
        userService.authorization("asd", "asd");
        return MAPPING_BASE_LAYOUT;
    } catch (ServiceException e) {
    e.printStackTrace();

    return ERROR;
    }
    }
}
