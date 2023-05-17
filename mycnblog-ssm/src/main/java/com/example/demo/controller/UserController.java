package com.example.demo.controller;

import com.example.demo.common.AjaxResult;
import com.example.demo.common.EmailUnit;
import com.example.demo.common.SecurityUnit;
import com.example.demo.common.SessionUnit;
import com.example.demo.model.Constant;
import com.example.demo.model.UserInfo;
import com.example.demo.service.ArticleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

    /**
     * 注册功能
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/reg")
    public Object reg(String username,String password){
        //1.进行非空判断
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)){
            return AjaxResult.fail("-1","非法参数请求");
        }
        //2.进行数据库添加操作
        userService.add(username, SecurityUnit.encrypt(password));//对密码进行加盐处理
        return AjaxResult.success("1","注册成功");
    }

    /**
     *登录功能
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public Object login(String username, String password, HttpServletRequest request){
        //1.进行非空判断
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)){
            return AjaxResult.fail("-1","非法参数请求");
        }
        //2.进行数据库查询操作
        //根据用户名查询到用户对象
        UserInfo userInfo = userService.login(username);
        if(userInfo == null || userInfo.getId() <= 0){
           return AjaxResult.fail("-1","登录失败,用户名或密码错误");
        }else {
            //获取该用户当前数据库的密码
            String finalPassword = userInfo.getPassword();
            if(SecurityUnit.decrypt(password,finalPassword)){ // 解密验证密码是否正确
                //将登录信息保存到 session 中
                SessionUnit.setLoginUser(request,userInfo);
                return AjaxResult.success("1","登录成功");
            }else {
                return AjaxResult.fail("-1","登录失败,用户名或密码错误");
            }
        }
    }

    /**
     * 退出登录功能
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public Object logout(HttpServletRequest request){
        HttpSession  session = request.getSession();
        if(session != null && session.getAttribute(Constant.SESSION_USERINFO_KEY) != null){
            session.removeAttribute(Constant.SESSION_USERINFO_KEY);
            return AjaxResult.success("1","退出登录成功");
        }
        return AjaxResult.fail("-1","退出登录失败");
    }

    /**
     * 获取当前用户信息
     * @param request
     * @return
     */
    @RequestMapping("/myinfo")
    public Object myInfo(HttpServletRequest request){
        UserInfo userInfo = SessionUnit.getLoginUser(request);
        if(userInfo != null) {
            return AjaxResult.success(userInfo, "获取信息成功");
        }
        return AjaxResult.fail("-1","获取信息失败");
    }

    /**
     * 上传用户头像
     * @return
     */
    @RequestMapping("/upload")
    public Object uploadAvatar(MultipartFile file,HttpServletRequest request){
        UserInfo userInfo = SessionUnit.getLoginUser(request); // 获取当前用户对象
        if(userInfo != null){
            /**
             * 将图片存储到指定文件路径
             */
            String fileNameAndType = file.getOriginalFilename(); // 获取文件名及类型
            String path = Constant.AVATAR_FILE_PATH + fileNameAndType; // 获取文件路径
            File dest = new File(path);
            if(!dest.exists()){ // 该文件夹不存在 , 则创建一个
                dest.mkdir();
            }
            try {
                file.transferTo(dest); // 将文件上传到 dest 位置
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            /**
             * 将图片存储到数据库中
             */
            String url = "img/avatar/" + fileNameAndType; // 这里只需要用 img/avatar + fileNameAndType 为路径即可
            int res = userService.uploadAvatar(url,userInfo.getId()); // path 即是图片存储的路径
            if(res == 1){
                return AjaxResult.success(1,"插入成功!");
            }else {
                return AjaxResult.fail(-1,"插入失败!");
            }
        }
        return AjaxResult.fail(-1,"获取不到用户对象!");
    }

    /**
     * 初始化博客详情页面个人信息
     * @param id
     * @return
     */
    @RequestMapping("/mycontentinfo")
    public Object myContentInfo(Integer id){
        Integer uid = articleService.selectUid(id);//通过文章 id 查询 uid
        if(uid != null) {
            UserInfo userInfo = userService.myContentInfo(uid);
            if (userInfo != null) {
                return AjaxResult.success(userInfo, "获取信息成功");
            }
        }
        return AjaxResult.fail("-1","获取信息失败");
    }

    /**
     * 初始化个人中心的个人信息
     */
    @RequestMapping("/center")
    public Object initCenterInfo(HttpServletRequest request){
        UserInfo userInfo = SessionUnit.getLoginUser(request); //获取当前用户对象
        if(userInfo != null){
            return AjaxResult.success(userInfo,"");
        }
        return AjaxResult.fail(-1,"");
    }

    /**
     * 绑定邮箱功能
     * @param request
     * @param email
     * @return
     */
    @RequestMapping("/email")
    public Object bindEmail(HttpServletRequest request,String email){
        UserInfo userInfo = SessionUnit.getLoginUser(request); //获取当前用户对象
        if(userInfo != null){
            if(StringUtils.hasLength(email) && EmailUnit.isEmail(email)){ //非空判断且邮件格式是否正确
                return userService.bindEmail(email,userInfo.getId());
            }else {
                return AjaxResult.fail(-1,"邮件格式错误");
            }
        }
        return AjaxResult.fail(-1,"不存在该用户!");
    }

    /**
     * 编辑昵称功能
     * @param request
     * @param nickname
     * @return
     */
    @RequestMapping("/nickname")
    public Object editNickname(HttpServletRequest request,String nickname){
        UserInfo userInfo = SessionUnit.getLoginUser(request); //获取当前用户对象
        if(userInfo != null){
            if(StringUtils.hasLength(nickname)){ //非空判断
                return userService.editNickname(nickname,userInfo.getId());
            }
        }
        return 0;
    }

    /**
     * 编辑个人简介功能
     * @param request
     * @param introduction
     * @return
     */
    @RequestMapping("/introduction")
    public Object editIntroduction(HttpServletRequest request,String introduction){
        UserInfo userInfo = SessionUnit.getLoginUser(request); //获取当前用户对象
        if(userInfo != null){
            if(StringUtils.hasLength(introduction)){ //非空判断
                return userService.editIntroduction(introduction,userInfo.getId());
            }
        }
        return 0;
    }
}
