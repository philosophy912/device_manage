/**
 * 商品
 *
 * @author lizhe
 * @date 2020/11/19 17:51
 **/
package com.chinatsp.device.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Goods")
@ApiModel(value = "设备")
public class Goods implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "序号")
    private Integer id;
    @Column(name = "name", nullable = false)
    @ApiModelProperty(value = "设备名称")
    private String name;
    @Column(name = "code", nullable = false)
    @ApiModelProperty(value = "设备编号")
    private String code;
    @Column(name = "description")
    @ApiModelProperty(value = "设备描述")
    private String description;
    @Column(name = "image_url")
    @ApiModelProperty(value = "设备图片")
    private String imageUrl;
    // 领用状态
    @Column(name = "recipients_status", nullable = false)
    @ApiModelProperty(value = "设备领用状态")
    private Boolean recipientsStatus;
    // 物品状态
    @Column(name = "goods_status", nullable = false)
    @ApiModelProperty(value = "设备状态")
    private Boolean goodsStatus;
    // 入库时间
    @Column(name = "in_time", nullable = false)
    @ApiModelProperty(value = "设备入库时间")
    private Long inTime;
    // 领用时间
    @Column(name = "recipients_time")
    @ApiModelProperty(value = "设备领用时间")
    private Long recipientsTime;
    // 归还时间
    @Column(name = "return_time")
    @ApiModelProperty(value = "设备归还时间")
    private Long returnTime;
    @JoinColumn(name = "employee_id")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @ApiModelProperty(value = "领用人")
    private Employee employee;
    @JoinColumn(name = "project_id", nullable = false)
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @ApiModelProperty(value = "所属项目")
    private Project project;

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", recipientsStatus=" + recipientsStatus +
                ", goodsStatus=" + goodsStatus +
                ", inTime=" + inTime +
                ", recipientsTime=" + recipientsTime +
                ", returnTime=" + returnTime +
                '}';
    }
}
