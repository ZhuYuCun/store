package net.zyc.store.controller;

import net.zyc.store.controller.ex.*;
import net.zyc.store.entity.User;
import net.zyc.store.service.IUserService;
import net.zyc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(OK);
    }


    @RequestMapping("login")
    public JsonResult<User> login(String username, String password,
                                  HttpSession session) {
        User data = userService.login(username, password);

        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());

        return new JsonResult<User>(OK, data);
    }


    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        userService.changePassword(uid,username,oldPassword, newPassword);

        return new JsonResult<>(OK);
    }


    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        User data = userService.getByUid(uid);

        return new JsonResult<User>(OK, data);
    }


    @RequestMapping("change_info")
    public JsonResult<Void> updateUserInfo(User user,
                                           HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid, username, user);

        return new JsonResult<Void>(OK);
    }

//    ???????????????
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

//    ????????????????????????
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
       AVATAR_TYPE.add("image/jpeg");
       AVATAR_TYPE.add("image/png");
       AVATAR_TYPE.add("image/bmp");
       AVATAR_TYPE.add("image/gif");
    }

    /**
     * MultipartFile SpringMVC????????????????????????????????????????????? ??????????????????
     * @param session
     * @param file
     * @return
     * @RequestParam ???????????????????????????
     */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(MultipartFile file,
                                           HttpSession session) {
        if(file.isEmpty()) {
            throw new FileEmptyException("????????????");
        }

        if(file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("????????????");
        }

        String contentType = file.getContentType();
        if(!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("??????????????????");
        }

//        ??????????????? ../upload/??????.xxx

        String parent = session.getServletContext().getRealPath("upload");

        File dir = new File(parent);

        if(!dir.exists()) {
            dir.mkdirs();
        }

//???????????????
        String originFilename = file.getOriginalFilename();
        System.out.println("----------filename----------");
        System.out.println(originFilename);
        System.out.println("---------   end  ---------");
        int index = originFilename.lastIndexOf(".");
        String suffix = originFilename.substring(index);

        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;

        File dest = new File(dir, filename);
        try {
            file.transferTo(dest);
        } catch (FileStateException e) {
            throw new FileStateException("??????????????????");
        } catch (IOException e) {
            throw new FileUploadIOException("??????????????????");
        }

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        String avatar = "/upload/" + filename;

        userService.changeAvatar(uid, avatar, username);

        return new JsonResult<>(OK, avatar);
    }
}
