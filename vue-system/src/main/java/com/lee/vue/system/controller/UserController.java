package com.lee.vue.system.controller;

import com.lee.vue.system.entity.User;
import com.lee.vue.system.mapper.UserMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :lee
 * @date :Created in 2020/10/6
 * @description :用户操作类
 * @since :JDK 1.6
 **/
@Api("用户controller")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/list")
    public List<User> getList(){
        return userMapper.selectList(null);
    }
}
