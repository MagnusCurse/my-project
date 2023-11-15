package com.example.demo.controller;

import com.example.demo.common.SessionUnit;
import com.example.demo.model.DraftInfo;
import com.example.demo.model.UserInfo;
import com.example.demo.service.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/draft")
public class DraftController {
  @Autowired
  private DraftService draftService;

  /**
   * 初始化草稿列表
   * @return
   */
  @RequestMapping("/list")
  public List<DraftInfo> initDraftList(){
      List<DraftInfo> res = new ArrayList<>();
      res = draftService.selectAll();
      if(res != null){
        return res;
      }
      return null;
   }

    /**
     * 保存草稿功能
     * @param title
     * @param content
     * @param request
     * @return
     */
   @RequestMapping("/save")
    public int saveDraft(String title, String content, HttpServletRequest request){
       UserInfo userInfo = SessionUnit.getLoginUser(request);//得到当前用户的登录对象
       return draftService.saveDraft(title,content, userInfo.getId());
   }

    /**
     * 初始化草稿详情功能
     * @param id
     * @return
     */
   @RequestMapping("/detail")
   public DraftInfo initDetail(int id){
       DraftInfo res = draftService.selectDraftById(id);
       if(res != null){
           return res;
       }
       return null;
   }

    /**
     * 删除草稿功能
     * @param id
     * @return
     */
   @RequestMapping("/delete")
    public int deleteDraft(int id){
       int res = draftService.deleteDraftById(id);
       if(res != 0){
           return res;
       }
       return 0;
   }

   @RequestMapping("/update")
   public int updateDraft(int id,String title,String content){
       int res = draftService.updateDraftById(id,title,content);
       if(res != 0){
           return res;
       }
       return 0;
   }
}
