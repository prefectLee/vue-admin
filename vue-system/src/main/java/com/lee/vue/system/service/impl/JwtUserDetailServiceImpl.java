package com.lee.vue.system.service.impl;

import com.lee.vue.system.entity.User;
import com.lee.vue.system.mapper.UserMapper;
import com.lee.vue.system.vo.JwtUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :lee
 * @date :Created in 2020/9/30
 * @description :jwt用户安全服务类
 * @since :JDK 1.6
 **/
@Slf4j
@Service
public class JwtUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUserName(username);
        if (user == null || StringUtils.isEmpty(user.getId())) {
            throw new UsernameNotFoundException(String.format("'%s'.这个用户不存在", username));
        } else {
            // 根据数据库中的用户信息，构建JwtUser对象
            List<SimpleGrantedAuthority> collect = new ArrayList<>();
            collect.add(new SimpleGrantedAuthority("admin"));
            //List<SimpleGrantedAuthority> collect = user.getRoles().stream().map(SysRole::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            return new JwtUser(user.getUserName(), user.getPassword(), user.getEnabled(), collect);
        }

    }
}
