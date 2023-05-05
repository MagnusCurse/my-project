package com.example.demo.mapper;

import com.example.demo.model.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoveMusicMapper {

    /**
     * 根据用户id和音乐id查询音乐
     * @param user_id
     * @param music_id
     * @return
     */
    public Music findLoveMusic(@Param("user_id") int user_id,@Param("music_id") int music_id);

    /**
     * 查询该用户的所有收藏音乐
     * @param user_id
     * @return
     */
    public List<Music> findLoveMusicByUserId(@Param("user_id") int user_id);

    /**
     * 对于该用户收藏的音乐进行模糊查询
     * @param title
     * @param user_id
     * @return
     */
    public List<Music> findLoveMusicByTitleAndUserId(
            @Param("title") String title,
            @Param("user_id") int user_id);

    /**
     * 将一个音乐插入喜欢列表
     * @param user_id
     * @param music_id
     * @return
     */
    public int insertLoveMusic(@Param("user_id") int user_id,@Param("music_id") int music_id);

    /**
     * 删除该用户喜欢列表的音乐
     * @param music_id
     * @return
     */
    public int deleteLoveMusic(@Param("music_id") int music_id,@Param("user_id") int user_id);

    /**
     * 根据音乐 ID 删除喜欢列表的音乐
     * @param music_id
     * @return
     */
    public int deleteLoveMusicByMusicId(@Param("music_id") int music_id);


}
