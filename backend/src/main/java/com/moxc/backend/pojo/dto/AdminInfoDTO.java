package com.moxc.backend.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class AdminInfoDTO {
    private Integer id;
    private String username;
    private String avatar;
    @JsonProperty("super")
    private Integer adminType; // 实际字段用adminType，JSON输出为"super"
    private RoleDTO role;
    private List<MenuDTO> menus;
    private List<String> ruleNames;
}