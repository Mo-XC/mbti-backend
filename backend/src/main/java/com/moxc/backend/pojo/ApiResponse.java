// pojo/ApiResponse.java
package com.moxc.backend.pojo;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String msg = "ok";  // 默认值
    private T data;
    private Integer errorCode;

    // 快速创建成功响应
    // static 后面的 <T> 是什么？
    // static 方法是在类加载的时候调用的，而不是在实例化的时候调用的，因此需要向static方法中转递 <T> 泛型
    // 也就是static 这里的 T 和 ApiResponse<T> 中的 T 不一样
    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data); // 这些set方法在@Data注解中定义了
        return response;
    }

    // 快速创建错误响应
    // 这里的错误响应为什么有两个方法？一个是500服务器错误，另一个是自定义的错误状态码，为什么要这样？
    // 更加方便管理
    public static <T> ApiResponse<T> error(String msg) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setMsg(msg);
        response.setErrorCode(500);
        return response;
    }

    public static <T> ApiResponse<T> error(String msg, Integer errorCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setMsg(msg);
        response.setErrorCode(errorCode);
        return response;
    }
}