package com.lee.vue.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.vue.system.entity.User;
import com.lee.vue.system.mapper.UserMapper;
import com.lee.vue.system.vo.JwtUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author :lee
 * @date :Created in 2020/9/30
 * @description :用户登录服务类
 * @since :JDK 1.6
 **/
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    public User findUserInfo() {
        // 从SecurityContextHolder中获取到，当前登录的用户信息。
        JwtUser userDetails = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 根据用户Id，获取用户详细信息。
        User sysUser = baseMapper.selectById(userDetails.getId());
//        UserVo result = new UserVo();
//        BeanUtils.copyProperties(sysUser, result);
//        // 根据用户Id，获取到拥有的 权限列表
//        Set<SysPermission> permissions = sysPermissionService.findAllByUserId(sysUser.getUid());
//        List<ButtonVo> buttonVos = new ArrayList<>();
//        List<MenuVo> menuVos = new ArrayList<>();
//        if (permissions != null && permissions.size() > 1) {
//            permissions.forEach(permission -> {
//                if (permission.getType().toLowerCase().equals(PermissionType.BUTTON)) {
//                    /*
//                     * 如果权限是按钮，就添加到按钮里面
//                     * */
//                    buttonVos.add(
//                            new ButtonVo(
//                                    permission.getPid(),
//                                    permission.getResources(),
//                                    permission.getTitle())
//                    );
//                }
//                if (permission.getType().toLowerCase().equals(PermissionType.MENU)) {
//                    /*
//                     * 如果权限是菜单，就添加到菜单里面
//                     * */
//                    menuVos.add(
//                            new MenuVo(
//                                    permission.getPid(),
//                                    permission.getParentId(),
//                                    permission.getIcon(),
//                                    permission.getResources(),
//                                    permission.getTitle(),
//                                    null
//                            )
//                    );
//                }
//            });
//        }
//        result.setButtons(buttonVos);
//        result.setMenus(findRoots(menuVos));
//        Set<SysRole> roles = sysRoleService.findAllByUserId(result.getUid());
//        Set<String> rolesName = roles
//                .stream()
//                .map(r -> r.getDescription())
//                .collect(Collectors.toSet());
//        result.setRoles(rolesName);
        return sysUser;
    }
}
