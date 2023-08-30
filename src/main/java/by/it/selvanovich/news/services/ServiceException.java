package by.it.selvanovich.news.services;

public class ServiceException extends Exception {

    public ServiceException(String e) {
        super(e);
    }

    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String message, Exception e) {
        super(message, e);
    }
}