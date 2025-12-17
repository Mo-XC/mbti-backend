package com.moxc.backend.mapper;

import com.moxc.backend.pojo.dto.QuestionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {
    List<QuestionDTO> getQuestionsByVersion(@Param("perDim") Integer perDim);
}
