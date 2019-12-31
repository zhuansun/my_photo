package com.zspc.photo.my_photo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理
 *
 * @author : zhuansun
 * @date : 2019-12-31 10:21
 **/
@ControllerAdvice
public class SubmitExceptionHandler {


    private static final Logger logger = LoggerFactory.getLogger(SubmitExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public SubmitResponse handle(Exception e) {

        if (e instanceof SubmitException) {
            SubmitException submitException = (SubmitException) e;
            return SubmitResponse.buildError(submitException.getCode(),submitException.getMsg());
        }
        logger.error("异常:{}",e);
        return SubmitResponse.buildUnkownError();
    }

}
