package com.chinatsp.device.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/11/20 15:11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RolesVo {
    private List<String> roles;
    private String introduction;
    private String avatar;
    private String name;
}