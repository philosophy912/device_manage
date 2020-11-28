package com.chinatsp.device.service;

import com.chinatsp.device.entity.vo.DepartmentVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/11/26 9:43
 **/
public interface DepartmentService {

    List<DepartmentVo> findAllDepartment();

    List<DepartmentVo> findDepartment(Pageable pageable, String name);

    Long findAllDepartmentCount();

    DepartmentVo addDepartment(DepartmentVo departmentVo);

    DepartmentVo updateDepartment(DepartmentVo departmentVo);

    DepartmentVo deleteDepartment(DepartmentVo departmentVo);

    List<DepartmentVo> findDepartmentByName(DepartmentVo departmentVo);
}
