package net.zyc.store.mapper;

import net.zyc.store.entity.Car;
import net.zyc.store.vo.CartVo;

import java.util.Date;
import java.util.List;

public interface CarMapper {
    /**
     * 新增购物车
     * @param car
     * @return
     */
    Integer insert(Car car);

//    修改购物数量
    Integer updateNumByCid(
                        Integer cid,
                        Integer num,
                        String modifiedUser,
                        Date modifiedTime
                         );

    /**
     * 查询购物车商品
     * @param uid
     * @param pid
     * @return
     */
    Car findByUidAndPid(Integer uid, Integer pid);

    List<CartVo> findVoByUid(Integer uid);
}
