package by.it.selvanovich.news.services;

import by.it.selvanovich.news.bean.User;

public interface UserService {
	
	String authorization(String username, String password) throws ServiceException;
	boolean registration(User user) throws ServiceException;
	User getUserDetails(String username) throws ServiceException;

}