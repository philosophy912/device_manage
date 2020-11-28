package com.chinatsp.device.dao;

import com.chinatsp.device.entity.po.Department;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class DepartmentDaoTest {
    @Autowired
    private DepartmentDao dao;

    @Test
    void testDelete() {
        Optional<Department> departmentOptional = dao.findById(2);
        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();
            log.debug("department = {}", department);
            dao.delete(department);
        }
    }

}