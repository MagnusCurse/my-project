package com.example.demo.controller;

import com.example.demo.common.AjaxResult;
import com.example.demo.common.SessionUnit;
import com.example.demo.model.*;
import com.example.demo.service.ArticleService;
import com.example.demo.service.CommentService;
import com.example.demo.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/article")
public class ArticleController{
   @Autowired
   private ArticleService articleService;
   @Autowired
   private UserService userService;
   @Autowired
   private CommentService commentService;

   /**
    * 初始化列表功能
    * @param request
    * @return
    */
   @RequestMapping("/mylist")
   public List<Articleinfo> myList(HttpServletRequest request){
         UserInfo userInfo = SessionUnit.getLoginUser(request);
         if(userInfo != null){
            return articleService.myList(userInfo.getId());//通过用户对象得到用户id
         }
      return null;
   }

   /**
    * 根据文章 id 初始化文章详情页功能
    * @param id
    * @return
    */
   @RequestMapping("/detail")
   public Articleinfo myDetail(Integer id){
      return articleService.myDetail(id);
   }

   /**
    * 根据文章 id 删除文章功能
    * @param id
    * @return
    */
   @RequestMapping("/delete")
   public Object myDelete(Integer id){
      int res = articleService.myDelete(id);
      if(res == 1){
         commentService.deleteCommentByArticleID(id); // 删除该文章的同时删除该文章评论
         commentService.deleteLikeByArticleID(id); // 删除该文章对应的点赞信息
         return res;
      }else {
         return AjaxResult.fail(-1,"删除文章失败!");
      }
   }

   /**
    * 修改文章功能
    * @param request
    * @param id
    * @param content
    * @param title
    * @return
    * 修改成功返回受影响行数,失败返回 0
    */
   @RequestMapping("/update")
   public Integer myUpdate(HttpServletRequest request,Integer id,String content,String title){//这里传进来的是文章的id
         //非空校验
         if(StringUtils.hasLength(content) && StringUtils.hasLength(title)){
            //得到当前用户和文章的对象
            Articleinfo articleinfo = articleService.myDetail(id);
            UserInfo userInfo = SessionUnit.getLoginUser(request);
            if (userInfo != null && userInfo.getId() == articleinfo.getUid()){//该文章是该用户发表
               return articleService.myUpdate(id, content, title);
            }
         }
      return 0;
   }

   /**
    * 定时发布功能
    * @param request
    * @param date
    * @param content
    * @param title
    * @return
    * @throws InterruptedException
    */
   String CONTENT;
   String TITLE;
   UserInfo USERINFO; //定义全局变量在多线程中使用
   @RequestMapping("/schedule")
   public Integer scheduledAdd(HttpServletRequest request, String date, String content, String title) throws InterruptedException, ParseException {

      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//24小时制
      long time = simpleDateFormat.parse(date).getTime();
      long difference = time - System.currentTimeMillis(); //减去当前时间的值

      //给全局变量赋值
      CONTENT = content;
      TITLE = title;

      if(difference >= 0){
         if(StringUtils.hasLength(content) && StringUtils.hasLength(title)) { //非空校验
            UserInfo userInfo = SessionUnit.getLoginUser(request); //得到当前用户对象
            USERINFO = userInfo; //给全局变量赋值
            if(userInfo != null){ //判断当前用户是否为空
               new Timer().schedule(new TimerTask(){ //建立定时器,一定时间后执行程序
                  @Override
                  public void run() {
                     Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                           articleService.myAdd(USERINFO.getId(),CONTENT,TITLE); //这里要用全局变量,要不然会空指针异常
                        }
                     });
                     thread.start();
                  }
               },difference);
            }
         }
      }else {
         return 0; //添加失败返回 0
      }
      return 1; //添加成功返回 1
   }

   /**
    * 发布文章功能
    * @param request
    * @param content
    * @param title
    * @return
    */
   @RequestMapping("/add")
   public Integer myAdd(HttpServletRequest request,String content,String title){
      //非空校验
      if(StringUtils.hasLength(content) && StringUtils.hasLength(title)){
         //得到当前用户对象
         UserInfo userInfo = SessionUnit.getLoginUser(request);
         if(userInfo != null){
           return articleService.myAdd(userInfo.getId(),content,title);
         }
      }
      return 0;
   }

   /**
    * 点赞功能
    * @param request
    * @param articleID
    * @return
    */
   @RequestMapping("/like")
   public Object likeArticle(HttpServletRequest request,Integer articleID){
      UserInfo userInfo = SessionUnit.getLoginUser(request);
      if(userInfo != null) {
         List<ArticleLikeInfo> resList = articleService.selectLike(userInfo.getId(),articleID);
         if(resList.size() == 0){ // 该用户没有给该评论点赞
            int res = articleService.likeArticle(articleID);
            articleService.insertLike(userInfo.getId(),articleID);
            return AjaxResult.success(res,"点赞成功");
         }else { // 该用户已经给该评论点赞了
            int res = articleService.unlikeArticle(articleID);
            articleService.deleteLike(userInfo.getId(),articleID);
            return AjaxResult.success(res,"取消点赞成功");
         }
      }
      return AjaxResult.fail(-1,"获取不到当前用户对象");
   }

   /**
    * 初始化点赞图标
    * @param request
    * @param articleID
    * @return
    */
   @RequestMapping("/state")
   public Object initLikeState(HttpServletRequest request,Integer articleID){
      UserInfo userInfo = SessionUnit.getLoginUser(request);
      if(userInfo != null) {
         List<ArticleLikeInfo> res = articleService.selectLike(userInfo.getId(),articleID);
         if(res.size() == 0){
            return 0;
         }else {
            return 1;
         }
      }
      return AjaxResult.fail(-1,"获取不到当前用户对象");
   }

   /**
    * 初始化文章点赞数
    * @param id
    * @return
    */
   @RequestMapping("/count")
   public Object initLikeCount(Integer id){
      Articleinfo articleinfo = articleService.myDetail(id);
      if(articleinfo != null){
         return articleinfo.getLikes();
      }
      return AjaxResult.fail(-1,"获取不到该文章对象");
   }

   /**
    * 分页功能
    * @param pindex
    * @param psize
    * @return
    */
   @RequestMapping("/list")
   public List<Articleinfo> homePageList(Integer pindex,Integer psize){
      if(pindex != null && psize != null && pindex > 0 && psize > 0) {
         Integer offset = psize * (pindex - 1);
         return articleService.pageList(psize,offset);
      }
      return null;
   }

   /**
    * 增加浏览量功能
    * @param id
    * @return
    */
   @RequestMapping("/view")
   public Object view(Integer id){
      int res = articleService.view(id);
      if(res == 1){
         return res;
      }else {
         return AjaxResult.fail(-1,"浏览量修改失败,请重试");
      }
   }

   /**
    * 初始化浏览量
    * @param id
    * @return
    */
   @RequestMapping("/initview")
   public Object initView(Integer id){
      Articleinfo articleinfo = articleService.myDetail(id);
      if(articleinfo != null){
         return articleinfo.getViews();
      }
      return AjaxResult.fail(-1,"获取不到该文章对象");
   }

   /**
    * 获取列表的总页数
    * @param psize
    * @return
    */
   @RequestMapping("/total")
   public Integer totalPage(Integer psize){
      return articleService.totalPage(psize);
   }
}
