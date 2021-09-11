package com.example.demo.shiro;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.demo.dao.PermissionDao;
import com.example.demo.dao.RoleDao;
import com.example.demo.dto.AccountFileDto;
import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.LidarException;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserService mUserService;

    @Resource
    private RoleDao roleDao;

    @Resource
    private PermissionDao permissionDao;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 此方法获取用户的权限信息，并返回
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AccountFileDto user = (AccountFileDto) principalCollection.getPrimaryPrincipal();
        log.info("doGetAuthorizationInfo user {}",user);
        List<String> permissionList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        List<Role> roles = roleDao.selectRoleByUserId(user.getUsername());
        log.info("doGetAuthorizationInfo roles {}",roles);
        if (CollectionUtils.isNotEmpty(roles)) {
            for(Role role : roles) {
                roleNameList.add(role.getRanme());
                List<Permission> permissions = permissionDao.selectPermissionByRoleId(role.getRid());
                log.info("doGetAuthorizationInfo permission {}",permissions);
                if (CollectionUtils.isNotEmpty(permissions)) {
                    for (Permission permission : permissions) {
                        permissionList.add(permission.getPername());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionList);
        info.addRoles(roleNameList);
        log.info("doGetAuthorizationInfo info {}",info);
        return info;
    }

    /**
     * 此方法是验证用户登录的信息，并返回
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        JwtToken token = (JwtToken) authenticationToken;
        String userid = jwtUtil.getClaimByToken((String) token.getPrincipal()).getSubject();

        User mUser = mUserService.queryById(Integer.valueOf(userid));
        if(mUser == null) {
            throw new LidarException("用户不存在");
        }
        AccountFileDto dto = new AccountFileDto();
        BeanUtils.copyProperties(mUser,dto);
        dto.setId(mUser.getUid());
        return new SimpleAuthenticationInfo(dto,token.getCredentials(),getName());
    }
}
