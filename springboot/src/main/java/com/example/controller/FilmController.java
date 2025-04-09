package com.example.controller;

import com.example.ChatModel.ChatController;
import com.example.common.Result;
import com.example.entity.Film;
import com.example.service.FilmService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/film")
public class FilmController {

    @Resource
    private FilmService filmService;
    @Resource
    private ChatController chatController;

    @PostMapping("/add")
    public Result add(@RequestBody Film film) {
        film.setCategoryId(chatController.FilmCategory(film.getDescr()));
        filmService.add(film);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Film film) {
        film.setCategoryId(chatController.FilmCategory(film.getDescr()));
        filmService.update(film);
        return Result.success();
    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        filmService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/selectRecommend/{id}")
    public Result selectRecommend(@PathVariable Integer id) {
        List<Film> list=filmService.selectRecommend(id);
        return Result.success(list);
    }

    @GetMapping("/selectAll")
    public Result selectAll(Film film) {
        List<Film> list=filmService.selectAll(film);
        return Result.success(list);
    }

    @GetMapping("/selectRanking")
    public Result selectRanking() {
        List<Film> list=filmService.selectRanking();
        return Result.success(list);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Film film=filmService.selectById(id);
        return Result.success(film);
    }

    @GetMapping("/selectPage")
    public Result selectPage(Film film,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Film> pageinfo=filmService.selectPage(film,pageNum,pageSize);
        return Result.success(pageinfo);
    }
}
