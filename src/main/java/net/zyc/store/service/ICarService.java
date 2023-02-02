package net.zyc.store.service;


import net.zyc.store.vo.CartVo;

import java.util.List;

public interface ICarService {
    /**
     * 商品添加到购物车
     * @param uid
     * @param pid
     * @param num
     * @param username
     */
    void addToCart(Integer uid, Integer pid, Integer num, String username);

    List<CartVo> findVoByUid(Integer uid);

}
