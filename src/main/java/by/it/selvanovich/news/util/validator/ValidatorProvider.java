package by.it.selvanovich.news.util.validator;


import by.it.selvanovich.news.util.validator.impl.NewsValidatorImpl;
import by.it.selvanovich.news.util.validator.impl.SecurityAccessImpl;
import by.it.selvanovich.news.util.validator.impl.UserValidatorImpl;

public class ValidatorProvider {

    private static final ValidatorProvider instance = new ValidatorProvider();
    private final SecurityAccess securityAccess = new SecurityAccessImpl();
    private final UserValidator userValidator = new UserValidatorImpl();
    private final NewsValidator newsValidator = new NewsValidatorImpl();


    private ValidatorProvider() {
    }

    public SecurityAccess getSecurityAccess() {
        return securityAccess;
    }
    public UserValidator getUserValidator() {
        return userValidator;
    }
    public NewsValidator getNewsValidator() {
        return newsValidator;
    }

    public static ValidatorProvider getInstance() {
        return instance;
    }
}
