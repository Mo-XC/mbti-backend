package com.moxc.backend.service.Impl;

import com.moxc.backend.pojo.dto.AnswerItemDTO;
import com.moxc.backend.pojo.dto.SubmitTestRequestDTO;
import com.moxc.backend.pojo.dto.TestResultDTO;
import com.moxc.backend.service.TestResultService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TestResultServiceImpl implements TestResultService {
    @Override
    public TestResultDTO calculateAndSaveResult(SubmitTestRequestDTO submitData) {
        // 1. 计算维度分
        Map<String, Integer> dimensionScores = calculateDimensionScores(submitData.getAnswers());

        // 2. 计算MBTI类型
        String mbtiType = determineMBTIType(dimensionScores);

        // 3. 获取性格分析 暂时先不实现
//        PersonalityAnalysisDTO analysis = getPersonalityAnalysis(mbtiType);

        // 4. 生成测试ID
        String testId = generateTestId();

        // 5. 构建返回结果
        TestResultDTO result = new TestResultDTO();
        result.setTestId(testId);
        result.setMbtiType(mbtiType);
        result.setDimensionScores(dimensionScores);
        result.setAnalysis(null);
        result.setTestVersion(submitData.getVersion());
        result.setCompletedTime(new Date().toString());
        result.setTotalQuestions(submitData.getAnswers().size());

        // 6. 这里可以保存到数据库（如果后面需要）
        // saveResultToDatabase(result);

        return result;
    }

    private Map<String, Integer> calculateDimensionScores(List<AnswerItemDTO> answers) {
        Map<String, Integer> scores = new HashMap<>();

        // 初始化所有维度
        scores.put("E", 0); scores.put("I", 0);
        scores.put("S", 0); scores.put("N", 0);
        scores.put("T", 0); scores.put("F", 0);
        scores.put("J", 0); scores.put("P", 0);

        for (AnswerItemDTO answer : answers) {
            // 将1-5的答案转换为-2到2的分数
            int rawScore = answer.getAnswer() - 3;
            int adjustedScore = rawScore * answer.getDirection();

            // 根据adjustedScore的正负决定倾向哪个维度
            switch (answer.getDimension()) {
                case "E/I":
                    if (adjustedScore > 0) {
                        scores.put("E", scores.get("E") + adjustedScore);
                    } else if (adjustedScore < 0) {
                        scores.put("I", scores.get("I") - adjustedScore);  // 注意：adjustedScore是负数
                    }
                    break;
                case "S/N":
                    if (adjustedScore > 0) {
                        scores.put("S", scores.get("S") + adjustedScore);
                    } else if (adjustedScore < 0) {
                        scores.put("N", scores.get("N") - adjustedScore);
                    }
                    break;
                case "T/F":
                    if (adjustedScore > 0) {
                        scores.put("T", scores.get("T") + adjustedScore);
                    } else if (adjustedScore < 0) {
                        scores.put("F", scores.get("F") - adjustedScore);
                    }
                    break;
                case "J/P":
                    if (adjustedScore > 0) {
                        scores.put("J", scores.get("J") + adjustedScore);
                    } else if (adjustedScore < 0) {
                        scores.put("P", scores.get("P") - adjustedScore);
                    }
                    break;
            }
        }

        return scores;
    }

    private String determineMBTIType(Map<String, Integer> scores) {
        StringBuilder mbtiType = new StringBuilder();

        // E/I: 外向 vs 内向
        mbtiType.append(scores.get("E") >= scores.get("I") ? "E" : "I");

        // S/N: 实感 vs 直觉
        mbtiType.append(scores.get("S") >= scores.get("N") ? "S" : "N");

        // T/F: 思考 vs 情感
        mbtiType.append(scores.get("T") >= scores.get("F") ? "T" : "F");

        // J/P: 判断 vs 感知
        mbtiType.append(scores.get("J") >= scores.get("P") ? "J" : "P");

        return mbtiType.toString();
    }

    private String generateTestId() {
        // 生成唯一的测试ID
        return "TEST_" + System.currentTimeMillis() + "_" +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public TestResultDTO getResultById(String testId) {
        // 这里可以从数据库查询，暂时返回null
        // 实际项目中应该实现这个功能
        return null;
    }
}
