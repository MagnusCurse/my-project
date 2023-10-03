package com.hmdp.controller;


import cn.hutool.core.bean.BeanUtil;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import com.hmdp.entity.UserInfo;
import com.hmdp.service.IUserInfoService;
import com.hmdp.service.IUserService;
import com.hmdp.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private IUserInfoService userInfoService;

    /**
     * 发送手机验证码
     */
    @PostMapping("/code")
    public Result sendCode(@RequestParam("phone") String phone, HttpSession session) {
        // TODO 发送短信验证码并保存验证码
        return userService.sendCode(phone,session);
    }

    /**
     * 登录功能
     * @param loginForm 登录参数，包含手机号、验证码；或者手机号、密码
     */
    @PostMapping("/phone-login")
    public Result login(@RequestBody LoginFormDTO loginForm, HttpSession session){
        // TODO 实现登录功能
        return userService.phoneLogin(loginForm,session);
    }

    /**
     * 登出功能
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request){
        // TODO 实现登出功能
        return userService.logout(request);
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    @GetMapping("/me")
    public Result me(){
        // TODO 获取当前登录的用户并返回
        return Result.ok(UserHolder.getUser());
    }

    /**
     * 根据 id 查询用户信息
     * @param id
     */
    @GetMapping("/{id}")
    public Result queryUserById(@PathVariable("id") Long id) {
        // TODO 从数据库中获取用户信息
        User user = userService.getById(id);
        if(user == null) {
            return Result.fail("查询结果为空");
        }
        // TODO 包装成 UserDTO 类型返回
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        return Result.ok(userDTO);
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long userId){
        // 查询详情
        UserInfo info = userInfoService.getById(userId);
        if (info == null) {
            // 没有详情,应该是第一次查看详情
            return Result.ok();
        }
        info.setCreateTime(null);
        info.setUpdateTime(null);
        // 返回
        return Result.ok(info);
    }

    /**
     * 更新用户昵称
     * @param id
     * @param nickname
     * @return
     */
    @GetMapping("/info/nickname/{id}")
    public Result modifyNickname(@PathVariable("id") Long id, String nickname) {
        boolean isSuccess = userService.update().set("nick_name",nickname).eq("id",id).update();
        if(isSuccess) {
            return Result.ok();
        } else {
            return Result.fail("修改昵称失败");
        }
    }

    /**
     * 签到功能
     * @return
     */
    @PostMapping("/sign")
    public Result sign() {
       return userService.sign();
    }

    @GetMapping("/sign/count")
    public Result signCount() {
        return userService.signCount();
    }
}
