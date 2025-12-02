// AuthController.java
package com.moxc.backend.controller;

import com.moxc.backend.pojo.ApiResponse;
import com.moxc.backend.pojo.dto.LoginRequestDTO;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/*
* @RestController
* 这是一个组合注解，相当于 @Controller + @ResponseBody
* @Controller：标识这个类是Spring MVC的控制器
* @ResponseBody：表示方法的返回值直接写入HTTP响应体，而不是跳转到视图页面
* */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5174")
public class AuthController {

    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.getUsername();
        String password = loginRequestDTO.getPassword();

        // 模拟验证
        if ("moxc".equals(username) && "123".equals(password)) {
            Map<String, Object> data = new HashMap<>();
            data.put("token", "fake-jwt-token-" + System.currentTimeMillis());
            data.put("user", Map.of(
                    "id", 1,
                    "username", "moxc",
                    "personalityType", "INTJ"
            ));
            return ApiResponse.ok(data);
        } else {
            return ApiResponse.error("用户名或密码错误", 401);
        }
    }
}