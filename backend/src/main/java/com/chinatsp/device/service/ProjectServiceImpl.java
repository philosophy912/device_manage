/**
 * @author lizhe
 * @date 2020/11/27 16:08
 **/
package com.chinatsp.device.service;

import com.chinatsp.device.dao.ProjectDao;
import com.chinatsp.device.entity.po.Department;
import com.chinatsp.device.entity.po.Employee;
import com.chinatsp.device.entity.po.Project;
import com.chinatsp.device.entity.vo.DepartmentVo;
import com.chinatsp.device.entity.vo.EmployeeVo;
import com.chinatsp.device.entity.vo.ProjectVo;
import com.chinatsp.device.utils.Constant;
import com.chinatsp.device.utils.ObjectUtils;
import com.philosophy.base.util.StringsUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private ProjectDao projectDao;

    private ProjectVo convert(Project project) {
        ProjectVo vo = new ProjectVo();
        vo.setId(project.getId());
        vo.setName(project.getName());
        vo.setTimestamp(project.getCreateDate());
        return vo;
    }

    private Project convert(ProjectVo vo, String type) {
        Project project = new Project();
        // 更新的时候需要ID
        if (type.equalsIgnoreCase(Constant.UPDATE)) {
            project.setId(vo.getId());
        }
        project.setName(vo.getName());
        // 创建的时候需要时间
        if (type.equalsIgnoreCase(Constant.CREATE)) {
            project.setCreateDate(vo.getTimestamp());
        }

        return project;
    }


    @Override
    public List<ProjectVo> findProject(Pageable pageable, String name) {
        List<ProjectVo> projectVos = new ArrayList<>();
        Page<Project> projects = projectDao.findAll((Specification<Project>) (root, query, criteriaBuilder) -> {
            // 1. 创建集合 存储查询条件
            List<Predicate> queryList = new ArrayList<>();
            // 2. 添加查询条件
            if (StringsUtils.isNotEmpty(name)) {
                queryList.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            query.where(queryList.toArray(new Predicate[0]));
            return null;
        }, pageable);
        for (Project project : projects) {
            ProjectVo vo = convert(project);
            projectVos.add(vo);
        }
        return projectVos;
    }

    @Override
    public long findAllProjectCount() {
        return projectDao.count();
    }

    @Override
    public int findProjectCountByName(String name) {
        return projectDao.findByNameLike(name).size();
    }

    @Override
    public ProjectVo addProject(ProjectVo projectVo) {
        List<Project> projects = projectDao.findByName(projectVo.getName());
        if (projects.size() == 0) {
            Project project = convert(projectVo, Constant.CREATE);

            Project dpt = projectDao.saveAndFlush(project);
            return convert(dpt);
        }
        return null;
    }

    @Override
    public ProjectVo updateProject(ProjectVo projectVo) {
        int projectId = projectVo.getId();
        Project originProject = convert(projectVo, Constant.UPDATE);
        Optional<Project> optionalProject = projectDao.findById(projectId);
        Project project = optionalProject.orElseGet(optionalProject::get);
        ObjectUtils.copyFiledValue(originProject, project);
        projectDao.saveAndFlush(project);
        return projectVo;
    }

    @Override
    public ProjectVo deleteProject(ProjectVo projectVo) {
        Optional<Project> optionalProject = projectDao.findById(projectVo.getId());
        Project project = optionalProject.orElseGet(optionalProject::get);
        projectDao.delete(project);
        return projectVo;
    }

    @Override
    public List<ProjectVo> findProjectByName(ProjectVo projectVo) {
        String name = projectVo.getName();
        List<Project> projects = projectDao.findByName(name);
        List<ProjectVo> projectVos = new ArrayList<>();
        for (Project project : projects) {
            ProjectVo vo = convert(project);
            projectVos.add(vo);
        }
        return projectVos;
    }

    @Override
    public List<ProjectVo> findAllProject() {
        List<ProjectVo> projectVos = new ArrayList<>();
        List<Project> projects = projectDao.findAll();
        for (Project project : projects) {
            ProjectVo vo = convert(project);
            projectVos.add(vo);
        }
        return projectVos;
    }
}
