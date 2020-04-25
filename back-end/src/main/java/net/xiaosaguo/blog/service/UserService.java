package net.xiaosaguo.blog.service;

import net.xiaosaguo.blog.po.User;

/**
 * description: 用户相关的服务层接口
 *
 * @author xiaosaguo
 * @version 1 xiaosaguo 创建
 */
public interface UserService {

    /**
     * description: 根据用户名和密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return net.xiaosaguo.blog.po.User
     * @author xiaosaguo
     * @date 2020-04-25 03:22
     * @version 1 xiaosaguo 创建
     */
    User checkUser(String username, String password);


}
