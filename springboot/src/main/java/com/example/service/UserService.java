package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Account;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

//管理员业务处理
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    //增
    public void add(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (ObjectUtil.isNotNull(dbUser)) {
            throw new CustomException("用户不存在");
        }
        if (ObjectUtil.isEmpty(user.getPassword())) {
            user.setPassword("123");
        }
        if (ObjectUtil.isEmpty(user.getName())) {
            user.setName(user.getUsername());
        }
        user.setRole("USER");
        userMapper.insert(user);
    }

    //删
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    //改
    public void updateById(User user) {
        userMapper.updateById(user);
    }

    //根据ID查
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    //查所有
    public List<User> selectAll(User user) {
        return userMapper.selectAll(user);
    }

    //分页查
    public PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectAll(user);
        return PageInfo.of(list);
    }

    //登录
    public Account login(Account account) {
        Account dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException("用户不存在");
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException("账号或密码错误");
        }
        return dbUser;
    }

    //改密码
    public void updatePassword(Account account) {
        User dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException("用户不存在");
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException("原密码错误");
        }
        dbUser.setPassword(account.getNewPassword());
        userMapper.updateById(dbUser);
    }

}