package by.it.selvanovich.news.services.impl;

import by.it.selvanovich.news.bean.User;
import by.it.selvanovich.news.dao.DAOException;
import by.it.selvanovich.news.dao.UserDAO;
import by.it.selvanovich.news.services.ServiceException;
import by.it.selvanovich.news.services.UserService;
import by.it.selvanovich.news.util.validator.UserValidator;
import by.it.selvanovich.news.util.validator.ValidatorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class UserServiceImpl implements UserService {

    private final static ReentrantLock locker = new ReentrantLock();
    private final UserValidator userValidator = ValidatorProvider.getInstance().getUserValidator();

    @Autowired
    private UserDAO userDAO;




    //@Override
    @Transactional
    public User authorization(String username, String password) throws ServiceException {
        try {
            return userDAO.authorization(username, password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }


    //@Override

    @Transactional
    public boolean registration(User user) throws ServiceException {
        locker.lock();
        try {
            if (userValidator.isUserValid(user)) {
                userDAO.registration(user);
                return true;
            } else {
                return false;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
            locker.unlock();
        }
    }
    //@Override

    @Transactional
    public User getUserDetails(String username) throws ServiceException {
        try {
            return userDAO.getUserDetails(username);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    @Transactional
    public List<User> getUserList() throws ServiceException {
        try {
            return userDAO.getUserList();
        } catch (DAOException e) {
            throw new ServiceException("Services getting problems with user list", e);
        }
    }
}
