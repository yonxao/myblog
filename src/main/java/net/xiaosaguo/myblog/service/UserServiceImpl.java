package net.xiaosaguo.myblog.service;

import net.xiaosaguo.myblog.dao.UserRepository;
import net.xiaosaguo.myblog.po.User;
import net.xiaosaguo.myblog.util.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * description: 用户相关的接口的实现类
 *
 * @author xiaosaguo
 * @version 1 xiaosaguo 创建
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
    }
}
