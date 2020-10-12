package com.lee.vue.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.vue.system.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    @Select("select u.*,u.name as username from sys_user u where u.name=#{username}")
    User selectByUserName(String username);
}
