package by.it.selvanovich.news.dao.impl;

import by.it.selvanovich.news.bean.User;
import by.it.selvanovich.news.dao.DAOException;
import by.it.selvanovich.news.dao.UserDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private static final String HQL_USER = "FROM User WHERE username = :username and password = :password";

    @Override
    public User authorization(String username, String password) throws DAOException {

        try {
            Session currentSession = sessionFactory.getCurrentSession();

            Query<User> theQuery = currentSession.createQuery(HQL_USER, User.class);

            User user = theQuery
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();

            return user;
        } catch (HibernateException e) {
            throw new DAOException("Hibernate getting problems with sign in", e);
        }
    }

    @Override
    public boolean registration(User user) throws DAOException {
        return false;
    }

    @Override
    public String getRole(String username) throws DAOException {
        return null;
    }

    @Override
    public User getUserDetails(String username) throws DAOException {
        return null;
    }
}
