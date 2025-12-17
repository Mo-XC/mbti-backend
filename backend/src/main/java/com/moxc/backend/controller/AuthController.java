// AuthController.java
package com.moxc.backend.controller;

import com.moxc.backend.pojo.ApiResponse;
import com.moxc.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth") // 接口文档中路径 /admin/login
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ApiResponse<Map<String, Object>> login(
            @RequestParam String username,
            @RequestParam String password) {
        return authService.login(username, password);
    }
}