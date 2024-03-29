/**
 * @author lizhe
 * @date 2020/11/26 9:43
 **/
package com.chinatsp.device.service;

import com.chinatsp.device.dao.DepartmentDao;
import com.chinatsp.device.entity.po.Department;
import com.chinatsp.device.entity.vo.DepartmentVo;
import com.chinatsp.device.utils.Constant;
import com.chinatsp.device.utils.ObjectUtils;
import com.philosophy.base.util.StringsUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentDao departmentDao;

    private DepartmentVo convert(Department department) {
        DepartmentVo vo = new DepartmentVo();
        vo.setId(department.getId());
        vo.setName(department.getName());
        vo.setTimestamp(department.getCreateDate());
        return vo;
    }

    private Department convert(DepartmentVo vo, String type) {
        Department department = new Department();
        // 更新的时候需要ID
        if (type.equalsIgnoreCase(Constant.UPDATE)) {
            department.setId(vo.getId());
        }
        department.setName(vo.getName());
        // 创建的时候需要时间
        if (type.equalsIgnoreCase(Constant.CREATE)) {
            department.setCreateDate(vo.getTimestamp());
        }
        return department;
    }


    @Override
    public List<DepartmentVo> findAllDepartment() {
        List<DepartmentVo> departmentVos = new ArrayList<>();
        List<Department> departments = departmentDao.findAll();
        for (Department department : departments) {
            DepartmentVo vo = convert(department);
            departmentVos.add(vo);
        }
        return departmentVos;
    }

    @Override
    public List<DepartmentVo> findDepartment(Pageable pageable, String name) {
        List<DepartmentVo> departmentVos = new ArrayList<>();
        Page<Department> departments = departmentDao.findAll((Specification<Department>) (root, query, criteriaBuilder) -> {
            // 1. 创建集合 存储查询条件
            List<Predicate> queryList = new ArrayList<>();
            // 2. 添加查询条件
            if (StringsUtils.isNotEmpty(name)) {
                queryList.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            query.where(queryList.toArray(new Predicate[0]));
            return null;
        }, pageable);
        for (Department department : departments) {
            DepartmentVo vo = convert(department);
            departmentVos.add(vo);
        }
        return departmentVos;
    }

    @Override
    public Long findAllDepartmentCount() {
        return departmentDao.count();
    }

    @Override
    public int findDepartmentCountByName(String name) {
        return departmentDao.findByNameLike(name).size();
    }

    @Override
    public DepartmentVo addDepartment(DepartmentVo departmentVo) {
        List<Department> departments = departmentDao.findByName(departmentVo.getName());
        if (departments.size() == 0) {
            Department department = convert(departmentVo, Constant.CREATE);
            Department dpt = departmentDao.saveAndFlush(department);
            return convert(dpt);
        }
        return null;
    }

    @Override
    public DepartmentVo updateDepartment(DepartmentVo departmentVo) {
        Department originDepartment = convert(departmentVo, Constant.UPDATE);
        Optional<Department> optionalDepartment = departmentDao.findById(departmentVo.getId());
        Department department = optionalDepartment.orElseGet(optionalDepartment::get);
        ObjectUtils.copyFiledValue(originDepartment, department);
        log.debug("update department and department is {}", department);
        departmentDao.saveAndFlush(department);
        return departmentVo;
    }

    @Override
    public DepartmentVo deleteDepartment(DepartmentVo departmentVo) {
        int id = departmentVo.getId();
        log.debug("department id = " + id);
        Optional<Department> optionalDepartment = departmentDao.findById(id);
        Department department = optionalDepartment.orElseGet(optionalDepartment::get);
        departmentDao.delete(department);
        return departmentVo;
    }

    @Override
    public List<DepartmentVo> findDepartmentByName(DepartmentVo departmentVo) {
        String name = departmentVo.getName();
        List<Department> departments = departmentDao.findByName(name);
        List<DepartmentVo> departmentVos = new ArrayList<>();
        for (Department department : departments) {
            DepartmentVo vo = convert(department);
            departmentVos.add(vo);
        }
        return departmentVos;
    }
}
