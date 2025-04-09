package com.example.mapper;

import com.example.entity.Book;
import java.util.List;

public interface BookMapper {

    void insert(Book book);

    void updateById(Book book);

    void deleteById(Integer id);

    List<Book> selectAll(Book book);

    Book selectById(Integer id);
}
