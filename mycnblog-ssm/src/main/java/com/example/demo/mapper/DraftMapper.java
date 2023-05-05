package com.example.demo.mapper;

import com.example.demo.controller.DraftController;
import com.example.demo.model.DraftInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DraftMapper {

    /**
     * 查询草稿表的所有内容
     * @return
     */
    public List<DraftInfo> selectAll();

    /**
     * 保存草稿
     * @return
     */
    public int saveDraft(@Param("title") String title,
                         @Param("content") String content,
                         @Param("uid") int uid);

    /**
     * 根据草稿 id 查询一篇草稿
     * @param id
     * @return
     */
    public DraftInfo selectDraftById(@Param("id") int id);

    /**
     * 根据草稿 id 删除一篇草稿
     * @param id
     * @return
     */
    public int deleteDraftById(@Param("id") int id);

    /**
     * 根据草稿 id 修改一篇草稿
     * @param id
     * @return
     */
    public int updateDraftById(@Param("id") int id,
                               @Param("title") String title,
                               @Param("content") String content);
}
