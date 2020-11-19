package com.chinatsp.device.dao;

import com.chinatsp.device.entity.po.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lizhe
 * @date 2020/11/19 18:04
 **/
public interface ProjectDao extends JpaRepository<Project, Integer> {
}
