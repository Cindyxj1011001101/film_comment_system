package com.example.mapper;

import com.example.entity.BookComment;
import com.example.entity.Comment;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BookCommentMapper {

    void insert(BookComment bookcomment);

    void updateById(BookComment bookcomment);

    void deleteById(Integer id);

    List<BookComment> selectAll(BookComment bookcomment);

    @Select("select count(*) from bookcomment where book_id = #{bookId}")
    int selectTotal(Integer bookId);

    @Select("select sum(score) from bookcomment where book_id = #{bookId}")
    double selectSum(Integer bookId);
}
