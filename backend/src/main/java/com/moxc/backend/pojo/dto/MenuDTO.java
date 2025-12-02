package com.moxc.backend.pojo.dto;

import lombok.Data;
import java.util.List;

@Data
public class MenuDTO {
    private Integer id;
    private Integer rule_id;
    private Integer status;
    private String create_time;
    private String update_time;
    private String name;
    private String desc;
    private String frontpath;
    private String condition;
    private Integer menu;
    private Integer order;
    private String icon;
    private String method;
    private List<MenuDTO> child; // 关键：用自身类型的列表表示子菜单
}