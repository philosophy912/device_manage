package com.chinatsp.device.service;

import com.chinatsp.device.entity.vo.ProjectVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/11/27 16:08
 **/
public interface ProjectService {

    List<ProjectVo> findProject(Pageable pageable, String name);

    long findAllProjectCount();

    int findProjectCountByName(String name);

    ProjectVo addProject(ProjectVo projectVo);

    ProjectVo updateProject(ProjectVo projectVo);

    ProjectVo deleteProject(ProjectVo projectVo);

    List<ProjectVo> findProjectByName(ProjectVo projectVo);

    List<ProjectVo> findAllProject();
}
