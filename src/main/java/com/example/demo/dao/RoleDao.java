package com.example.demo.dao;

import com.example.demo.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao {

    List<Role> selectRoleByUserId(@Param("username") String username);
}
