package org.duohuo.paper.manager;

import org.apache.shiro.authz.UnauthenticatedException;
import org.duohuo.paper.exceptions.CaptchaException;
import org.duohuo.paper.exceptions.UserException;
import org.duohuo.paper.model.User;
import org.duohuo.paper.repository.RedisRepository;
import org.duohuo.paper.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Component("userManager")
public class UserManager {

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "redisRepository")
    private RedisRepository redisRepository;

    public void captchaValidation(String uuid, String code) {
        Object value = redisRepository.get(uuid);
        if (value == null) {
            throw new CaptchaException("验证码过期");
        }
        if (!code.equals(value)) {
            throw new CaptchaException("验证码错误");
        }
    }

    public User findUserByUserName(final String userName) {
        Optional<User> user = userRepository.findById(userName);
        if (!user.isPresent()) {
            throw new UnauthenticatedException("用户不存在:" + userName);
        }
        return user.get();
    }

    public void save(final String userName, final String password) {
        Optional<User> t = userRepository.findById(userName);
        if (t.isPresent()) {
            throw new UserException("用户:" + userName + "已存在!");
        }
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setRole("user");
        user.setPermission("view");
        userRepository.save(user);
    }
}
