package com.example.demo.mapper;

import com.example.demo.model.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MusicMapper {
    /**
     * 将音乐插入到数据库中
     * @param title
     * @param singer
     * @param uplaodtime
     * @param user_id
     * @return
     */
    public int insert(@Param("title") String title,
                      @Param("singer") String singer,
                      @Param("uploadtime") String uplaodtime,
                      @Param("url") String url,
                      @Param("user_id") int user_id);

    /**
     * 根据id删除音乐
     * @param id
     * @return
     */
    public int deleteById(@Param("id") int id);

    /**
     * 根据id查询音乐
     * @param id
     * @return
     */
    public Music selectById(@Param("id") int id);

    /**
     * 根据title查询音乐:支持模糊查询
     * @param title
     * @return
     */
    public List<Music> selectByTitle(@Param("title") String title);

    /**
     * 根据title和singer查询音乐
     * @param title
     * @return
     */
    public Music selectByTitleAndSinger(@Param("title") String title,@Param("singer") String singer);

    /**
     * 查询数据库中的所有音乐
     * @return
     */
    public List<Music> selectAll(@Param("psize") int pszie,@Param("offset") int offset);

    /**
     * 查询数据库中的所有音乐(无参数)
     * @return
     */
    public List<Music> selectAllNoParam();
}
