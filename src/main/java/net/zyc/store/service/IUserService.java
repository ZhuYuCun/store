package net.zyc.store.service;

import net.zyc.store.entity.User;

public interface IUserService {
    Integer reg(User user);
    User login(String username, String password);

    Integer changePassword( Integer uid,
                            String username,
                            String oldPassword,
                            String newPassword);

    User getByUid(Integer uid);

    void changeInfo(Integer uid, String username,User user);

    /**
     * 修改头像
     * @param uid
     * @param avatar
     * @param username
     */
    void changeAvatar(Integer uid,
                      String avatar,
                      String username);
}
