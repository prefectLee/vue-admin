package com.lee.vue.system.controller;

import com.alibaba.fastjson.JSON;
import com.lee.vue.system.constant.TestConstant;
import com.lee.vue.system.entity.User;
import com.lee.vue.system.mapper.UserMapper;
import com.lee.vue.system.service.UserService;
import com.lee.vue.system.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    private UserService userService;

    @GetMapping("/list")
    public List<User> getList(){
        return userService.list(null);
    }

    @ApiOperation(value = "获取用户详细信息", notes = "获取用户详细信息")
    @GetMapping("/info")
    public Object findUserInfo() {
        //return Result.success(userService.findUserInfo());
        return JSON.parse(TestConstant.USER_INFO);
    }
}
