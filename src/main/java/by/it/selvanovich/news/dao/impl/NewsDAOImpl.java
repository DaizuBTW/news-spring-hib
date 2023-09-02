package by.it.selvanovich.news.dao.impl;

import by.it.selvanovich.news.bean.News;
import by.it.selvanovich.news.dao.DAOException;
import by.it.selvanovich.news.dao.NewsDAO;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NewsDAOImpl implements NewsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private static final String ERR_MESSAGE_SQL = "sql error";
    private static final String ERR_MESSAGE_CONNECTION_POOL = "error trying to take connection";

    private static final String DATE_FORMAT = "dd.MM.yyyy HH:mm";

    private static final String SQL_SHOW_LIST = "SELECT id, title, brief, content, date, category " +
            "FROM news JOIN newscategory n on news.category_id = n.news_category_id ORDER BY date DESC";
    private static final String SQL_SHOW_LIST_BY_FILTER = "SELECT id, title, brief, content, date, category " +
            "FROM news JOIN newscategory n on news.category_id = n.news_category_id WHERE category_id = ? ORDER BY date DESC";
    private static final String SQL_SHOW_LAST_NEWS_LIST = "SELECT id, title, brief, content, date, category " +
            "FROM news JOIN newscategory n ON category_id = n.news_category_id ORDER BY date DESC LIMIT ?";
    private static final String SQL_SHOW_BY_ID = "SELECT id, title, brief, content, date, category " +
            "FROM news JOIN newscategory n on news.category_id = n.news_category_id WHERE id = ?";
    private static final String SQL_ADD_NEWS = "INSERT INTO news(content,title,brief,date,category_id,users_id) VALUES(?,?,?,?,?,?)";
    private static final String SQL_UPDATE_NEWS = "UPDATE news SET content=?,title=?,brief=?,date=?,category_id=?,users_id=? WHERE id = ?";
    private static final String SQL_DELETE_NEWS = "DELETE FROM news WHERE id = ?";

    @Override
    public List<News> getList() throws DAOException {
        try {
            Session currentSession = sessionFactory.getCurrentSession();

            Query<News> newsQuery = currentSession
                    .createQuery("FROM News n INNER JOIN FETCH n.user ORDER BY n.date DESC", News.class);

            return newsQuery.getResultList();
        }  catch (HibernateException e) {
            throw new DAOException("Hibernate are getting problems with news list", e);
        }
    }

    @Override
    public List<News> getListByFilter(int category) throws DAOException {
        return null;
    }

    @Override
    public List<News> getLatestList(int count) throws DAOException {
        return null;
    }

    @Override
    public News findById(int id) throws DAOException {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            News news = currentSession
                    .createQuery("SELECT n FROM News n INNER JOIN FETCH n.user WHERE n.id = :id", News.class)
                    .setParameter("id", id)
                    .uniqueResult();

            return news;
        }  catch (HibernateException e) {
            throw new DAOException("Hibernate error", e);
        }
    }

    @Override
    public int addNews(News news) throws DAOException {
        try {
            Session currentSession = sessionFactory.getCurrentSession();

            return (Integer) currentSession.save(news);
        }  catch (HibernateException e) {
            throw new DAOException("Hibernate are getting problems with news adding", e);
        }
    }

    @Override
    public void updateNews(News news) throws DAOException {
        try {
            Session currentSession = sessionFactory.getCurrentSession();

            currentSession.update(news);
        }  catch (HibernateException e) {
            throw new DAOException("Hibernate are getting problems with news updating", e);
        }
    }

    @Override
    public void deleteNewses(String[] idNewses) throws DAOException {

    }
}
