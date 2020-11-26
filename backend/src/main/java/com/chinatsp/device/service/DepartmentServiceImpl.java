package com.chinatsp.device.service;

import com.chinatsp.device.dao.DepartmentDao;
import com.chinatsp.device.entity.po.Department;
import com.chinatsp.device.entity.vo.DepartmentVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhe
 * @date 2020/11/26 9:43
 **/
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private DepartmentDao departmentDao;

    private DepartmentVo convert(Department department){
        DepartmentVo vo = new DepartmentVo();
        vo.setId(department.getId());
        vo.setName(department.getName());
        vo.setCreateDate(department.getCreateDate());
        return vo;
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
    public List<DepartmentVo> findDepartment(int pageNo, int size) {
        List<DepartmentVo> departmentVos = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<Department> departments = departmentDao.findAll(pageable, sort);
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
}
