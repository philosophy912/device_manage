package com.chinatsp.device.entity.vo;

import com.chinatsp.device.utils.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author lizhe
 * @date 2020/11/20 14:57
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Response {

    private Integer code = Constant.OK;
    private String message;
    private Object data;
    private int pageSize = 20;
    private int totalRows = 0;
    private int totalPages = 0;

}
