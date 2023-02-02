package net.zyc.store;

import net.zyc.store.entity.Car;
import net.zyc.store.entity.User;
import net.zyc.store.mapper.CarMapper;
import net.zyc.store.mapper.UserMapper;
import net.zyc.store.vo.CartVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CarMapperTest {


    @Autowired
    private CarMapper carMapper;

    @Test
    public void insert() {
        Car car = new Car();
        car.setUid(7);
        car.setPid(10000003);
        car.setPrice(666L);
        car.setNum(10);
        car.setModifiedTime(new Date());
        car.setModifiedUser("zyc");

        Integer rows = carMapper.insert(car);
        System.out.println(rows);
    }


    @Test
    public void select() {
        Car car = carMapper.findByUidAndPid(7,10000003);
        System.out.println(car);
    }


    @Test
    public void updateUser() {
        Car car = new Car();
        car.setUid(7);
        car.setPid(10000003);
        carMapper.updateNumByCid(1, 12, "zyc", new Date());
    }

    @Test
    public void findVoById() {
        List<CartVo> cartVo = carMapper.findVoByUid(7);
        System.out.println(cartVo);
    }
}
