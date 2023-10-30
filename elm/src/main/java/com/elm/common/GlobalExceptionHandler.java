package com.elm.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

//全局异常处理
//拦截注解为 RestController 的注解
@ControllerAdvice(annotations = {RestController.class, Conditional.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    //进行异常处理 处理的是 下面这种异常
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex)
    {
        log.error(ex.getMessage());

        if(ex.getMessage().contains("Duplicate entry"))
        {
            String[] split=ex.getMessage().split(" ");
            String msg=split[2]+"已存在";

            return R.error(msg);
        }

        return R.error("操作失败");
    }
}
