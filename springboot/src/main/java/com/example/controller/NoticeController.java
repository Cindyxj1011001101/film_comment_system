package com.example.controller;

import com.example.common.Result;
import com.example.entity.Notice;
import com.example.mapper.NoticeMapper;
import com.example.service.NoticeService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @PostMapping("/add")
    public Result add(@RequestBody Notice notice) {
        noticeService.add(notice);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Notice notice) {
        noticeService.update(notice);
        return Result.success();
    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        noticeService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll(Notice notice) {
        List<Notice> list=noticeService.selectAll(notice);
        return Result.success(list);
    }

    @GetMapping("/selectPage")
    public Result selectPage(Notice notice,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Notice> pageinfo=noticeService.selectPage(notice,pageNum,pageSize);
        return Result.success(pageinfo);
    }
}
