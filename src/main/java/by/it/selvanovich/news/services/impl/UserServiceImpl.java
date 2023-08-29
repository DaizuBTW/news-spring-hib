package by.it.selvanovich.news.services.impl;

import by.it.selvanovich.news.bean.User;
import by.it.selvanovich.news.dao.DAOException;
import by.it.selvanovich.news.dao.UserDAO;
import by.it.selvanovich.news.services.ServiceException;
import by.it.selvanovich.news.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.ReentrantLock;

@Service
public class UserServiceImpl implements UserService {

    private final static ReentrantLock locker = new ReentrantLock();

    @Autowired
    private UserDAO userDAO;



    //@Override
    @Transactional
    public String authorization(String username, String password) throws ServiceException {
        try {
            System.out.println(userDAO.authorization("admadm", "password3331"));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return "false";
    }



    //@Override
    @Transactional
    public boolean registration(User user) throws ServiceException {
        locker.lock();
        try {
            userDAO.registration(user);
            return true;
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
}
