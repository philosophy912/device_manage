package com.chinatsp.device.service;

import com.chinatsp.device.entity.vo.EmployeeVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    List<EmployeeVo> findEmployee(Pageable pageable, String name);

    long findAllEmployeeCount();

    EmployeeVo addEmployee(EmployeeVo employeeVo);

    EmployeeVo updateEmployee(EmployeeVo employeeVo);

    EmployeeVo deleteEmployee(EmployeeVo employeeVo);

    List<EmployeeVo> findEmployeeByName(EmployeeVo employeeVo);

    List<EmployeeVo> findAllEmployee();
}
