package org.duohuo.paper.service.impl;

import org.apache.shiro.authz.UnauthenticatedException;
import org.duohuo.paper.constants.TimeConstant;
import org.duohuo.paper.manager.UserManager;
import org.duohuo.paper.model.User;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.model.result.UserResult;
import org.duohuo.paper.service.UserService;
import org.duohuo.paper.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource(name = "userManager")
    private UserManager userManager;

    @Override
    public JsonResult login(final String userName, final String password, final String uuid, final String code) {
        userManager.captchaValidation(uuid, code);
        User user = userManager.findUserByUserName(userName);
        if (user.getPassword().equals(password)) {
            return doLogin(userName, password);
        } else {
            throw new UnauthenticatedException("用户密码错误:" + userName);
        }
    }

    @Override
    public JsonResult register(String userName, String password, String uuid, String code) {
        userManager.captchaValidation(uuid, code);
        userManager.save(userName, password);
        LOGGER.info("用户:{}, 成功注册!", userName);
        return doLogin(userName, password);
    }

    private JsonResult doLogin(String userName, String password) {
        String jwtCode = JwtUtil.sign(userName, password);
        LOGGER.info("用户:{} 成功登陆!", userName);
        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name(),
                new UserResult(jwtCode, TimeConstant.TIME_USER_SURVIVAL));
    }
}
