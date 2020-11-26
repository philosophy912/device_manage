package com.chinatsp.device.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
import java.util.Date;

/**
 * 商品
 *
 * @author lizhe
 * @date 2020/11/19 17:51
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    @Column(name = "imageUrl")
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
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "project_id")
    private Project project;

}
