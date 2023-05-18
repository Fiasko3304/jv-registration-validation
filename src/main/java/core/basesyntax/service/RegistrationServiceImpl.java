package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MAX_AGE = 100;
    public static final int MIN_AGE = 18;
    public static final int MIN_LOGIN_LENGTH = 6;
    public static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationUserException {
        if (user == null) {
            throw new RegistrationUserException("Please enter your data for registration!");
        }
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkLogin(User user) throws RegistrationUserException {
        if (user.getLogin() == null) {
            throw new RegistrationUserException("Please enter your login!");
        } else if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationUserException("Your login must contain more than "
                    + MIN_LOGIN_LENGTH + " characters!");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationUserException("A user with this login already exists!");
        }
    }

    private void checkPassword(User user) throws RegistrationUserException {
        if (user.getPassword() == null) {
            throw new RegistrationUserException("Please enter your password!");
        } else if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationUserException("Your password must contain more than "
                    + MIN_PASSWORD_LENGTH + " characters!");
        }
    }

    private void checkAge(User user) throws RegistrationUserException {
        if (user.getAge() < 1 || user.getAge() > MAX_AGE) {
            throw new RegistrationUserException("Please enter the correct age!");
        } else if (user.getAge() < MIN_AGE) {
            throw new RegistrationUserException("You must be "
                    + MIN_AGE + " or older to register!");
        }
    }
}