package com.example.demo.controller;

import com.example.demo.common.AjaxResult;
import com.example.demo.common.SecurityUnit;
import com.example.demo.common.SessionUnit;
import com.example.demo.model.Constant;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;
    @RequestMapping("/login")
    @ResponseBody
    public Object login(HttpServletRequest request,String username, String password){
       // 进行非空判断
       if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)) {
           return AjaxResult.fail(-1,"用户名或者密码为空");
       }
       // 获取到当前用户对象
       User user = service.login(username);
       // 对该用户对象进行非空判断
       if(user == null) {
           return AjaxResult.fail(-1,"找不到该用户对象");
       }else {
           if(SecurityUnit.decrypt(password,user.getPassword())){
               // 将用户对象保存到 session 中
               SessionUnit.setLoginUser(request,user);
               return AjaxResult.success(1,"登录成功");
           }else {
               return AjaxResult.fail(-2,"登录失败,密码错误");
           }
       }
    }

    @RequestMapping("/reg")
    @ResponseBody
    public Object reg(String username,String password) {
        // 进行非空判断
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)){
            return AjaxResult.fail(-1,"用户名或者密码为空");
        }
        // 进行数据库添加操作
        int res = service.reg(username,SecurityUnit.encrypt(password));
        return AjaxResult.success(res,"注册成功");
    }

    @RequestMapping("/upload-avatar")
    @ResponseBody
    public Object uploadAvatar(MultipartFile file,HttpServletRequest request) {
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户对象为空");
        }
        // 将上传的头像存储到指定文件路径
        // 获取文件全名
        String fileNameAndType = file.getOriginalFilename();
        // 凭借得到头像文件存放路径
        String path = Constant.AVATAR_FILE_PATH + fileNameAndType;
        // 创建了一个 File 对象，代表了文件系统中的一个文件。
        // 这个对象会使用之前生成的完整路径作为参数来初始化
        File destination = new File(path);
        // 将文件上传到 destination 位置
        try {
            file.transferTo(destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 将图片 url 存储到数据库中
        String url = fileNameAndType;


        int res = service.uploadAvatar(url, curUser.getId());
        if(res != 1) {
            return AjaxResult.fail(-1,"数据库插入失败");
        }
        return AjaxResult.success(res,"更新头像成功");
    }

    @RequestMapping("/init-avatar")
    @ResponseBody
    public Object initAvatar(HttpServletRequest request) {
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户对象为空");
        }
        String res = service.initAvatar(curUser.getId());
        if(res == null) {
            return AjaxResult.fail(-1,"数据库查询结果为空");
        }
        return AjaxResult.success(res,"初始化用户头像成功");
    }

    @RequestMapping("inti-user-info")
    @ResponseBody
    public Object initUserInfo(HttpServletRequest request) {
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户对象为空");
        }
        User res = service.initUserInfo(curUser.getId());
        if(res == null) {
            return AjaxResult.fail(-1,"数据库查询结果为空");
        }
        return AjaxResult.success(res,"初始化用户信息为空");
    }

    @RequestMapping("change-nickname")
    @ResponseBody
    public Object changeNickname(HttpServletRequest request,String nickname) {
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户对象为空");
        }
        int res = service.changeNickname(curUser.getId(),nickname);
        if(res != 1) {
            return AjaxResult.fail(-1,"数据库查询结果为空");
        }
        return AjaxResult.success(res,"更改昵称成功");
    }

    @RequestMapping("change-introduction")
    @ResponseBody
    public Object changeIntroduction(HttpServletRequest request,String introduction) {
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户对象为空");
        }
        int res = service.changeIntroduction(curUser.getId(),introduction);
        if(res != 1) {
            return AjaxResult.fail(-1,"数据库查询结果为空");
        }
        return AjaxResult.success(res,"更改简介成功");
    }
}
