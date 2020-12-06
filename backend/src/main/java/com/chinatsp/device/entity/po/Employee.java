package com.chinatsp.device.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

/**
 * 雇员
 *
 * @author lizhe
 * @date 2020/11/19 17:55
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Employee")
@ApiModel(value = "雇员")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "序号")
    private Integer id;
    @Column(name = "name", nullable = false)
    @ApiModelProperty(value = "雇员名字")
    private String name;
    @Column(name = "sex", nullable = false)
    @ApiModelProperty(value = "雇员性别")
    private Boolean sex;
    @Column(name = "create_date", nullable = false)
    @ApiModelProperty(value = "创建日期")
    private Long createDate;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "department_id")
    @ApiModelProperty(value = "所属部门")
    private Department department;

}
