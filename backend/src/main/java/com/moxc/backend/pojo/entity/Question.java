package com.moxc.backend.pojo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Question {
    private Integer id;
    private String content;
    private String dimension;  // E/I, S/N, T/F, J/P
    private Integer direction;  // 1正向, -1反向
    private Integer isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}