package com.panda.family.service;

import com.panda.family.dao.UserDao;
import com.panda.family.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User queryUserByUserName(String userName) {
        User user = userDao.queryUserByUserName(userName);
        return user;
    }

    public User insertUser(User user) {
        long userId = userDao.insertUser(user);
        user.setId(userId);
        return user;
    }
}
