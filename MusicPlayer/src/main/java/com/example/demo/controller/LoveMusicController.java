package com.example.demo.controller;

import com.example.demo.common.AjaxResult;
import com.example.demo.common.SessionUtils;
import com.example.demo.model.Music;
import com.example.demo.model.User;
import com.example.demo.service.LoveMusicService;
import com.example.demo.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/love")
public class LoveMusicController {
    @Autowired
    LoveMusicService loveMusicService;
    @Autowired
    MusicService musicService;
    @Autowired
    SessionUtils sessionUtils;
    @Autowired
    AjaxResult ajaxResult;

    /**
     * 收藏音乐
     * 失败返回 0
     * 成功返回 1
     * @param music_id
     * @return
     */
    @RequestMapping("/like")
    public Object like(HttpServletRequest request,int music_id){
        User user = sessionUtils.getLoginUser(request); // 获取当前用户对象
        if(user == null){
            return ajaxResult.fail("当前用户未登录",0);
        }
        Music music = loveMusicService.findLoveMusic(user.getId(),music_id);
        if(music == null){
            int res = loveMusicService.insertLoveMusic(user.getId(),music_id);
            if(res == 1){
                return res;
            }else {
                return ajaxResult.fail("数据库插入失败!!",0);
            }
        }else {
            return ajaxResult.fail("喜欢列表中已经有该音乐!!",0);
        }
    }

    /**
     * 查询喜欢列表所有音乐
     * 成功data返回音乐列表
     * 失败data返回0
     * @return
     */
    @RequestMapping("/findall")
    public Object findAll(HttpServletRequest request){
        User user = sessionUtils.getLoginUser(request);
        List<Music> res = loveMusicService.findLoveMusicByUserId(user.getId()); // 获取当前用户喜欢音乐列表的音乐
        if(res != null){
            return res;
        }
        return 0;
    }

    /**
     * 根据ID删除喜欢列表的音乐
     * 成功返回 1
     * 失败返回 0
     * @param music_id
     * @return
     */
    @RequestMapping("/delete")
    public Object delete(HttpServletRequest request,int music_id){
        User user = sessionUtils.getLoginUser(request);
        int res = loveMusicService.deleteLoveMusic(music_id,user.getId());
        if(res == 1){
            return 1;
        }else {
            return 0;
        }
    }

    /**
     * 删除喜欢列表中选中的音乐
     * 删除成功状态码为200,data为1
     * 删除失败状态码为-1,data为删除的记录条数
     * @param music_ids
     * @return
     */
    @RequestMapping("/deleteSel")
    public Object deleteSel(HttpServletRequest request,@RequestParam("music_ids[]") List<Integer> music_ids){
        User user = sessionUtils.getLoginUser(request);
        int record = 0; // 记录删除喜欢列表音乐的个数
        for (int i = 0; i < music_ids.size(); i++) {
            int res = loveMusicService.deleteLoveMusic(music_ids.get(i), user.getId()); // 删除成功返回 1,否则返回 0
            record += res;
        }
        if(record == music_ids.size()){
            return 1;
        }else {
            return ajaxResult.fail("某些音乐删除失败","删除音乐个数:" + record);
        }
    }

    /**
     * title不为空进行模糊查询
     * 为空默认查询所有元素
     * @param request
     * @param title
     * @return
     */
    @RequestMapping("/search")
    public Object search(HttpServletRequest request,@RequestParam(required = false) String title){
        User user = sessionUtils.getLoginUser(request);
        List<Music> res = null;
        if(title == ""){
            res = loveMusicService.findLoveMusicByUserId(user.getId());
        }else {
            res = loveMusicService.findLoveMusicByTitleAndUserId(title,user.getId());
        }
        return res;
    }
}
