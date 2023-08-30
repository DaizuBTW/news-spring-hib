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
    public boolean update(int id, String title, String brief, String content, String date, String category) throws ServiceException {
        return false;
    }

    @Transactional
    public List<News> latestList(int count) throws ServiceException {
        return null;
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
        return null;
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
    public boolean delete(String[] idNewses) throws ServiceException {
        return false;
    }
}