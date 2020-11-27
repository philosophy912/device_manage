package com.chinatsp.device.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeVo {

    private Integer id;
    private String name;
    private String sex;
    private Long timestamp;
    private Integer departmentId;
    private String departmentName;
}
