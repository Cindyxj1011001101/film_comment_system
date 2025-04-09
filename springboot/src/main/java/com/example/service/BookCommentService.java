package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.ChatModel.ChatController;
import com.example.entity.BookComment;
import com.example.entity.Comment;
import com.example.mapper.BookCommentMapper;
import com.example.mapper.CommentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCommentService {
    @Resource
    private BookCommentMapper bookcommentMapper;
    @Resource
    private ChatController chatController;

    //查询所有
    public List<BookComment> selectAll(BookComment bookcomment) {
        List<BookComment> list = bookcommentMapper.selectAll(bookcomment);
        return list;
    }
    //分页查询
    public PageInfo<BookComment> selectPage(BookComment bookcomment, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<BookComment> list = bookcommentMapper.selectAll(bookcomment);
        return PageInfo.of(list);
    }
    //添加
    public void add(BookComment bookcomment) {
        bookcomment.setTime(DateUtil.now());
        bookcomment.setScore(chatController.CommentScore(bookcomment.getComment()));
        bookcommentMapper.insert(bookcomment);
    }
    //更新
    public void update(BookComment bookcomment) {
        bookcomment.setTime(DateUtil.now());
        bookcommentMapper.updateById(bookcomment);
    }
    //通过ID删除
    public void deleteById(Integer id) {
        bookcommentMapper.deleteById(id);
    }
}
