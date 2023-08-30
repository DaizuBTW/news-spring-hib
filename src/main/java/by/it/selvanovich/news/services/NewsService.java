package by.it.selvanovich.news.services;

import by.it.selvanovich.news.bean.News;

import java.util.List;

public interface NewsService {

    boolean update(int id, String title, String brief, String content, String date, String category) throws ServiceException;

    List<News> latestList(int count) throws ServiceException;

    List<News> getList() throws ServiceException;

    News findById(int id) throws ServiceException;

    boolean addNews(News news) throws ServiceException;

    boolean delete(String[] idNewses) throws ServiceException;
}