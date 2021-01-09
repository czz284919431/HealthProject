package com.czz;

import com.czz.entity.Result;
import com.czz.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author czz
 * @create 2021-01-09 0:32
 **/
@RestControllerAdvice
public class MyExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(MyExceptionAdvice.class);

    /**
     * 业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    public Result handleException(MyException e) {
        return new Result(false, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("发生异常", e);
        return new Result(false, "发生未知异常，请联系管理员");
    }
}
