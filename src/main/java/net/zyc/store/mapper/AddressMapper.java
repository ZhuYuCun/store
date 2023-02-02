package net.zyc.store.mapper;

import net.zyc.store.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 收获地址持久层接口
 *
 */
public interface AddressMapper {
    /**
     * 插入用户的收获地址数据
     * @param address 收获地址数据
     * @return 受影响的行数
     */
    Integer insert(Address address);


    /**
     * 根据用户id 查询用户的收获地址条数
     * @param uid 用户的id
     * @return 用户收获地址条数
     */
    Integer countById(Integer uid);


    /**
     * 根据用户id查询用户的收获地址
     * @param uid 用户id
     * @return 用户收获地址列表
     */
    List<Address> findByUid(Integer uid);


    /**
     * 根据收货地址id获取收货地址
     * @param aid
     * @return
     */
    Address findByAid(Integer aid);

    /**
     * 根据用户id设置所有非默认的地址
     * @param uid
     * @return
     */
    Integer updateNoneDefault(Integer uid);

    /**
     * 根据用户id设置默认的地址
     * @param aid
     * @return
     */
    Integer updateDefault(
            Integer aid,
            String modifiedUser,
            Date modifiedTime);

    /**
     * 删除收货地址
     * @param aid
     * @return
     */
    Integer deleteByAid(Integer aid);

    /**
     * 根据用户id查询最后一条地址
     * @param uid
     * @return
     */
    Address findLastModified(Integer uid);
}
