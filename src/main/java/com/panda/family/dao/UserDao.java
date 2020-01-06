package com.panda.family.dao;

import com.panda.family.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {

    long insertUser(User user);

    User queryUserById(@Param("id") long id);

    User queryUserByUserName(@Param("userName") String userName);
}
