package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//操作user相关数据接口

public interface UserMapper {

    //增
    int insert(User user);

    //删
    @Delete("delete from user where id = #{id}")
    int deleteById(Integer id);

    //改
    int updateById(User user);

    //根据ID查
    @Select("select * from user where id = #{id}")
    User selectById(Integer id);

    //查所有
    List<User> selectAll(User user);

    //根据用户名查
    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);

}