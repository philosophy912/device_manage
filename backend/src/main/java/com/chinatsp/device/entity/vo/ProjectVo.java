/**
 * @author lizhe
 * @date 2020/11/27 16:10
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
@ApiModel(value = "项目显示实体")
public class ProjectVo {
    @ApiModelProperty(value = "序号")
    private Integer id;
    @ApiModelProperty(value = "项目名称", required = true)
    private String name;
    @ApiModelProperty(value = "创建时间")
    private Long timestamp;
}
