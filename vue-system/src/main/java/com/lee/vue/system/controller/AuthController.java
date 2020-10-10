package com.lee.vue.system.controller;

import com.alibaba.fastjson.JSON;
import com.lee.vue.system.constant.TestConstant;
import com.lee.vue.system.entity.User;
import com.lee.vue.system.mapper.UserMapper;
import com.lee.vue.system.util.JwtTokenUtil;
import com.lee.vue.system.util.Result;
import com.lee.vue.system.vo.JwtUser;
import com.lee.vue.system.vo.TokenValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("授权controller")
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @ApiOperation("获取用户列表")
    @GetMapping("/getList")
    private List<User> getUser() {
        return userMapper.selectList(null);
    }

    @PostMapping("/login")
    public Object login(@RequestBody Map<String, String> map) throws UsernameNotFoundException {
        String username = map.get("username");
        String password = map.get("password");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            //return Result.fail("用户或者密码不能为空！");
//            return "{\n" +
//                    "    \"code\": 400,\n" +
//                    "    \"msg\": \"用户或者密码不能为空！\",\n" +
//                    "    \"data\": null\n" +
//                    "}";
        }
        JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(username);
        String token = jwtTokenUtil.generateToken(userDetails);
        log.debug("userDetails: {}", userDetails);
        TokenValue tokenValue = TokenValue.builder()
                .header(jwtTokenUtil.getHeader())
                .value(token)
                .prefix(jwtTokenUtil.getTokenHead())
                .expiration(jwtTokenUtil.getExpiration())
                .build();
        System.out.println(token);
        // 登录成功后，就从 redis 中删除验证码
        //return Result.success("登录成功", tokenValue);

        //return Result.success("登录成功", user);
        return JSON.parse(TestConstant.LOGIN_INFO);
    }

    @GetMapping("/refreshToken")
    public String refreshToken(@RequestParam  String oldToken) {
        if (!jwtTokenUtil.isTokenExpired(oldToken)) {
            return jwtTokenUtil.refreshToken(oldToken);
        }
        return "error";
    }

    @PostMapping("/2step-code")
    public String stepCode(){
        return "{code: 0,\n" +
                "message: \"\",\n" +
                "result{\n" +
                "stepCode: 0}\n" +
                "}";
    }

    @PostMapping("/logout")
    public String logout(){
        return "{code: 0,\n" +
                "message: \"\",\n" +
                "result{\n" +
                "stepCode: 0}\n" +
                "}";
    }
}
