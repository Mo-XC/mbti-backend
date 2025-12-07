package com.moxc.backend.service;

import com.moxc.backend.pojo.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AuthService {
    ApiResponse<Map<String, Object>> login(String username, String password);
}
