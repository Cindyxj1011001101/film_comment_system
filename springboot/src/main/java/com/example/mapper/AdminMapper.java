package com.example.mapper;

import com.example.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//操作admin相关数据接口

public interface AdminMapper {

    //增
    int insert(Admin admin);

    //删
    @Delete("delete from admin where id = #{id}")
    int deleteById(Integer id);

    //改
    int updateById(Admin admin);

    //根据ID查
    @Select("select * from admin where id = #{id}")
    Admin selectById(Integer id);

    //查所有
    List<Admin> selectAll(Admin admin);

    //根据用户名查
    @Select("select * from admin where username = #{username}")
    Admin selectByUsername(String username);

}