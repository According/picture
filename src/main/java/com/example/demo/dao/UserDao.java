package com.example.demo.dao;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    User queryById(@Param("uid") Integer uid);

    User queryUserByName(String username);

    List<User> selectAllUser();

}
