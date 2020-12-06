package com.chinatsp.device.service;

import com.chinatsp.device.dao.DepartmentDao;
import com.chinatsp.device.dao.EmployeeDao;
import com.chinatsp.device.entity.po.Department;
import com.chinatsp.device.entity.po.Employee;
import com.chinatsp.device.entity.vo.EmployeeVo;
import com.chinatsp.device.utils.Constant;
import com.chinatsp.device.utils.ObjectUtils;
import com.philosophy.base.util.StringsUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeDao employeeDao;
    @Resource
    private DepartmentDao departmentDao;

    private EmployeeVo convert(Employee employee) {
        EmployeeVo vo = new EmployeeVo();
        vo.setId(employee.getId());
        vo.setName(employee.getName());
        vo.setSex(employee.getSex() ? Constant.MAN : Constant.WOMEN);
        vo.setTimestamp(employee.getCreateDate());
        vo.setDepartmentId(employee.getDepartment().getId());
        vo.setDepartmentName(employee.getDepartment().getName());
        return vo;
    }

    private Employee convert(EmployeeVo vo, String type) {
        Employee employee = new Employee();
        // 更新的时候需要ID
        if (type.equalsIgnoreCase(Constant.UPDATE)) {
            employee.setId(vo.getId());
        }
        employee.setName(vo.getName());
        // 创建的时候需要时间
        if (type.equalsIgnoreCase(Constant.CREATE)) {
            employee.setCreateDate(vo.getTimestamp());
        }
        employee.setSex(vo.getSex().equalsIgnoreCase(Constant.MAN));
        Optional<Department> optionalDepartment = departmentDao.findById(vo.getDepartmentId());
        employee.setDepartment(optionalDepartment.orElseGet(employee::getDepartment));
        return employee;
    }


    @Override
    public List<EmployeeVo> findEmployee(Pageable pageable, String name) {
        List<EmployeeVo> employeeVos = new ArrayList<>();
        Page<Employee> employees = employeeDao.findAll((Specification<Employee>) (root, query, criteriaBuilder) -> {
            // 1. 创建集合 存储查询条件
            List<Predicate> queryList = new ArrayList<>();
            // 2. 添加查询条件
            if (StringsUtils.isNotEmpty(name)) {
                queryList.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            query.where(queryList.toArray(new Predicate[0]));
            return null;
        }, pageable);
        for (Employee employee : employees) {
            EmployeeVo vo = convert(employee);
            employeeVos.add(vo);
        }
        return employeeVos;
    }

    @Override
    public long findAllEmployeeCount() {
        return employeeDao.count();
    }

    @Override
    public int findEmployeeCountByName(String name) {
        return employeeDao.findByNameLike(name).size();
    }

    @Override
    public EmployeeVo addEmployee(EmployeeVo employeeVo) {
        List<Employee> employees = employeeDao.findByName(employeeVo.getName());
        if (employees.size() == 0) {
            Employee employee = convert(employeeVo, Constant.CREATE);
            Employee dpt = employeeDao.saveAndFlush(employee);
            return convert(dpt);
        }
        return null;
    }

    @Override
    public EmployeeVo updateEmployee(EmployeeVo employeeVo) {
        Employee originEmployee = convert(employeeVo, Constant.UPDATE);
        Optional<Employee> optionalEmployee = employeeDao.findById(employeeVo.getId());
        Employee employee = optionalEmployee.orElseGet(optionalEmployee::get);
        ObjectUtils.copyFiledValue(originEmployee, employee);
        employeeDao.saveAndFlush(employee);
        return employeeVo;

    }

    @Override
    public EmployeeVo deleteEmployee(EmployeeVo employeeVo) {
        Optional<Employee> optionalEmployee = employeeDao.findById(employeeVo.getId());
        Employee employee = optionalEmployee.orElseGet(optionalEmployee::get);
        employeeDao.delete(employee);
        return employeeVo;
    }

    @Override
    public List<EmployeeVo> findEmployeeByName(EmployeeVo employeeVo) {
        String name = employeeVo.getName();
        List<Employee> employees = employeeDao.findByName(name);
        List<EmployeeVo> employeeVos = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeVo vo = convert(employee);
            employeeVos.add(vo);
        }
        return employeeVos;
    }

    @Override
    public List<EmployeeVo> findAllEmployee() {
        List<EmployeeVo> employeeVos = new ArrayList<>();
        List<Employee> employees = employeeDao.findAll();
        for (Employee employee : employees) {
            EmployeeVo vo = convert(employee);
            employeeVos.add(vo);
        }
        return employeeVos;
    }


}
