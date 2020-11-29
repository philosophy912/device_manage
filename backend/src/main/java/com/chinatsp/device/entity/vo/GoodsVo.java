package com.chinatsp.device.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GoodsVo {
    private Integer id;
    private String name;
    private String code;
    private String image;
    private Integer employeeId;
    private String employeeName;
    private Integer projectId;
    private String projectName;
    private Integer count;
    private String recipientsStatus;
    private String goodsStatus;
    private Long inTime;
    private Long recipientsTime;
    private Long returnTime;
}
