package com.example.demo.controller;

import com.example.demo.common.AjaxResult;
import com.example.demo.common.SessionUtils;
import com.example.demo.model.Constant;
import com.example.demo.model.Music;
import com.example.demo.model.User;
import com.example.demo.service.LoveMusicService;
import com.example.demo.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/music")
public class MusicController {
    @Autowired
    AjaxResult ajaxResult;
    @Autowired
    SessionUtils sessionUtils;
    @Autowired
    MusicService musicService;
    @Autowired
    LoveMusicService loveMusicService;

    /**
     * 将文件上传到指定路径,并将其添加到数据库中
     * 成功返回 1
     * 失败返回 0
     */
    @RequestMapping("/upload")
    public Object upload(HttpServletRequest request, HttpServletResponse response,MultipartFile file, String singer) throws IOException {
        // MutipartFile 主要实现以表单形式上传文件的内容
        /**
         * 将文件上传到指定路径
         */
        String fileNameAndType = file.getOriginalFilename(); // xxx.mp3,获取文件名和文件类型

        /**
         * 检查该歌曲是否已经上传
         */
        int index = fileNameAndType.lastIndexOf('.');
        String title = fileNameAndType.substring(0,index); // 获取歌曲名 title
        Music music = musicService.selectByTitleAndSinger(title,singer); // 检查数据库中是否已经有相同的歌名与歌手名
        //如果歌名和歌手名相同,证明是同一首音乐,不上传
        if(music != null){
            return ajaxResult.fail("Music has been uploaded!!",-1);
        }

        String path = Constant.MUSIC_FILE_PATH + fileNameAndType; // 获得文件路径
        File dest = new File(path);
        if(!dest.exists()){
            dest.mkdir();
        }
        try {
            file.transferTo(dest); // 将文件上传到 dest 位置
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

        /**
         * 将音乐存储到数据库中
         */
        String type = fileNameAndType.substring(index + 1); // 获取类型,如.mp3

        User user = sessionUtils.getLoginUser(request);
        int user_id = user.getId(); // 获取 user_id

        String url = "/music/get?path=" + title + "." + type; // 这个 url 用来在播放音乐函数中向 get 方法发送请求得到音乐文件字节数组

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String uploadtime = sdf.format(new Date()); // 获取当前时间

        response.sendRedirect("http://localhost:8080/list.html"); // 实现页面跳转:记得要写完整路径,要不然容易出错
//      response.sendRedirect("http://43.139.61.124:8084/list.html"); // 打包的时候记得修改,这里是要跳转到云服务器的 list.html
        int res = musicService.insert(title,singer,uploadtime,url,user_id); // 插入成功返回 1,否则 0
        return res;
    }

    /**
     * 播放音乐的时候:发送请求到这,返回音乐文件字节数组 ( 前端播放音乐函数的 url 会向这发送请求 )
     * @param path
     * @return
     */
    @RequestMapping("/get")
    public ResponseEntity<byte[]> get(String path){
        File file = new File(Constant.MUSIC_FILE_PATH + path);
        try {
            byte[] arr = Files.readAllBytes(file.toPath()); // 获取当前路径下的音乐文件
            if(arr == null){ // 为空证明没有该音乐
                return ResponseEntity.badRequest().build();
            }
            // 无参数 ok 返回 ok 状态,有参数返回 body 和 ok 状态
            return ResponseEntity.ok(arr); // 成功返回音乐文件的字节数组
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 根据音乐id从数据库中删除单个音乐,再移除服务器中的音乐
     * 成功data返回1
     * 失败data返回0
     */
    @RequestMapping("/delete")
    public Object delete(HttpServletRequest request,int id){
        User user = sessionUtils.getLoginUser(request);
        Music music = musicService.selectById(id);
        if(music != null){
            //从数据库中删除音乐
            int res = musicService.deleteById(id);
            if(res == 1){
                //从服务器中删除音乐
                String url = music.getUrl();
                int index = url.lastIndexOf('=');
                File file = new File(Constant.MUSIC_FILE_PATH + url.substring(index + 1)); // 获取文件路径
                System.out.println("Current path:" + file.getPath());
                if(file.delete()){ // 删除成功
                    loveMusicService.deleteLoveMusicById(music.getId()); // 同步删除喜欢列表中的该音乐
                    return 1;
                }else { // 删除失败
                    return ajaxResult.fail("从服务器中删除音乐失败!!",0);
                }
            }else {
                return ajaxResult.fail("从数据库中删除失败!!",0);
            }
        }else {
            return ajaxResult.fail("数据库中没有该音乐",0);
        }
    }

    /**
     * 删除所有选中的歌曲
     * 删除成功状态码为200,data为1
     * 删除失败状态码为-1,data为删除的记录条数或者0
     * @RequestParam("ids[]"):参数前要加这个不然会报错
     * @param ids
     * @return
     */
    @RequestMapping("/deleteSel")
    public Object deleteSel(@RequestParam("ids[]") List<Integer> ids){
        int record = 0; // 记录以及删除音乐的个数
        for (int i = 0; i < ids.size(); i++) {
            Music music = musicService.selectById(ids.get(i));
            if(music != null){
                //从数据库中删除音乐
                int res = musicService.deleteById(ids.get(i));
                if(res == 1){
                    //从服务器中删除音乐
                    String url = music.getUrl();
                    int index = url.lastIndexOf('=');
                    File file = new File(Constant.MUSIC_FILE_PATH + url.substring(index + 1)); // 获取文件路径
                    System.out.println("Current path:" + file.getPath());
                    if(file.delete()){ // 删除成功
                        record += res; // 记录删除音乐的个数
                        loveMusicService.deleteLoveMusicById(music.getId()); // 同步删除喜欢列表中的音乐
                    }else { // 删除失败
                        return ajaxResult.fail("从服务器中删除音乐失败!!",0);
                    }
                }else {
                    return ajaxResult.fail("从数据库中删除失败!!",0);
                }
            }else {
                return ajaxResult.fail("数据库中没有该音乐",0);
            }
        }
        if(record == ids.size()){
            return 1;
        }else {
            return ajaxResult.fail("某些音乐删除失败","删除音乐个数:" + record);
        }
    }

    /**
     * 查询所有音乐
     * 成功data返回音乐列表
     * 失败data返回0
     * @return
     */
    @RequestMapping("/findall")
    public Object findAll(Integer pindex,Integer psize){
        if(pindex != null && psize != null && pindex > 0 && psize > 0){
            int offset = (pindex - 1) * psize; // 计算偏移值
            List<Music> res = musicService.selectAll(psize,offset);
            if(res != null){
                return res;
            }
        }
        return 0;
    }

    /**
     * 得到总页数:通过每页大小和音乐总数
     * @param psize
     * @return
     */
    @RequestMapping("/total")
    public Object getTotalPage(Integer psize){
        if(psize != null && psize > 0){
            return musicService.totalPage(psize);
        }
        return ajaxResult.fail("每页音乐数不可以为零或为空",-1);
    }

    /**
     * 查询音乐
     * title不为空进行模糊查询
     * 为空默认查询所有元素
     * @RequestParam(required = false):可以传递 null 参数
     * @param title
     * @return
     */
    @RequestMapping("/search")
    public Object search(@RequestParam(required = false) String title,Integer pindex,Integer psize){
        List<Music> res = null;
        if(title != ""){ // 这里如果没有填搜索结果的话是""而不是null
            res = musicService.selectByTitle(title);
        }else {
            int offset = (pindex - 1) * psize; // 计算偏移值
            res = musicService.selectAll(psize,offset);
        }
        return res;
    }
}
