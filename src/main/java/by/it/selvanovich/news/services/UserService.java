package by.it.selvanovich.news.services;

import by.it.selvanovich.news.bean.User;

import java.util.List;

public interface UserService {
	
	User authorization(String username, String password) throws ServiceException;
	boolean registration(User user) throws ServiceException;
	User getUserDetails(String username) throws ServiceException;
	List<User> getUserList() throws ServiceException;

}