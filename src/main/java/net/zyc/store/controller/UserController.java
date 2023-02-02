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

//    文件最大值
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

//    限制上传文件类型
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
       AVATAR_TYPE.add("image/jpeg");
       AVATAR_TYPE.add("image/png");
       AVATAR_TYPE.add("image/bmp");
       AVATAR_TYPE.add("image/gif");
    }

    /**
     * MultipartFile SpringMVC为我们包装了获取文件类型的数据 任何文件类型
     * @param session
     * @param file
     * @return
     * @RequestParam 名称不一致时候使用
     */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(MultipartFile file,
                                           HttpSession session) {
        if(file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }

        if(file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件过大");
        }

        String contentType = file.getContentType();
        if(!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型错误");
        }

//        上传的文件 ../upload/文件.xxx

        String parent = session.getServletContext().getRealPath("upload");

        File dir = new File(parent);

        if(!dir.exists()) {
            dir.mkdirs();
        }

//获取文件名
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
            throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        String avatar = "/upload/" + filename;

        userService.changeAvatar(uid, avatar, username);

        return new JsonResult<>(OK, avatar);
    }
}
