package net.xiaosaguo.myblog.service;

import net.xiaosaguo.myblog.pojo.entity.User;

/**
 * description: 用户 Service
 *
 * @author xiaosaguo
 * @date 2020/04/25
 */
public interface UserService {

    /**
     * description: 根据用户名和密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 根据用户名和密码匹配到的 {@code User}
     * @author xiaosaguo
     * @date 2020-04-25 03:22
     */
    User getByUsernameAndPassword(String username, String password);
}
