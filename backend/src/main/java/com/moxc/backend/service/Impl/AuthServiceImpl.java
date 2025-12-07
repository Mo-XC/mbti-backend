package com.moxc.backend.service.Impl;

import com.moxc.backend.mapper.AuthMapper;
import com.moxc.backend.pojo.ApiResponse;
import com.moxc.backend.pojo.entity.User;
import com.moxc.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public ApiResponse<Map<String, Object>> login(String username, String password) {
        User user = authMapper.findByUsername(username);
        if (user == null) {
            return ApiResponse.error("用户不存在", 20000);
        }
        if (!password.equals(user.getPassword())) {
            return ApiResponse.error("密码错误", 20000);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("token", "fake-jwt-token-" + System.currentTimeMillis());
        return ApiResponse.ok(data);
    }
}
