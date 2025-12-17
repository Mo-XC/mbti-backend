package com.moxc.backend.service;

import com.moxc.backend.pojo.dto.SubmitTestRequestDTO;
import com.moxc.backend.pojo.dto.TestResultDTO;
import org.springframework.stereotype.Service;

@Service
public interface TestResultService {
    TestResultDTO calculateAndSaveResult(SubmitTestRequestDTO submitData);

    TestResultDTO getResultById(String testId);
}
