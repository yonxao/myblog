package net.xiaosaguo.myblog.service.impl;

import net.xiaosaguo.myblog.dao.UserRepository;
import net.xiaosaguo.myblog.pojo.entity.User;
import net.xiaosaguo.myblog.service.UserService;
import net.xiaosaguo.myblog.util.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * description: 用户 ServiceImpl
 *
 * @author xiaosaguo
 * @date 2020/04/25
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public User getByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
    }
}
