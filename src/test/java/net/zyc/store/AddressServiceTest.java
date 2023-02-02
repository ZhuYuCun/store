package net.zyc.store;

import net.zyc.store.entity.Address;
import net.zyc.store.mapper.AddressMapper;
import net.zyc.store.service.IAddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//单元测试 不会被打包上线
@SpringBootTest
// QRunWith：走示启动这个单元河试关(单元测试类是不能够运行的)，需要传进一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class AddressServiceTest {


    @Autowired
    private IAddressService addressService;

    @Test
    public void insert() {
        Address address = new Address();
        address.setPhone("1858489999");
        address.setName("收货人1");

        addressService.addNewAddress(7, "测试", address);
    }


    @Test
    public void setDefault() {
        addressService.setDefault(8, 7, "测试修改1");
    }


    @Test
    public void delete() {
        addressService.delete(3, 7, "测试删除1");
    }
}
