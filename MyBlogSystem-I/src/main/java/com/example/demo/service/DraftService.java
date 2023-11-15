package com.example.demo.service;


import com.example.demo.mapper.DraftMapper;
import com.example.demo.model.DraftInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DraftService {
   @Autowired
   private DraftMapper draftMapper;

   public List<DraftInfo> selectAll(){
       return draftMapper.selectAll();
   }

   public int saveDraft(String title,String content,int uid){
       return draftMapper.saveDraft(title,content,uid);
   }

   public DraftInfo selectDraftById(int id){
       return draftMapper.selectDraftById(id);
   }

   public int deleteDraftById(int id){
       return draftMapper.deleteDraftById(id);
   }

   public int updateDraftById(int id,String title,String content){
       return draftMapper.updateDraftById(id,title,content);
   }
}
