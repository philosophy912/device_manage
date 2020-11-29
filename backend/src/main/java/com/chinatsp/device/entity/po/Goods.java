package com.chinatsp.device.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

/**
 * 商品
 *
 * @author lizhe
 * @date 2020/11/19 17:51
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Goods")
public class Goods implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "description")
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    // 领用状态
    @Column(name = "recipients_status")
    private Boolean recipientsStatus;
    // 物品状态
    @Column(name = "goods_status")
    private Boolean goodsStatus;
    // 入库时间
    @Column(name = "in_time")
    private Long inTime;
    // 领用时间
    @Column(name = "recipients_time")
    private Long recipientsTime;
    // 归还时间
    @Column(name = "return_time")
    private Long returnTime;
    @JsonIgnoreProperties(value = {"goods"})
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @JsonIgnoreProperties(value = {"goods"})
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "project_id")
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
