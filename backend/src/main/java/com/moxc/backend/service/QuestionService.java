package com.moxc.backend.service;

import com.moxc.backend.pojo.dto.QuestionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {

    List<QuestionDTO> getQuestionsByVersion(String version);
}
