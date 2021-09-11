package com.example.demo.dao;

import com.example.demo.entity.Permission;

import java.util.List;

public interface PermissionDao {

    List<Permission> selectPermissionByRoleId(int rid);


}
