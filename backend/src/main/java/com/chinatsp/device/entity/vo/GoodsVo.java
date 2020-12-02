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
@ApiModel(value = "设备显示实体")
public class GoodsVo {
    @ApiModelProperty(value = "序号")
    private Integer id;
    @ApiModelProperty(value = "设备名称", required = true)
    private String name;
    @ApiModelProperty(value = "设备编号")
    private String code;
    @ApiModelProperty(value = "设备图片")
    private String image;
    @ApiModelProperty(value = "设备所属雇员ID", required = true)
    private Integer employeeId;
    @ApiModelProperty(value = "设备所属雇员名称")
    private String employeeName;
    @ApiModelProperty(value = "设备所属项目ID", required = true)
    private Integer projectId;
    @ApiModelProperty(value = "设备所属项目名称")
    private String projectName;
    @ApiModelProperty(value = "设备数量", notes = "仅添加使用")
    private Integer count;
    @ApiModelProperty(value = "设备领用状态", required = true)
    private String recipientsStatus;
    @ApiModelProperty(value = "设备自身状态", required = true)
    private String goodsStatus;
    @ApiModelProperty(value = "设备入库时间", required = true)
    private Long inTime;
    @ApiModelProperty(value = "设备领用时间")
    private Long recipientsTime;
    @ApiModelProperty(value = "设备归还时间")
    private Long returnTime;
}
