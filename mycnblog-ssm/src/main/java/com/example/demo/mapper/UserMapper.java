package com.example.demo.mapper;
import com.example.demo.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * UserMapper 的参数顺序要和 UserService 中一样要不然不生效
 */
@Mapper
public interface UserMapper {
    /**
     * 添加一个新用户
     * @param username
     * @param password
     * @return
     */
    public int add(@Param("username") String username,@Param("password") String password);

    /**
     * 根据用户名查询用户(登录功能)
     * @param username
     * @return
     */
    public UserInfo login(@Param("username") String username);

    /**
     * 根据用户 id 查询用户(初始化用户文章功能)
     * @param uid
     * @return
     */
    public UserInfo myContentInfo(@Param("uid") Integer uid);

    /**
     * 通过用户 id 查询用户名
     * @param id
     * @return
     */
    public String selectUsername(@Param("id") Integer id);

    /**
     * 通过用户名查询邮箱
     * @param username
     * @return
     */
    public String selectEmail(@Param("username") String username);

    /**
     * 重置密码功能
     * @param username
     * @param password
     * @return
     */
    public int resetPassword(@Param("username") String username,@Param("password") String password);

    /**
     * 根据当前用户ID绑定邮箱
     * @param email
     * @param id
     * @return
     */
    public int bindEmail(@Param("email") String email,@Param("id") Integer id);

    /**
     * 根据用户ID修改昵称
     * @param nickname
     * @param id
     * @return
     */
    public int editNickname(@Param("nickname") String nickname,@Param("id") Integer id);

    /**
     * 根据用户ID修改个人简介
     * @param introduction
     * @param id
     * @return
     */
    public int editIntroduction(@Param("introduction") String introduction,@Param("id") Integer id);
}
