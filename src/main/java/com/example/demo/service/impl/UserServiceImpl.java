package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User queryById(Integer uid) {


        return userDao.queryById(uid);
    }

    @Override
    public User queryUserByName(String username) {

        return userDao.queryUserByName(username);
    }

    @Override
    public List<User> selectAllUser() {
        List<User> users = userDao.selectAllUser();
        return users;
    }


}
