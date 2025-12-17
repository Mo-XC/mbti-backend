package com.moxc.backend.pojo.dto;

import lombok.Data;
import java.util.List;

@Data
public class PersonalityAnalysisDTO {
    private String typeName;           // 类型名称，如"鉴赏家"
    private String typeDescription;    // 类型描述
    private List<String> strengths;    // 优势
    private List<String> weaknesses;   // 弱点
    private List<String> careerSuggestions; // 职业建议
    private String relationshipAdvice; // 关系建议
}