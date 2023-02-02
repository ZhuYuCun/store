package net.zyc.store;

import net.zyc.store.entity.User;
import net.zyc.store.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

//单元测试 不会被打包上线
@SpringBootTest
// QRunWith：走示启动这个单元河试关(单元测试类是不能够运行的)，需要传进一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class UserMapperTest {


    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("zhangSan");
        user.setPassword("123456");

        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }


    @Test
    public void select() {
        User user = userMapper.findByUsername("zhangSan");
        System.out.println(user);
    }


    @Test
    public void updateUser() {
        User user = new User();
                user.setUid(7);
                user.setPhone("18584811484");
                user.setEmail("1203407743@qq.com");
                user.setGender(1);
        userMapper.updateInfoByUid(user);
    }


    @Test
    public void updateUserAVatar() {
        userMapper.updateAvatarByUid(7,"/sdf/sdf/sdf.png", "zyc", new Date());
    }
}
