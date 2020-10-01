package com.lee.vue.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.vue.system.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    @Select("select u.*,u.named as username from sys_user u where u.named=#{username}")
    User selectByUserName(String username);
}
