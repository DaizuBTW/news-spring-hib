package by.it.selvanovich.news.dao;

import by.it.selvanovich.news.bean.News;

import java.util.List;

public interface NewsDAO {
    List<News> getList() throws DAOException;
    List<News> getListByFilter(int category) throws DAOException;

    List<News> getLatestList(int count) throws DAOException;

    News findById(int id) throws DAOException;

    int addNews(News news) throws DAOException;

    void updateNews(News news) throws DAOException;

    void deleteNewses(String[] idNewses) throws DAOException;

}
