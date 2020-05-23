package net.xiaosaguo.blog.dao;

import net.xiaosaguo.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * description: 用户DAO
 *
 * @author xiaosaguo
 * @version 1 xiaosaguo 创建
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * description: 根据用户名和密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return net.xiaosaguo.blog.po.User
     * @author xiaosaguo
     * @date 2020-04-25 04:16
     * @version 1 xiaosaguo 创建
     */
    User findByUsernameAndPassword(String username, String password);
}
