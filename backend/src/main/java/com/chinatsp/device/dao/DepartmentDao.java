package com.chinatsp.device.dao;

import com.chinatsp.device.entity.po.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lizhe
 * @date 2020/11/19 18:02
 **/
public interface DepartmentDao extends JpaRepository<Department, Integer> {
}
