package com.chinatsp.device.dao;

import com.chinatsp.device.entity.po.Department;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;
import java.util.List;


/**
 * @author lizhe
 * @date 2020/11/19 18:02
 **/
@Transactional
public interface DepartmentDao extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {

    @NotNull
    Page<Department> findAll(@NotNull Pageable pageable);

    List<Department> findByName(String name);

    List<Department> findByNameLike(String name);
}
