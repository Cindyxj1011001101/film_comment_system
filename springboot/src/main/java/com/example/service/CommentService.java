package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Comment;
import com.example.mapper.CommentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;

    //查询所有
    public List<Comment> selectAll(Comment comment) {
        List<Comment> list = commentMapper.selectAll(comment);
        return list;
    }

    public PageInfo<Comment> selectPage(Comment comment,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Comment> list = commentMapper.selectAll(comment);
        return PageInfo.of(list);
    }

    public void add(Comment comment) {
        comment.setTime(DateUtil.now());
        commentMapper.insert(comment);
    }

    public void update(Comment comment) {
        comment.setTime(DateUtil.now());
        commentMapper.updateById(comment);
    }

    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
    }
}
