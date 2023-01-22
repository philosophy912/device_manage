/**
 * @author lizhe
 * @date 2020/11/26 9:40
 **/
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
@ApiModel(value = "部门显示实体")
public class DepartmentVo {
    @ApiModelProperty(value = "序号")
    private Integer id;
    @ApiModelProperty(value = "部门名称", required = true)
    private String name;
    @ApiModelProperty(value = "部门创建时间")
    private Long timestamp;

}
