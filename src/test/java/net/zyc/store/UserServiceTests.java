
package net.zyc.store;

import net.zyc.store.entity.User;
import net.zyc.store.mapper.UserMapper;
import net.zyc.store.service.impl.UserServiceImpl;
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
    public class UserServiceTests {


        @Autowired
        private UserServiceImpl userService;
        @Autowired
        private UserMapper userMapper;

        @Test
        public void reg() {
            try {
                User user = new User();
                user.setUsername("wangwu");
                user.setPassword("654321");
                Integer rows = userService.reg(user);
                System.out.println(rows);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
                System.out.println(e.getMessage());
            }
        }

        @Test
        public void login() {

            User user = userService.login("朱玉存", "521716");
            System.out.println(user);
        }


        @Test
        public void updatePasswordByUid() {
//            userMapper.updatePasswordByUid(
//                    6, "321", "管理员", new Date());
            userService.changePassword(7, "管理员", "521716", "654321");
        }


        @Test
        public void findByUid() {
            User user = userMapper.findByUid(7);
            System.out.println(user);
        }


        @Test
        public void getById() {
            User user = userService.getByUid(7);
            System.out.println(user);
        }

        @Test
        public void changeInfo() {
            User user = new User();
            user.setPhone("123456789");
            user.setEmail("123456789@qq.com");
            user.setGender(0);

            userService.changeInfo(7, "朱玉存", user);
        }

    @Test
    public void changeAvatar() {
        userService.changeAvatar(7, "/a/a/d.png","朱玉存");
    }
}
