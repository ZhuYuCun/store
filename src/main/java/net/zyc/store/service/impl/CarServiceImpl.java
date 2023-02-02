package net.zyc.store.service.impl;

import net.zyc.store.entity.Car;
import net.zyc.store.entity.Product;
import net.zyc.store.mapper.CarMapper;
import net.zyc.store.mapper.ProductMapper;
import net.zyc.store.service.ICarService;
import net.zyc.store.service.ex.InsertException;
import net.zyc.store.service.ex.UpdateException;
import net.zyc.store.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CarServiceImpl implements ICarService {
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer num, String username) {

//        查询是否已经存在
        Car result = carMapper.findByUidAndPid(uid, pid);

        if(result != null) { // 已存在 则更新
            Integer row = carMapper.updateNumByCid(result.getCid(), num, username, new Date());
            if(row != 1) {
                throw new UpdateException("修改购物车异常");
            }
        } else { // 不存在 更新数据
            Car car = new Car();
            car.setPid(pid);
            car.setUid(uid);
            car.setNum(num);
            //  价格来自商品
            Product product = productMapper.findById(pid);
            Long price = product.getPrice();
            car.setPrice(price);
            car.setCreatedTime(new Date());
            car.setModifiedTime(new Date());
            car.setModifiedUser(username);
            car.setCreatedUser(username);
           Integer row = carMapper.insert(car);
           if(row != 1) {
               throw new InsertException("新增购物车异常");
           }
        }
    }

    @Override
    public List<CartVo> findVoByUid(Integer uid) {
        return carMapper.findVoByUid(uid);
    }
}
