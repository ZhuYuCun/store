package net.zyc.store.service.impl;

import net.zyc.store.entity.User;
import net.zyc.store.mapper.UserMapper;
import net.zyc.store.service.IUserService;
import net.zyc.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer reg(User user) {
        // 查询用户是否存在 userMapper
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);
        if(result !=null) {
            throw  new UsernameDuplicatedException("用户名被占用");
        }
        // 加密
        String oldPassword = user.getPassword();
        String salt = UUID.randomUUID().toString();

        String newPassword = getMD5Password(oldPassword, salt);

        user.setPassword(newPassword);
        user.setSalt(salt);
        // 补全数据
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        // 不存在直接注册
        Integer rows = userMapper.insert(user);
        if(rows !=1) {
            throw new InsertException("插入失败！");
        }
        return rows;
    }

    @Override
    public User login(String username, String password) {
        User result = userMapper.findByUsername(username);
        if(result == null) {
            throw new UserNotFoundException("用户不存在");
        }

        String salt = result.getSalt();

        String newMd5Password = getMD5Password(password, salt);
        String oldMd5Password = result.getPassword();

        if(!newMd5Password.equals(oldMd5Password)) {
            throw new PasswordNotMatchException("用户名密码错误！");
        }

        if(result.getIsDelete()==1){
            throw new UserNotFoundException("用户不存在");
        }

        User user = new User();

        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        return user;
    }

    @Override
    public Integer changePassword(Integer uid,
                                  String username,
                                  String oldPassword,
                                  String newPassword) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        String oldMd5Password = getMD5Password(oldPassword, result.getSalt());
        if(!result.getPassword().equals(oldMd5Password)) {
            throw new PasswordNotMatchException("密码错误");
        }

//        新密码设置到数据库
        String newMd5Password = getMD5Password(newPassword, result.getSalt());

        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());

        if(rows !=1) {
            throw new UpdateException("更新数据时产生未知异常");
        }
        return rows;
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if(result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("用户不存在");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if(result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("用户不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateInfoByUid(user);

        if(rows != 1){
            throw new UpdateException("更新时产生异常");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);

        if(result==null || result.getIsDelete().equals(1)){
            throw new UserNotFoundException("用户不存在");
        }

        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if(rows != 1){
            throw new UpdateException("更新头像时产生异常");
        }
    }

    private String getMD5Password(String password, String salt) {
        for(int i=0; i<3; i++) {
           password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        return password;
    }

}
