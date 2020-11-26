package com.chinatsp.device.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * @author lizhe
 * @date 2020/11/26 9:40
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DepartmentVo {

    private Integer id;
    private String name;
    private Long createDate;
    private Integer pageNo;
    private Integer size;

}
