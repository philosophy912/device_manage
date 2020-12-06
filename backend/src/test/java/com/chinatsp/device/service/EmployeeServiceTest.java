package com.chinatsp.device.service;

import com.chinatsp.device.entity.po.Department;
import com.chinatsp.device.entity.po.Employee;
import com.chinatsp.device.entity.vo.EmployeeVo;
import com.chinatsp.device.utils.Constant;
import com.philosophy.base.util.NumericUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class EmployeeServiceTest {
    @Autowired
    private EmployeeService service;

    @Test
    void findEmployee() {
    }

    @Test
    void findAllEmployeeCount() {
    }

    @Test
    void addEmployee() {
        for (int i = 0; i < 50; i++) {
            EmployeeVo vo = new EmployeeVo();
            vo.setName("员工" + (i + 1));
            vo.setTimestamp(System.currentTimeMillis());
            vo.setSex(i % 2 == 0 ? Constant.MAN : Constant.WOMEN);
            vo.setDepartmentId(NumericUtils.randomInteger(1, 50));
            service.addEmployee(vo);
        }
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void deleteEmployee() {
    }

    @Test
    void findEmployeeByName() {
    }

    @Test
    void findAllEmployee() {
    }
}