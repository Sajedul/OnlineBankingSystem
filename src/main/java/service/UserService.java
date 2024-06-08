package service;

import dao.UserDao;
import model.User;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {

        this.userDao = userDao;
    }
    public boolean registerUser(User user) {
        try {
            return userDao.createUser(user);
        } catch (Exception e) {
            // Handle exception
            return false;
        }
    }

    // Other service methods for view and update
}
