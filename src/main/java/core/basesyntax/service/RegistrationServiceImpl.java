package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int VALID_LOGIN_LENGTH = 6;
    public static final int VALID_PASSWORD_LENGTH = 6;
    public static final int VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationServiceException("The user value can't be null");
        }

        checkLoginValid(user);
        checkPasswordValid(user);
        checkAgeAllowed(user);
        checkUserAlreadyExist(user);

        return storageDao.add(user);
    }

    public void checkUserAlreadyExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("User with " + user.getLogin()
                    + " as a login already exist");
        }
    }

    public void checkLoginValid(User user) {
        if(user.getLogin() == null) {
            throw new RegistrationServiceException("The login can't be null");
        }

        int loginLength = user.getLogin().trim().length();
        if (loginLength < VALID_LOGIN_LENGTH) {
            throw new RegistrationServiceException(
                    "The login must have at least " + VALID_LOGIN_LENGTH
                            + " symbols, but you have: "
                            + loginLength);
        }
    }

    public void checkPasswordValid(User user) {
        if(user.getPassword() == null) {
            throw new RegistrationServiceException("The password can't be null");
        }

        int passwordLength = user.getPassword().trim().length();
        if (passwordLength < VALID_PASSWORD_LENGTH) {
            throw new RegistrationServiceException(
                    "The password must have at least " + VALID_PASSWORD_LENGTH
                            + " symbols, but you have: "
                            + passwordLength);
        }
    }

    public void checkAgeAllowed(User user) {
        if(user.getAge() == null) {
            throw new RegistrationServiceException("The age value can't be null");
        }

        if (user.getAge() < VALID_AGE) {
            throw new RegistrationServiceException(
                    "The age must have at least " + VALID_AGE
                            + " years old, but yours is : "
                            + user.getAge());
        }
    }
}
