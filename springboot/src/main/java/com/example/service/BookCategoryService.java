package com.example.service;

import com.example.entity.BookCategory;
import com.example.mapper.BookCategoryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCategoryService {

    @Resource
    private BookCategoryMapper bookcategoryMapper;

    /**
     * 新增
     */
    public void add(BookCategory bookcategory) {
        bookcategoryMapper.insert(bookcategory);
    }

    /**
     * 更新
     */
    public void update(BookCategory bookcategory) {
        bookcategoryMapper.updateById(bookcategory);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        bookcategoryMapper.deleteById(id);
    }

    /**
     * 查询所有
     */
    public List<BookCategory> selectAll(BookCategory bookcategory) {
        return bookcategoryMapper.selectAll(bookcategory);
    }

    /**
     * 分页查询
     * @param pageNum 当前的页码
     * @param pageSize 每页的个数
     * @return 分页的对象  包含数据和分页参数 total
     */
    public PageInfo<BookCategory> selectPage(BookCategory bookcategory, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BookCategory> list = bookcategoryMapper.selectAll(bookcategory);
        return PageInfo.of(list);
    }

}