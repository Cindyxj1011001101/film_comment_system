package com.example.mapper;

import com.example.entity.BookCategory;
import java.util.List;

public interface BookCategoryMapper {

    void insert(BookCategory bookcategory);

    void updateById(BookCategory bookcategory);

    void deleteById(Integer id);

    List<BookCategory> selectAll(BookCategory bookcategory);

}