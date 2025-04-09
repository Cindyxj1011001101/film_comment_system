package com.example.service;

import com.example.entity.Book;
import com.example.entity.Book;
import com.example.mapper.BookCommentMapper;
import com.example.mapper.BookMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Resource
    private BookMapper bookMapper;
    @Resource
    private BookCommentMapper bookcommentMapper;

    /**
     * 新增
     */
    public void add(Book book) {
        bookMapper.insert(book);
    }

    /**
     * 更新
     */
    public void update(Book book) {
        bookMapper.updateById(book);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }

    /**
     * 查询所有
     */
    public List<Book> selectAll(Book book) {
        return bookMapper.selectAll(book);
    }

    /**
     * 分页查询
     * @param pageNum 当前的页码
     * @param pageSize 每页的个数
     * @return 分页的对象  包含数据和分页参数 total
     */
    public PageInfo<Book> selectPage(Book book, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Book> list = bookMapper.selectAll(book);
        for(Book b:list){
            this.setScore(b);
        }
        return PageInfo.of(list);
    }


    /**
     *书籍排行榜
     */

    //随机推荐电影
    public List<Book> selectRecommend(Integer bookId) {
        List<Book>books=this.selectAll(null);
        //删除当前电影
        books=books.stream().filter(book->!book.getId().equals(bookId)).collect(Collectors.toList());
        Collections.shuffle(books);//打乱排序
        books=books.stream().limit(3).collect(Collectors.toList());
        for(Book f:books){
            this.setScore(f);
        }
        return books;
    }

    // 排行前5的书籍数据
    public List<Book> selectRanking() {
        List<Book> bookList = bookMapper.selectAll(null);
        for (Book book : bookList) {
            this.setScore(book);
        }
        return bookList.stream().sorted((b1, b2) -> b2.getScore().compareTo(b1.getScore())).limit(5).toList();
    }

    public Book selectById(Integer id) {
        Book book= bookMapper.selectById(id);
        this.setScore(book);
        return book;
    }

    private void setScore(Book book)
    {
        //查询当前电影有多少评分
        int total=bookcommentMapper.selectTotal(book.getId());
        book.setCommentNum(total);
        if(total==0)
            book.setScore(0.0);
        else {
            double sum=bookcommentMapper.selectSum(book.getId());
            BigDecimal score=BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(total),1, RoundingMode.HALF_UP);
            book.setScore(score.doubleValue());
        }
    }

}
