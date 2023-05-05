package com.example.demo.service;

import com.example.demo.mapper.LoveMusicMapper;
import com.example.demo.model.Music;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoveMusicService {
    @Autowired
    LoveMusicMapper loveMusicMapper;

    public Music findLoveMusic(int user_id,int music_id){
        return loveMusicMapper.findLoveMusic(user_id,music_id);
    }

    public List<Music> findLoveMusicByUserId(int user_id){
        return loveMusicMapper.findLoveMusicByUserId(user_id);
    }

    public List<Music> findLoveMusicByTitleAndUserId(String title,int user_id){
        return loveMusicMapper.findLoveMusicByTitleAndUserId(title,user_id);
    }

    public int insertLoveMusic(int user_id,int music_id){
        return loveMusicMapper.insertLoveMusic(user_id,music_id);
    }

    public int deleteLoveMusic(int music_id,int user_id){
        return loveMusicMapper.deleteLoveMusic(music_id,user_id);
    }

    public int deleteLoveMusicById(int music_id){
        return loveMusicMapper.deleteLoveMusicByMusicId(music_id);
    }
}
