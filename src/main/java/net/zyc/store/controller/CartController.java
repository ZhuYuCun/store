package net.zyc.store.controller;

import net.zyc.store.service.ICarService;
import net.zyc.store.util.JsonResult;
import net.zyc.store.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("cart")
@RestController
public class CartController extends BaseController {

    @Autowired
    private ICarService carService;


    @RequestMapping("add_to_cart")
    public JsonResult<Void> add(Integer pid,
                                Integer num,
                                HttpSession session) {

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        carService.addToCart(uid, pid, num, username);
        return new JsonResult<>(OK);
    }

    @RequestMapping({"", "/"})
    public JsonResult<List<CartVo>> cart(HttpSession session) {
        List<CartVo> list = carService.findVoByUid(getUidFromSession(session));
        return new JsonResult<>(OK, list);
    }

}
