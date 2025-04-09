package com.example.controller;

import com.example.common.Result;
import com.example.entity.BookComment;
import com.example.service.BookCommentService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookComment")
public class BookCommentController {

    @Resource
    private BookCommentService bookcommentService;

    @PostMapping("/add")
    public Result add(@RequestBody BookComment bookcomment) {
        bookcommentService.add(bookcomment);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody BookComment bookcomment) {
        bookcommentService.update(bookcomment);
        return Result.success();
    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        bookcommentService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll(BookComment bookcomment) {
        List<BookComment> list=bookcommentService.selectAll(bookcomment);
        return Result.success(list);
    }

    @GetMapping("/selectPage")
    public Result selectPage(BookComment bookcomment,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<BookComment> pageinfo=bookcommentService.selectPage(bookcomment,pageNum,pageSize);
        return Result.success(pageinfo);
    }
}
