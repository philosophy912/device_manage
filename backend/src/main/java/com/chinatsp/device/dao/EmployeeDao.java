package com.chinatsp.device.dao;

import com.chinatsp.device.entity.po.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lizhe
 * @date 2020/11/19 18:03
 **/
public interface EmployeeDao extends JpaRepository<Employee, Integer> {
}
