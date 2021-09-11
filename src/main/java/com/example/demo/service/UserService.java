package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;

public interface UserService {

    User queryById(Integer uid);

    User queryUserByName(String username);

    List<User> selectAllUser();

}
