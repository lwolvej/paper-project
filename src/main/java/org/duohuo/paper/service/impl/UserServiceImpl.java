package org.duohuo.paper.service.impl;

import org.apache.shiro.authz.UnauthenticatedException;
import org.duohuo.paper.exceptions.CaptchaException;
import org.duohuo.paper.exceptions.UserException;
import org.duohuo.paper.model.User;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.model.result.UserResult;
import org.duohuo.paper.repository.RedisRepository;
import org.duohuo.paper.repository.UserRepository;
import org.duohuo.paper.service.UserService;
import org.duohuo.paper.utils.JwtUtil;
import org.duohuo.paper.utils.RegexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource(name = "redisRepository")
    private RedisRepository redisRepository;

    @Resource(name = "userRepository")
    private UserRepository userRepository;


    @Override
    public JsonResult login(String userName, String password, String uuid, String code) {
        captchaValidation(uuid, code);
        Optional<User> user = userRepository.findById(userName);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                return doLogin(userName, password);
            } else {
                throw new UnauthenticatedException();
            }
        } else {
            throw new UnauthenticatedException();
        }
    }

    @Override
    public JsonResult register(String userName, String password, String uuid, String code) {
        captchaValidation(uuid, code);
        Optional<User> temp = userRepository.findById(userName);
        if (temp.isPresent()) {
            throw new UserException("用户:" + userName + "已存在!");
        } else {
            if (!RegexUtil.userNameValidation(userName)) {
                throw new UserException("用户名不符合规范");
            }
            if (!RegexUtil.passwordValidation(password)) {
                throw new UserException("密码不符合规范");
            }
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            user.setPermission("view");
            user.setRole("user");
            userRepository.save(user);
            LOGGER.info("{} success register!", userName);
            return doLogin(userName, password);
        }
    }

    private void captchaValidation(String uuid, String code) {
        Object value = redisRepository.get(uuid);
        if (value == null) {
            throw new CaptchaException("验证码过期");
        }
        if (!code.equals(value)) {
            throw new CaptchaException("验证码错误");
        }
    }

    private JsonResult doLogin(String userName, String password) {
        String jwtCode = JwtUtil.sign(userName, password);
        LOGGER.info("User:{} login!", userName);
        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name(),
                new UserResult(jwtCode, 60 * 60 * 1000));
    }
}
