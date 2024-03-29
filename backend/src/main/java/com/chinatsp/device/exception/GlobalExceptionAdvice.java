/**
 * @author lizhe
 * @date 2020/12/8 11:13
 **/
package com.chinatsp.device.exception;

import com.chinatsp.device.entity.vo.Response;
import com.chinatsp.device.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Response defaultErrorHandler(HttpServletRequest request, Exception e) {
        Response response = new Response();
        log.error("request url" + request.getRequestURI());
        response.setCode(Constant.NOK);
        response.setMessage("error and message is " + e.getMessage());
        log.info("response is {}", response);
        return response;
    }
}
