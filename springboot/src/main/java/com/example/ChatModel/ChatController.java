package com.example.ChatModel;

import com.example.common.Result;
import com.example.entity.*;
import com.example.service.BookCategoryService;
import com.example.service.BookService;
import com.example.service.CategoryService;
import com.example.service.FilmService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class ChatController {

    @Resource
    private OpenAiChatModel model;
    @Resource
    private BookCategoryService bookCategory;
    @Resource
    private CategoryService filmcategory;
    @Resource
    private FilmService film;
    @Resource
    private BookService book;

    @GetMapping("/CommentScore")
    public Double CommentScore(@RequestParam String Comment) {
        ChatClient chatClient = ChatClient.create(model);
        Double CommentScore = chatClient.prompt()
                .user("请根据以下电影评论给出一个合理的电影评分（1-5分，5分为最高，支持 0.5 分间隔）,仅返回一个数值，评论内容：" + Comment)
                .call()
                .entity(Double.class);
        System.out.println("电影评分："+CommentScore);
        return CommentScore;
    }

    @GetMapping("/BookCategory")
    public Integer BookCategory(@RequestParam String Description) {
        ChatClient chatClient = ChatClient.create(model);
        List<BookCategory> list = bookCategory.selectAll(null);
        String allBookCategory = "";
        for (BookCategory bookCategory : list) {
            allBookCategory += bookCategory.getId() + ":" + bookCategory.getName() + ",";
        }
        Integer categoryId = chatClient.prompt()
                .user("请根据以下书籍简介返回一个电影标签，从" + allBookCategory + "中选择一个并返回其对应序号，简介内容：" + Description)
                .call()
                .entity(Integer.class);
        System.out.println(categoryId);
        return categoryId;
    }

    @GetMapping("/FilmCategory")
    public Integer FilmCategory(@RequestParam String Description) {

        List<Category> list = filmcategory.selectAll(null);
        String allCategory = "";
        for (Category category : list) {
            allCategory += category.getId() + ":" + category.getName() + ",";
        }

        ChatClient chatClient = ChatClient.create(model);
        Integer categoryId = chatClient.prompt()
                .user("请根据以下电影简介返回一个电影标签，从" + allCategory + "中选择一个并返回其对应序号，简介内容：" + Description)
                .call()
                .entity(Integer.class);
        System.out.println(categoryId);
        return categoryId;
    }
    @GetMapping("/AIChat")
    public Result AIChat(@RequestParam String InputMessage) {

        List<Film> listfilm = film.selectAll(null);
        String allFilm="";
        String filmname="";
        for (Film film : listfilm) {
            allFilm+=film.getName()+":"+film.getId()+",";
            filmname+=film.getName()+",";
        }
        System.out.println(allFilm);
        List<Book> listbook = book.selectAll(null);
        String allBook="";
        String bookname="";
        for (Book book : listbook) {
            allBook+=book.getName()+":"+book.getId()+",";
            bookname+=book.getName()+",";
        }
        ChatClient chatClient = ChatClient.create(model);

        ChatAns chatans = new ChatAns();

        System.out.println(InputMessage);
        int type = chatClient.prompt()
                .user("根据用户问题分析出需要推荐的内容对应的类型倾向，需要推荐电影为1，需要推荐书籍为2，仅返回数字1或2，用户输入内容："+InputMessage)
                .call()
                .entity(Integer.class);
        chatans.setType(type);
        System.out.println(type);


        if(type==1)
        {

            ChatClient.CallResponseSpec chatres = chatClient.prompt()
                    // .user( "请根据用户问题："+InputMessage+"从以下电影列表：" + filmname + "与书籍列表：" + bookname + "中选出最推荐的内容，并给出大于20字的推荐理由，直接返回纯文本，不要用任何格式（如 JSON、Markdown、代码块等）。")
                    .user( "请根据用户输入"+InputMessage+"从以下电影列表：" +filmname+"中选出最推荐的一部电影并说明原因。仅推荐一部电影，限定50字。")
                    .call();
            System.out.println(chatres.content());
            chatans.setChatans(chatres.content());
            System.out.println(chatans.getChatans());

            int id=chatClient.prompt()
                    .user("根据"+chatans.getChatans()+"在"+allFilm+"中找到该电影对应的id，id为电影名称后的冒号跟着的数字，仅返回一个数字")
                    .call()
                    .entity(Integer.class);
                    chatans.setId(id);
        }
        else{

            ChatClient.CallResponseSpec chatres = chatClient.prompt()
                    .user( "请根据用户输入"+InputMessage+"从以下书籍列表：" +bookname+"中选出最推荐的一本书并说明原因。仅推荐一本书，限定50字。")
                    .call();
            chatans.setChatans(chatres.content());

            int id=chatClient.prompt()
                    .user("根据"+chatans.getChatans()+"在"+allBook+"中找到该书籍对应的id，id为电影名称后的冒号跟着的数字，仅返回一个数字")
                    .call()
                    .entity(Integer.class);
            chatans.setId(id);
        }
        System.out.println(chatans.getChatans());
        System.out.println(chatans.getType());
        System.out.println(chatans.getId());
        return Result.success(chatans);
    }

}