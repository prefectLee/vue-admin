package com.lee.vue.system.jwt;

import com.lee.vue.system.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author :lee
 * @date :Created in 2020/9/30
 * @description :jwt授权过滤器
 * @since :JDK 1.6
 **/
@Slf4j
@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        /*// 从这里开始获取 request 中的 jwt token
        String authHeader = request.getHeader(jwtTokenUtil.getHeader());
        log.info("authHeader：{}", authHeader);
        // 验证token是否存在
        if (authHeader != null && StringUtils.isNotEmpty(authHeader)) {
            // 根据token 获取用户名
            String username = jwtTokenUtil.getUsernameFromToken(authHeader);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 通过用户名 获取用户的信息
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // 验证token和用户是否匹配
                if (jwtTokenUtil.validateToken(authHeader, userDetails)) {
                    // 然后把构造UsernamePasswordAuthenticationToken对象
                    // 最后绑定到当前request中，在后面的请求中就可以获取用户信息
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }*/
        chain.doFilter(request, response);
    }
}

