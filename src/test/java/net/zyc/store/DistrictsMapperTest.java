package net.zyc.store;

import net.zyc.store.entity.Address;
import net.zyc.store.entity.District;
import net.zyc.store.mapper.AddressMapper;
import net.zyc.store.mapper.DistrictMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//单元测试 不会被打包上线
@SpringBootTest
// QRunWith：走示启动这个单元河试关(单元测试类是不能够运行的)，需要传进一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class DistrictsMapperTest {

    @Autowired
    private DistrictMapper districtMapper;


    @Test
    public void findById() {
        List<District> districts = districtMapper.findByParent("231000");
        System.out.println(districts);
    }


    @Test
    public void findNameByCode() {
        System.out.println(districtMapper.findNameByCode("110102"));
    }

}
