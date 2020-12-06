package com.chinatsp.device.dao;

import com.chinatsp.device.entity.po.Project;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/11/19 18:04
 **/
public interface ProjectDao extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project> {
    @NotNull
    Page<Project> findAll(@NotNull Pageable pageable);

    List<Project> findByName(String name);

    List<Project> findByNameLike(String name);

}
