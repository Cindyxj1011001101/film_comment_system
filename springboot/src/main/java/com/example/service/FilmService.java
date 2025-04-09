package com.example.service;

import com.example.entity.Film;
import com.example.mapper.CommentMapper;
import com.example.mapper.FilmMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    @Resource
    private FilmMapper filmMapper;
    @Resource
    private CommentMapper commentMapper;

    //查询所有
    public List<Film> selectAll(Film film) {
        List<Film> list = filmMapper.selectAll(film);
        return list;
    }

    public PageInfo<Film> selectPage(Film film,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Film> list = filmMapper.selectAll(film);
        for(Film f:list){
            this.setScore(f);
        }
        return PageInfo.of(list);
    }

    public void add(Film film) {
        filmMapper.insert(film);
    }

    public void update(Film film) {
        filmMapper.updateById(film);
    }

    public void deleteById(Integer id) {
        filmMapper.deleteById(id);
    }

    public Film selectById(Integer id) {
        Film film= filmMapper.selectById(id);
        this.setScore(film);
        return film;
    }
    //随机推荐电影
    public List<Film> selectRecommend(Integer filmid) {
        List<Film>films=this.selectAll(null);
        //删除当前电影
        films=films.stream().filter(film->!film.getId().equals(filmid)).collect(Collectors.toList());
        Collections.shuffle(films);//打乱排序
        films=films.stream().limit(3).collect(Collectors.toList());//推荐可以用机器学习优化，或者推荐三个评分最高的
        for(Film f:films){
            this.setScore(f);
        }
        return films;
    }

    private void setScore(Film film)
    {
        //查询当前电影有多少评分
        int total=commentMapper.selectTotal(film.getId());
        film.setCommentNum(total);
        if(total==0)
            film.setScore(0.0);
        else {
            double sum=commentMapper.selectSum(film.getId());
            BigDecimal score=BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(total),1, RoundingMode.HALF_UP);
            film.setScore(score.doubleValue());
        }
    }

    public List<Film> selectRanking() {
        List<Film>filmList=filmMapper.selectAll(null);
        for(Film film:filmList){
            setScore(film);
        }
        filmList=filmList.stream().sorted((f1,f2)->f2.getScore().compareTo(f1.getScore())).limit(5).collect(Collectors.toList());
        return filmList;
    }
}
