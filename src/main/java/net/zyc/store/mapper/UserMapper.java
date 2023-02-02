package net.zyc.store.mapper;

import net.zyc.store.entity.User;

import java.util.Date;


public interface UserMapper {

    /**
     * 新增用户
     * @param user
     * @return
     */
    Integer insert(User user);
    User findByUsername(String userName);

    /**
     * 修改密码
     * @param uid
     * @return
     */
    Integer updatePasswordByUid(Integer uid,
                                String password,
                                String modifiedUser,
                                Date modifiedTime);

    /**
     * 根据用户id查询用户
     * @param uid
     * @return
     */
    User findByUid(Integer uid);

    /**
     * 跟新用户数据
     * @param user 用户数据
     * @return 受影响的行数
     */
    Integer updateInfoByUid(User user);


    /**
     * 修改用户头像
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateAvatarByUid(Integer uid,
                              String avatar,
                              String modifiedUser,
                              Date modifiedTime);
}
