package com.moxc.backend.service.Impl;

import com.moxc.backend.mapper.QuestionMapper;
import com.moxc.backend.pojo.dto.QuestionDTO;
import com.moxc.backend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<QuestionDTO> getQuestionsByVersion(String version) {
        Integer perDim;
        switch (version) {
            case "普通版":
                perDim = 25;
                break;
            case "完整版":
                perDim = 45;
                break;
            default:
                perDim = 7;
        }
        return questionMapper.getQuestionsByVersion(perDim);
    }
}
