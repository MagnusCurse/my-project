package com.example.demo.service;

import com.example.demo.mapper.MusicMapper;
import com.example.demo.model.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicService {
    @Autowired
    MusicMapper musicMapper;

    public int insert(String title,String singer,String uploadtime,String url,int user_id){
        return musicMapper.insert(title,singer,uploadtime,url,user_id);
    }

    public int deleteById(int id){
        return musicMapper.deleteById(id);
    }

    public Music selectById(int id){
        return musicMapper.selectById(id);
    }

    public List<Music> selectByTitle(String title){
        return musicMapper.selectByTitle(title);
    }

    public Music selectByTitleAndSinger(String title,String singer){
        return musicMapper.selectByTitleAndSinger(title,singer);
    }

    public List<Music> selectAll(int psize,int offset){
        return musicMapper.selectAll(psize,offset);
    }

    public int totalPage(int psize){
        List<Music> res = musicMapper.selectAllNoParam();
        return (int) Math.ceil(res.size() * 1.0 / psize); //ceil是进位函数:如 ceil(2.5) = 3
    }
}
