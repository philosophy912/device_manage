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
 * 所属项目
 *
 * @author lizhe
 * @date 2020/11/19 17:57
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Project")
@ApiModel(value = "项目")
public class Project implements Serializable, Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "序号")
    private Integer id;
    @Column(name = "name", nullable = false)
    @ApiModelProperty(value = "项目名称")
    private String name;
    @Column(name = "create_date", nullable = false)
    @ApiModelProperty(value = "创建日期")
    private Long createDate;
    // 双向一对多，一个员工可以持有多个设备
    @OneToMany(mappedBy = "project", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @ApiModelProperty(value = "项目下所有设备")
    private Set<Goods> goods = new HashSet<>();

    @Override
    public String toString() {
        goods.forEach(g -> goods.add(null));
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", goods=" + goods +
                '}';
    }

}
