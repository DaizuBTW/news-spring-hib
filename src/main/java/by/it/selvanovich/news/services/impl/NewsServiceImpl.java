package by.it.selvanovich.news.services.impl;

import by.it.selvanovich.news.bean.News;
import by.it.selvanovich.news.dao.DAOException;
import by.it.selvanovich.news.dao.NewsDAO;
import by.it.selvanovich.news.services.NewsService;
import by.it.selvanovich.news.services.ServiceException;
import by.it.selvanovich.news.util.validator.NewsValidator;
import by.it.selvanovich.news.util.validator.ValidatorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsValidator newsValidator = ValidatorProvider.getInstance().getNewsValidator();
    private final static ReentrantLock locker = new ReentrantLock();

    @Autowired
    private NewsDAO newsDAO;

    @Transactional
    public List<News> getLatestList(int count) throws ServiceException {
        try {
            return newsDAO.getLatestList(count);
        } catch (DAOException e) {
            throw new ServiceException("Services are getting problems with latest news list", e);
        }
    }

    @Transactional
    public List<News> getList() throws ServiceException {
        try {
            return newsDAO.getList();
        } catch (DAOException e) {
            throw new ServiceException("Services are getting problems with news list", e);
        }
    }

    @Transactional
    public News findById(int id) throws ServiceException {
        try {
            return newsDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException("Services are getting problems with finding news by id", e);
        }
    }

    @Transactional
    public boolean addNews(News news) throws ServiceException {
        locker.lock();
        try {
            if (newsValidator.isNewsValid(news)) {
                newsDAO.addNews(news);
                return true;
            } else {
                return false;
            }
        } catch (DAOException e) {
            throw new ServiceException("Services are getting problems with adding news", e);
        } finally {
            locker.unlock();
        }
    }

    @Transactional
    public boolean update(News news) throws ServiceException {
        locker.lock();
        try {
            if (newsValidator.isNewsValid(news)) {
                newsDAO.updateNews(news);
                return true;
            } else {
                return false;
            }
        } catch (DAOException e) {
            throw new ServiceException("Services are getting problems with updating news", e);
        } finally {
            locker.unlock();
        }
    }

    @Transactional
    public boolean delete(int[] newsIds) throws ServiceException {
        locker.lock();
        try {
            newsDAO.deleteNewses(newsIds);
            return true;
        } catch (DAOException e) {
            throw new ServiceException("Services are getting problems with deleting news", e);
        } finally {
            locker.unlock();
        }
    }
}