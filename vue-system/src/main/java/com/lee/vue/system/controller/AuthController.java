package com.lee.vue.system.controller;

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
    public Result login(@RequestBody Map<String, String> map) throws UsernameNotFoundException {
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
        return Result.success("登录成功", tokenValue);

        //return Result.success("登录成功", user);
//        return "{\n" +
//                "    \"code\": 200,\n" +
//                "    \"msg\": \"登录成功\",\n" +
//                "    \"data\": {\n" +
//                "        \"username\": \"lee\",\n" +
//                "        \"password\": \"123456\",\n" +
//                "        \"authorities\": [\n" +
//                "            {\n" +
//                "                \"authority\": \"admin\"\n" +
//                "            }\n" +
//                "        ],\n" +
//                "        \"enabled\": true,\n" +
//                "        \"accountNonExpired\": true,\n" +
//                "        \"accountNonLocked\": true,\n" +
//                "        \"token\": 4291d7da9005377ec9aec4a71ea837f,\n" +
//                "        \"credentialsNonExpired\": true\n" +
//                "    }\n" +
//                "}";
       /* return "{\n" +
                "    \"code\": 200,\n" +
                "    \"message\": \"\",\n" +
                "    \"result\": {\n" +
                "        \"avatar\": \"/avatar2.jpg\",\n" +
                "        \"createTime\": 1497160610259,\n" +
                "        \"creatorId\": \"admin\",\n" +
                "        \"deleted\": 0,\n" +
                "        \"id\": \"4291d7da9005377ec9aec4a71ea837f\",\n" +
                "        \"lastLoginIp\": \"27.154.74.117\",\n" +
                "        \"lastLoginTime\": 1534837621348,\n" +
                "        \"merchantCode\": \"TLif2btpzg079h15bk\",\n" +
                "        \"name\": \"天野远子\",\n" +
                "        \"password\": \"\",\n" +
                "        \"role\": {\n" +
                "            \"id\": \"admin\",\n" +
                "            \"name\": \"管理员\",\n" +
                "            \"describe\": \"拥有所有权限\",\n" +
                "            \"status\": 1,\n" +
                "            \"deleted\": 0,\n" +
                "            \"createTime\": 1497160610259,\n" +
                "            \"creatorId\": \"system\",\n" +
                "            \"permissions\": [\n" +
                "                {\n" +
                "                    \"actionList\": null,\n" +
                "                    \"actions\": [\n" +
                "                        {\n" +
                "                            \"action\": \"add\",\n" +
                "                            \"defaultCheck\": false,\n" +
                "                            \"describe\": \"新增\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"action\": \"query\",\n" +
                "                            \"defaultCheck\": false,\n" +
                "                            \"describe\": \"查询\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"action\": \"get\",\n" +
                "                            \"defaultCheck\": false,\n" +
                "                            \"describe\": \"详情\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"action\": \"update\",\n" +
                "                            \"defaultCheck\": false,\n" +
                "                            \"describe\": \"修改\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"action\": \"delete\",\n" +
                "                            \"defaultCheck\": false,\n" +
                "                            \"describe\": \"删除\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"dataAccess\": null,\n" +
                "                    \"permissionId\": \"dashboard\",\n" +
                "                    \"permissionName\": \"仪表盘\",\n" +
                "                    \"roleId\": \"admin\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"roleId\": \"admin\",\n" +
                "        \"status\": 1,\n" +
                "        \"telephone\": \"\",\n" +
                "        \"username\": \"admin\"\n" +
                "    }\n" +
                "}";*/
    }

    @GetMapping("/refreshToken")
    public String refreshToken(@RequestParam  String oldToken) {
        if (!jwtTokenUtil.isTokenExpired(oldToken)) {
            return jwtTokenUtil.refreshToken(oldToken);
        }
        return "error";

    }
}
