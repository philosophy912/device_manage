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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 所属的小组
 *
 * @author lizhe
 * @date 2020/11/19 17:56
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Department")
@ApiModel(value = "部门")
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "序号")
    private Integer id;
    @Column(name = "name", nullable = false)
    @ApiModelProperty(value = "部门名称")
    private String name;
    @Column(name = "create_date", nullable = false)
    @ApiModelProperty(value = "创建日期")
    private Long createDate;
    // 双向一对多，一个部门有多个员工
    @JsonIgnoreProperties(value = {"department"})
    @OneToMany(mappedBy = "department", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @ApiModelProperty(value = "部门下的所有雇员")
    private Set<Employee> employees = new HashSet<>();

    @Override
    public String toString() {
        employees.forEach(employee -> employees.add(null));
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", employees=" + employees +
                '}';
    }
}
