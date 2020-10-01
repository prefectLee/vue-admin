package com.lee.vue.system.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.vue.system.entity.User;
import com.lee.vue.system.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author :lee
 * @date :Created in 2020/9/30
 * @description :用户登录服务类
 * @since :JDK 1.6
 **/
@Service
public class UserService extends ServiceImpl<UserMapper,User> {

}
