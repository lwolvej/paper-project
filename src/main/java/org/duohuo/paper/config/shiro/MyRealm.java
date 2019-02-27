package org.duohuo.paper.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.duohuo.paper.config.jwt.JwtToken;
import org.duohuo.paper.model.User;
import org.duohuo.paper.repository.UserRepository;
import org.duohuo.paper.utils.JwtUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author lwolvej
 */
@Component
public class MyRealm extends AuthorizingRealm {

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    /**
     * 必须重写此方法，否则shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JwtUtil.getUsername(principals.toString());
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.addRole(user.get().getRole());
            Set<String> permission = new HashSet<>(Arrays.asList(user.get().getPermission().split(",")));
            simpleAuthorizationInfo.addStringPermissions(permission);
            return simpleAuthorizationInfo;
        } else {
            throw new UnauthenticatedException("用户不存在!");
        }
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("Token错误!");
        }
        Optional<User> userBean = userRepository.findById(username);
        if (!userBean.isPresent()) {
            throw new AuthenticationException("用户不存在!");
        }
        if (!JwtUtil.verify(token, username, userBean.get().getPassword())) {
            throw new AuthenticationException("用户名或密码错误!");
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}

