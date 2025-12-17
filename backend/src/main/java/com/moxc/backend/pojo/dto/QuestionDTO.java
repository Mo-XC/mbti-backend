package com.moxc.backend.pojo.dto;

import lombok.Data;

@Data
public class QuestionDTO {
    Integer id;
    String content;
    String dimension;
    Integer direction;

    public QuestionDTO() {
    }

    public QuestionDTO(Integer id, String content, String dimension, Integer direction) {
        this.id = id;
        this.content = content;
        this.dimension = dimension;
        this.direction = direction;
    }
}
