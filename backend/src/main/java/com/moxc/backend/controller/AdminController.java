package com.moxc.backend.controller;

import com.moxc.backend.pojo.ApiResponse;
import com.moxc.backend.pojo.dto.AdminInfoDTO;
import com.moxc.backend.pojo.dto.MenuDTO;
import com.moxc.backend.pojo.dto.RoleDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// ... 其他import

@RestController
@RequestMapping("/admin")
public class AdminController {

    // Content-Type：application/x-www-form-urlencoded 接口文档中定义的请求参数为 表单
    @PostMapping(value = "/getinfo", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    // 这里的token是请求头中的参数，我们这里实际上只需要验证token，所以这里不需要其他参数
    public ApiResponse<AdminInfoDTO> getAdminInfo(@RequestHeader("token") String token) {
        // 1. 验证token（这是核心安全步骤）
        if (token == null || token.isEmpty() || !isValidToken(token)) {
            // 返回与文档一致的错误格式
            return ApiResponse.error("非法token，请先登录！", 20000);
        }

        // 2. 构建模拟数据（这是你当前阶段的目标：先让接口通）
        AdminInfoDTO adminInfo = buildMockAdminInfo();

        // 3. 返回成功响应
        return ApiResponse.ok(adminInfo);
    }

    private boolean isValidToken(String token) {
        // TODO: 实现真实的Token验证逻辑
        // 例如：检查Redis、解析JWT等
        // 目前先实现一个简单的逻辑，例如检查是否为之前登录接口颁发的token
        return token.startsWith("fake-jwt-token-");
    }

    private AdminInfoDTO buildMockAdminInfo() {
        AdminInfoDTO info = new AdminInfoDTO();
        info.setId(3);
        info.setUsername("moxc");
        info.setAvatar("http://example.com/default-avatar.png");
        info.setAdminType(1);

        // 构建角色
        RoleDTO role = new RoleDTO();
        role.setId(2);
        role.setName("超级管理员");
        info.setRole(role);

        // 构建简化版菜单（先实现1-2层，确保结构正确）
        info.setMenus(buildMockMenus());

        // 构建权限列表
        info.setRuleNames(Arrays.asList("createRule,POST", "updateRule,PUT", "deleteRule,DELETE"));

        return info;
    }

    private List<MenuDTO> buildMockMenus() {
        // 先构建一个最简单的、只有一层的菜单，让流程跑通
        MenuDTO mainMenu = new MenuDTO();
        mainMenu.setId(5);
        mainMenu.setRule_id(0);
        mainMenu.setStatus(1);
        mainMenu.setCreate_time("2023-09-01 12:00:00");
        mainMenu.setUpdate_time("2023-09-01 12:00:00");
        mainMenu.setName("后台面板");
        mainMenu.setDesc("index");
        mainMenu.setFrontpath(null);
        mainMenu.setCondition(null);
        mainMenu.setMenu(1);
        mainMenu.setOrder(1);
        mainMenu.setIcon("help");
        mainMenu.setMethod("GET");
        mainMenu.setChild(new ArrayList<>()); // 子菜单为空列表

        return Arrays.asList(mainMenu);
    }
}