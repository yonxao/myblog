package net.xiaosaguo.blog.service;

import net.xiaosaguo.blog.dao.UserRepository;
import net.xiaosaguo.blog.po.User;
import net.xiaosaguo.blog.util.MD5Utils;
import org.springframework.stereotype.Service;

/**
 * description: 用户相关的接口的实现类
 *
 * @author xiaosaguo
 * @version 1 xiaosaguo 创建
 */
@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User checkUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
    }
}
