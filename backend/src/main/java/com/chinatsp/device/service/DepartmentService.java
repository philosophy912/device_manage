package com.chinatsp.device.service;

import com.chinatsp.device.entity.po.Department;
import com.chinatsp.device.entity.vo.DepartmentVo;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/11/26 9:43
 **/
public interface DepartmentService {

    List<DepartmentVo> findAllDepartment();

    List<DepartmentVo> findDepartment(int pageNo, int size);

    Long findAllDepartmentCount();
}
