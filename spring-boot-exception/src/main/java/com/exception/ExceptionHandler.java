package com.exception;


import com.vo.ApiResult;
import com.vo.ApiResultGenerator;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/19
 */
@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class ExceptionHandler {

    @ResponseStatus(reason = "服务器有bug")
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ApiResult runtimeExceptionHandler(Exception e) {
        e.printStackTrace();
        return ApiResultGenerator.errorResult(e.getMessage(), e);
    }
}
