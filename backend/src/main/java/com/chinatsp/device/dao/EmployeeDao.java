/**
 * @author lizhe
 * @date 2020/11/19 18:03
 **/
package com.chinatsp.device.dao;

import com.chinatsp.device.entity.po.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface EmployeeDao extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

    List<Employee> findByName(String name);

    List<Employee> findByNameLike(String name);

}
