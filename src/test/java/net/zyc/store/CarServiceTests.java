
package net.zyc.store;

import net.zyc.store.mapper.UserMapper;
import net.zyc.store.service.ICarService;
import net.zyc.store.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

    @SpringBootTest
    @RunWith(SpringRunner.class)
    public class CarServiceTests {
        @Autowired
        private ICarService carService;

        @Test
        public void add() {
            carService.addToCart(7, 10000016, 99, "dsfdasd");
        }

}
