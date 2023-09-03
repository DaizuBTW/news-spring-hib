package by.it.selvanovich.news.dao.impl;

import by.it.selvanovich.news.bean.News;
import by.it.selvanovich.news.dao.DAOException;
import by.it.selvanovich.news.dao.NewsDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NewsDAOImpl implements NewsDAO {

    @Autowired
    private SessionFactory sessionFactory;


    private static final String DATE_FORMAT = "dd.MM.yyyy HH:mm";

    @Override
    public List<News> getList() throws DAOException {
        try {
            Session currentSession = sessionFactory.getCurrentSession();

            Query<News> newsQuery = currentSession
                    .createQuery("FROM News n INNER JOIN FETCH n.user ORDER BY n.date DESC", News.class);

            return newsQuery.getResultList();
        } catch (HibernateException e) {
            throw new DAOException("Hibernate are getting problems with news list", e);
        }
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
        } catch (HibernateException e) {
            throw new DAOException("Hibernate are getting problems with finding news by id", e);
        }
    }

    @Override
    public int addNews(News news) throws DAOException {
        try {
            Session currentSession = sessionFactory.getCurrentSession();

            return (Integer) currentSession.save(news);
        } catch (HibernateException e) {
            throw new DAOException("Hibernate are getting problems with news adding", e);
        }
    }

    @Override
    public void updateNews(News news) throws DAOException {
        try {
            Session currentSession = sessionFactory.getCurrentSession();

            currentSession.update(news);
        } catch (HibernateException e) {
            throw new DAOException("Hibernate are getting problems with news updating", e);
        }
    }

    @Override
    public void deleteNewses(int[] newsIds) throws DAOException {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            List<Integer> newsIdsList = Arrays.stream(newsIds).boxed().collect(Collectors.toList());

            currentSession
                    .createQuery("DELETE FROM News news WHERE news.id IN (:newsIds)")
                    .setParameterList("newsIds", newsIdsList)
                    .executeUpdate();
        } catch (HibernateException e) {
            throw new DAOException("Hibernate are getting problems with news deleting", e);
        }
    }
}
