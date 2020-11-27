package com.chinatsp.device.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author lizhe
 * @date 2020/11/27 16:10
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectVo {
    private Integer id;
    private String name;
    private Long timestamp;
}
