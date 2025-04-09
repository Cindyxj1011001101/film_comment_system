package com.example.ChatModel;

import com.example.common.Result;
import com.example.controller.BookCategoryController;
import com.example.controller.CategoryController;
import com.example.entity.BookCategory;
import com.example.entity.Category;
import com.example.entity.ChatAns;
import com.example.entity.User;
import com.example.service.BookCategoryService;
import com.example.service.CategoryService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
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
    private CategoryService category;

    @GetMapping("/chat")
    public Result chat(@RequestParam(defaultValue = "介绍自己") String input) {
            Prompt prompt = new Prompt(List.of(new UserMessage(input)));
            ChatResponse chatResp = model.call(prompt);
            Generation result = chatResp.getResult();
            ChatAns ans = new ChatAns();
            ans.setChatans(result.getOutput().getText());
            return Result.success(ans);
    }
    



    @GetMapping("/CommentScore")
    public Double CommentScore(@RequestParam String Comment) {
        ChatClient chatClient = ChatClient.create(model);
        Double CommentScore = chatClient.prompt()
                .user("请根据以下电影评论给出一个合理的电影评分（1-5分，5分为最高，支持 0.5 分间隔）,仅返回一个数值，评论内容："+Comment)
                .call()
                .entity(Double.class);
        System.out.println(CommentScore);
        return CommentScore;
    }
    @GetMapping("/BookCategory")
    public Integer BookCategory(@RequestParam String Description) {
        ChatClient chatClient = ChatClient.create(model);
        List<BookCategory> list = bookCategory.selectAll(null);
        String allBookCategory="";
        for (BookCategory bookCategory : list) {
            allBookCategory+=bookCategory.getId()+":"+bookCategory.getName()+",";
        }
        Integer categoryId = chatClient.prompt()
                .user("请根据以下书籍简介返回一个电影标签，从"+allBookCategory+"中选择一个并返回其对应序号，简介内容："+Description)
                .call()
                .entity(Integer.class);
        System.out.println(categoryId);
        return categoryId;
    }
    @GetMapping("/FilmCategory")
    public Integer FilmCategory(@RequestParam String Description) {
        ChatClient chatClient = ChatClient.create(model);
        List<Category> list = category.selectAll(null);
        String allCategory="";
        for (Category category : list) {
            allCategory+=category.getId()+":"+category.getName()+",";
        }
        System.out.println(allCategory);
        Integer categoryId = chatClient.prompt()
                .user("请根据以下电影简介返回一个电影标签，从"+allCategory+"中选择一个并返回其对应序号，简介内容："+Description)
                .call()
                .entity(Integer.class);
        System.out.println(categoryId);
        return categoryId;
    }
}