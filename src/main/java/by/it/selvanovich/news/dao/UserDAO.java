package by.it.selvanovich.news.dao;

import by.it.selvanovich.news.bean.User;
import by.it.selvanovich.news.services.ServiceException;

import java.util.List;

public interface UserDAO {
    User authorization(String username, String password) throws DAOException;

    boolean registration(User user) throws DAOException;

    String getRole(String username) throws DAOException;

    User getUserDetails(String username) throws DAOException;
    List<User> getUserList() throws DAOException;
}
