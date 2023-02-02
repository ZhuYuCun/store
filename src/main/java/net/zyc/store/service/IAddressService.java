package net.zyc.store.service;

import net.zyc.store.entity.Address;

import java.util.List;

public interface IAddressService {

    void addNewAddress(Integer uid, String username,Address address);

    List<Address> getByUid(Integer uid);

    /**
     *   设置默认地址
     */
    void setDefault(Integer aid, Integer uid, String username);

    /**
     * 删除收货地址
     * @param aid
     * @param uid
     * @param username
     */
    void delete(Integer aid, Integer uid, String username);
}
