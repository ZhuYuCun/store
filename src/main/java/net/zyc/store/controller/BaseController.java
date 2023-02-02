package net.zyc.store.controller;

import net.zyc.store.controller.ex.FileEmptyException;
import net.zyc.store.controller.ex.FileStateException;
import net.zyc.store.controller.ex.FileTypeException;
import net.zyc.store.controller.ex.FileUploadException;
import net.zyc.store.service.ex.*;
import net.zyc.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {
    public static final int OK = 200;

    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>();

        if(e instanceof UsernameDuplicatedException) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("注册时产生未知异常");
        } else if (e instanceof DeleteException) {
            result.setState(5001);
            result.setMessage("删除数据时候产生未知异常");
        } else if (e instanceof UserNotFoundException) {
            result.setState(4001);
            result.setMessage("用户名不存在");
        } else if (e instanceof ProductNotFoundException) {
            result.setState(5003);
            result.setMessage("用户名不存在");
        }
         if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
            result.setMessage("密码错误");
        } else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
            result.setMessage("用户收获地址超出上限");
        } else if (e instanceof AddressNotFoundException) {
            result.setState(4004);
            result.setMessage("数据不存在");
        } else if (e instanceof AccessDeniedException) {
            result.setState(4005);
            result.setMessage("收货地址非法访问");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileTypeException) {
            result.setState(6001);
        } else if (e instanceof FileStateException) {
            result.setState(6002);
        } else if (e instanceof FileUploadException) {
            result.setState(6003);
        }
        return result;
    }

    protected final  Integer getUidFromSession(HttpSession session) {
       return Integer.valueOf(session.getAttribute("uid").toString());
    }

    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
