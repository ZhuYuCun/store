package net.zyc.store;

import net.zyc.store.entity.Address;
import net.zyc.store.mapper.AddressMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

//单元测试 不会被打包上线
@SpringBootTest
// QRunWith：走示启动这个单元河试关(单元测试类是不能够运行的)，需要传进一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class AddressMapperTest {


    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(7);
        address.setPhone("18584811484");
        address.setName("啦啦啦");

        addressMapper.insert(address);
    }


    @Test
    public void countById() {
        Integer count = addressMapper.countById(7);
        System.out.println(count);
    }

    @Test
    public void getAddressByUid() {
        List<Address> list = addressMapper.findByUid(7);
        System.out.println(list);
    }

    @Test
    public void findByAid() {
        Address data = addressMapper.findByAid(8);
        System.out.println(data);
    }

    @Test
    public void updateNoneDefault() {
        Integer row = addressMapper.updateNoneDefault(7);
        System.out.println(row);
    }

    @Test
    public void updateDefault() {
        Integer row = addressMapper.updateDefault(1, "zyc", new Date());
        System.out.println(row);
    }


    @Test
    public void deleteByAid() {
        addressMapper.deleteByAid(1);
    }

    @Test
    public void findLastModified() {
        Address address = addressMapper.findLastModified(7);
        System.out.println(address);
    }

}
