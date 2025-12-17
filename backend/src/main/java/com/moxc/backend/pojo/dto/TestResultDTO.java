package com.moxc.backend.pojo.dto;

import lombok.Data;
import java.util.Map;

@Data
public class TestResultDTO {
    private String testId;          // 测试ID
    private String mbtiType;        // MBTI类型，如"ISTP"
    private Map<String, Integer> dimensionScores;  // 各维度得分
    private PersonalityAnalysisDTO analysis;      // 性格分析

    // 添加一些额外的信息
    private String testVersion;     // 测试版本
    private String completedTime;   // 完成时间
    private Integer totalQuestions; // 总题数
}