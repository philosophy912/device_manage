package com.chinatsp.device.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "雇员显示实体")
public class EmployeeVo {
    @ApiModelProperty(value = "序号")
    private Integer id;
    @ApiModelProperty(value = "雇员名称", required = true )
    private String name;
    @ApiModelProperty(value = "雇员性别", required = true)
    private String sex;
    @ApiModelProperty(value = "雇员创建时间")
    private Long timestamp;
    @ApiModelProperty(value = "雇员所属部门ID", required = true)
    private Integer departmentId;
    @ApiModelProperty(value = "雇员所属部门名称")
    private String departmentName;
}
