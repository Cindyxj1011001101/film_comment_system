package com.example.controller;

import com.example.ChatModel.ChatController;
import com.example.common.Result;
import com.example.entity.Book;
import com.example.service.BookService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    private BookService bookService;
    @Resource
    private ChatController chatController;


    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Book book) {
        book.setCategoryId(chatController.BookCategory(book.getDescr()));
        bookService.add(book);
        return Result.success();
    }

    /**
     * 更新
     */
    @PutMapping("/update")
    public Result update(@RequestBody Book book) {
        book.setCategoryId(chatController.BookCategory(book.getDescr()));
        bookService.update(book);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        bookService.deleteById(id);
        return Result.success();
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Book book) {
        List<Book> list = bookService.selectAll(book);
        return Result.success(list);
    }
    /**
     * 查询书籍评分榜
     */

    @GetMapping("/selectRanking")
    public Result selectRanking() {
        List<Book> list = bookService.selectRanking();
        return Result.success(list);
    }

    @GetMapping("/selectRecommend/{id}")
    public Result selectRecommend(@PathVariable Integer id) {
        List<Book> list = bookService.selectRecommend(id);
        return Result.success(list);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Book book = bookService.selectById(id);
        return Result.success(book);
    }


    /**
     * 分页模糊查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Book book,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Book> pageInfo = bookService.selectPage(book, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
