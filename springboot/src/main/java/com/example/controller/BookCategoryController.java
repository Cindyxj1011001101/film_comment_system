package com.example.controller;

import com.example.common.Result;
import com.example.entity.BookCategory;
import com.example.service.BookCategoryService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookcategory")
public class BookCategoryController {

    @Resource
    private BookCategoryService bookcategoryService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody BookCategory bookcategory) {
        bookcategoryService.add(bookcategory);
        return Result.success();
    }

    /**
     * 更新
     */
    @PutMapping("/update")
    public Result update(@RequestBody BookCategory bookcategory) {
        bookcategoryService.update(bookcategory);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        bookcategoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(BookCategory bookcategory) {
        List<BookCategory> list = bookcategoryService.selectAll(bookcategory);
        return Result.success(list);
    }

    /**
     * 分页模糊查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(BookCategory bookcategory,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<BookCategory> pageInfo = bookcategoryService.selectPage(bookcategory, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
