package net.xiaosaguo.myblog.dao;

import net.xiaosaguo.myblog.pojo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * description: 用户 DAO
 *
 * @author xiaosaguo
 * @date 2020/04/25
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * description: 根据用户名和密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 根据用户名和密码匹配到的 {@code User}
     * @author xiaosaguo
     * @date 2020-04-25 04:16
     */
    User findByUsernameAndPassword(String username, String password);
}
