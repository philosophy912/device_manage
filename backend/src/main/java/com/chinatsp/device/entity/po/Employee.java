package com.chinatsp.device.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 雇员
 *
 * @author lizhe
 * @date 2020/11/19 17:55
 **/
@Setter
@Getter
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
    @JsonIgnoreProperties(value = {"employees"})
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "department_id")
    @ApiModelProperty(value = "所属部门")
    private Department department;
    // 双向一对多，一个员工可以持有多个设备
    @JsonIgnoreProperties(value = {"employee"})
    @OneToMany(mappedBy = "employee", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @ApiModelProperty(value = "持有设备")
    private Set<Goods> goods = new HashSet<>();

    @Override
    public String toString() {
        goods.forEach(g -> goods.add(null));

        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", createDate=" + createDate +
                ", department=" + department +
                ", goods=" + goods +
                '}';
    }

}
