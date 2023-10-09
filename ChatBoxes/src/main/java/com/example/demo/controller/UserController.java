package com.example.demo.controller;

import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("user")
public class UserController {

    /**
     * 登录功能
     * @param user 提交的用户数据，包含用户名和密码
     * @param session
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpSession session) {
        Result result = new Result();
        if(user != null && "123".equals(user.getPassword())) {
            result.setFlag(true);
            //将数据存储到session对象中
            session.setAttribute("user",user.getUsername());
        } else {
            result.setFlag(false);
            result.setMessage("登陆失败");
        }
        return result;
    }

    /**
     * 获取用户名
     * @param session
     * @return
     */
    @GetMapping("/getUsername")
    public String getUsername(HttpSession session) {

        String username = (String) session.getAttribute("user");
        return username;
    }
}
