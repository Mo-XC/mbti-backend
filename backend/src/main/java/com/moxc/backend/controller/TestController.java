package com.moxc.backend.controller;

import com.moxc.backend.pojo.ApiResponse;
import com.moxc.backend.pojo.dto.GetQuestionsRequestDTO;
import com.moxc.backend.pojo.dto.QuestionDTO;
import com.moxc.backend.pojo.dto.SubmitTestRequestDTO;
import com.moxc.backend.pojo.dto.TestResultDTO;
import com.moxc.backend.service.QuestionService;
import com.moxc.backend.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TestResultService testResultService;

    /**
     * 获取测试题目
     * POST /test/questions
     *
     * 请求体示例：
     * {
     *   "version": "简洁版"
     * }
     *
     * 响应示例：
     * {
     *   "msg": "ok",
     *   "data": [
     *     {
     *       "id": 1,
     *       "content": "在社交场合中，我通常主动与他人交谈",
     *       "dimension": "E/I",
     *       "direction": 1
     *     },
     *     // ... 更多题目
     *   ],
     *   "errorCode": null
     * }
     */
    @PostMapping("/questions")
    public ApiResponse<List<QuestionDTO>> getQuestions(@RequestBody GetQuestionsRequestDTO request) {
        try {
            // 1. 参数验证
            if (request == null || request.getVersion() == null || request.getVersion().trim().isEmpty()) {
                return ApiResponse.error("请选择测试版本");
            }

            // 2. 验证版本是否有效
            String version = request.getVersion();
            if (!isValidVersion(version)) {
                return ApiResponse.error("无效的测试版本，请选择简洁版、普通版或完整版");
            }

            // 3. 调用服务层获取题目
            List<QuestionDTO> questions = questionService.getQuestionsByVersion(version);

            // 4. 检查是否获取到足够数量的题目
            int expectedCount = getExpectedQuestionCount(version);
            if (questions.size() < expectedCount) {
                // 实际题目数量不足，返回提示信息
                return ApiResponse.error(
                        String.format("题目数量不足，预期 %d 题，实际获取 %d 题", expectedCount, questions.size())
                );
            }

            // 5. 返回成功响应
            return ApiResponse.ok(questions);

        } catch (Exception e) {
            // 打印异常信息，便于调试
            e.printStackTrace();
            return ApiResponse.error("获取题目失败: " + e.getMessage());
        }
    }

    /**
     * 验证版本参数是否有效
     */
    private boolean isValidVersion(String version) {
        return version.equals("简洁版") || version.equals("普通版") || version.equals("完整版");
    }

    /**
     * 获取预期题目数量
     */
    private int getExpectedQuestionCount(String version) {
        switch (version) {
            case "简洁版":
                return 28;  // 4个维度 × 7题 = 28题
            case "普通版":
                return 100; // 4个维度 × 25题 = 100题
            case "完整版":
                return 180; // 4个维度 × 45题 = 180题
            default:
                return 0;
        }
    }

    /**
     * 测试接口连通性
     * GET /test/ping
     *
     * 响应示例：
     * {
     *   "msg": "ok",
     *   "data": "MBTI测试服务正常运行",
     *   "errorCode": null
     * }
     */
    @GetMapping("/ping")
    public ApiResponse<String> ping() {
        return ApiResponse.ok("MBTI测试服务正常运行");
    }

    /**
     * 获取测试版本信息
     * GET /test/versions
     *
     * 响应示例：
     * {
     *   "msg": "ok",
     *   "data": {
     *     "versions": [
     *       {
     *         "name": "简洁版",
     *         "description": "30题，快速了解自我",
     *         "questionCount": 28
     *       },
     *       {
     *         "name": "普通版",
     *         "description": "100题，深入了解自我",
     *         "questionCount": 100
     *       },
     *       {
     *         "name": "完整版",
     *         "description": "180题，全面剖析自我",
     *         "questionCount": 180
     *       }
     *     ]
     *   },
     *   "errorCode": null
     * }
     */
    @GetMapping("/versions")
    public ApiResponse<Object> getTestVersions() {
        try {
            Object versionInfo = new Object() {
                public final Object[] versions = {
                        new Object() {
                            public final String name = "简洁版";
                            public final String description = "30题，快速了解自我";
                            public final int questionCount = 28;
                        },
                        new Object() {
                            public final String name = "普通版";
                            public final String description = "100题，深入了解自我";
                            public final int questionCount = 100;
                        },
                        new Object() {
                            public final String name = "完整版";
                            public final String description = "180题，全面剖析自我";
                            public final int questionCount = 180;
                        }
                };
            };

            return ApiResponse.ok(versionInfo);
        } catch (Exception e) {
            return ApiResponse.error("获取版本信息失败");
        }
    }

    /**
     * 检查数据库题目数量
     * GET /test/question-count
     *
     * 这个接口主要用于调试，检查数据库中每个维度的题目数量
     */
    @GetMapping("/question-count")
    public ApiResponse<Object> getQuestionCount() {
        try {
            // 这里可以调用Service或Mapper获取更详细的信息
            // 暂时返回一个简单的响应，实际项目中可以从数据库查询
            Object countInfo = new Object() {
                public final String[] dimensions = {"E/I", "S/N", "T/F", "J/P"};
                public final String note = "此接口用于调试，检查每个维度的题目数量是否足够";
            };

            return ApiResponse.ok(countInfo);
        } catch (Exception e) {
            return ApiResponse.error("获取题目数量信息失败");
        }
    }

    /**
     * 获取用户提交的答案
     * POST /test/submit
     *
     * 请求体示例：
     * 对应的数据结构为 SubmitTestRequestDTO
     * {
     *     "version" : "简洁版",
     *     "answers" : [],
     *     "startedAt" : "2023-07-01T12:00:00Z",
     *     "completedAt" : "2023-07-01T12:30:00Z"
     *     "userId" : "雾语漫"
     * }
     *
     * 响应示例：
     * 对应都数据结构为 TestResultDTO
     * {
     *     "msg": "ok",
     *     "data": {
     *          "testId" : "1234567890",
     *          "mbtiType" : "ISTP",
     *          "dimensionScores" : {},
     *          "analysis" : {},
     *          "testVersion" : "简洁版",
     *          "completedTime" : "2023-07-01T12:30:00Z",
     *          "totalQuestions" : 28
     *     },
     *     "errorCode": null
     * }
     * */
    @PostMapping("/submit")
    public ApiResponse<TestResultDTO> submitTest(@RequestBody SubmitTestRequestDTO submitData) {
        try {
            if (submitData == null || submitData.getVersion() == null || submitData.getVersion().isEmpty()) {
                return ApiResponse.error("提交数据不能为空！");
            }
            // 调用服务层处理提交
            TestResultDTO result = testResultService.calculateAndSaveResult(submitData);

            // 返回成功响应
            return ApiResponse.ok(result);
        } catch (Exception e) {
            return ApiResponse.error("提交测试失败: " + e.getMessage());
        }
    }

    /**
     * 通过testId获取测试结果
     * GET /test/result/{testId}
     *
     * get请求示例：
     * /test/result/1234567890
     *
     * 响应示例：
     * 对应的数据结构为 TestResultDTO
     *  {
     *      "msg": "ok",
     *      "data": {
     *          "testId" : "1234567890",
     *          "mbtiType" : "ISTP",
     *          "dimensionScores" : {},
     *          "analysis" : {},
     *          "testVersion" : "简洁版",
     *          "completedTime" : "2023-07-01T12:30:00Z",
     *          "totalQuestions" : 28
     *      },
     *      "errorCode": null
     *  }
     * */
    @GetMapping("/result/{testId}")
    public ApiResponse<TestResultDTO> getTestResult(@PathVariable String testId) {
        try {
            // 调用服务层处理提交
            TestResultDTO result = testResultService.getResultById(testId);

            // 返回成功响应
            return ApiResponse.ok(result);
        } catch (Exception e) {
            return ApiResponse.error("获取测试结果失败: " + e.getMessage());
        }
    }
}