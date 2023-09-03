package by.it.selvanovich.news.services;

import by.it.selvanovich.news.bean.News;

import java.util.List;

public interface NewsService {

    boolean update(News news) throws ServiceException;

    List<News> latestList(int count) throws ServiceException;

    List<News> getList() throws ServiceException;

    News findById(int id) throws ServiceException;

    boolean addNews(News news) throws ServiceException;

    boolean delete(int[] newsIds) throws ServiceException;
}