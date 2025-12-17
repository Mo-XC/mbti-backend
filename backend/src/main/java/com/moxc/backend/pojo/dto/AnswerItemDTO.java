package com.moxc.backend.pojo.dto;

import lombok.Data;

@Data
public class AnswerItemDTO {
    private Integer questionId;    // 题目ID
    private Integer answer;        // 答案（1-5）
    private String dimension;      // 题目维度
    private Integer direction;     // 计分方向
}