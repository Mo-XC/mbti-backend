package com.moxc.backend.pojo.dto;

import lombok.Data;
import java.util.List;

@Data
public class SubmitTestRequestDTO {
    private String version;           // 测试版本
    private List<AnswerItemDTO> answers;  // 所有答案
    private String startedAt;         // 开始时间
    private String completedAt;       // 完成时间
    // 可以添加用户ID，如果后面需要用户系统
    private String userId = "雾语漫";  // 默认匿名用户
}