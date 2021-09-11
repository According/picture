package com.example.demo.controller;

import cn.hutool.core.map.MapUtil;
import com.example.demo.dto.AccountDto;
import com.example.demo.entity.User;
import com.example.demo.exception.LidarException;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtils;
import com.example.demo.util.Msg;
import com.example.demo.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class AccountController {

    @Autowired
    private UserService userService;
    
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@RequestBody AccountDto dto, HttpServletResponse response) {
        log.info("login dto {}",dto);
        User mUser = userService.queryUserByName(dto.getUsername());
        System.out.println(mUser);
        if(mUser == null) {
            throw new LidarException(Msg.E_102);
        }
        if(!mUser.getPassword().equals(dto.getPassword())){
            throw new LidarException(Msg.E_101);
        }

        //生成token
        String jwt = jwtUtils.generateToken(mUser.getUid());
        System.out.println(jwt);
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        Map<Object, Object> map = MapUtil.builder()
                .put("id", mUser.getUid())
                .put("username", mUser.getUsername())
                .map();
        System.out.println(map);
        return Result.ok().put("data",map);
    }


    @GetMapping("/logout")
    @RequiresAuthentication
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.ok();
    }

    @RequiresPermissions("delete")
    @GetMapping("/user")
    public Result admin() {
        List<User> users = userService.selectAllUser();
        return Result.ok().putData(users);
    }

}
