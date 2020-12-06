package com.chinatsp.device.service;

import com.chinatsp.device.entity.vo.DepartmentVo;
import com.philosophy.base.util.NumericUtils;
import com.philosophy.character.api.CharEnum;
import com.philosophy.character.api.ICharFactory;
import com.philosophy.character.factory.SingleCharFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class DepartmentServiceTest {
    @Autowired
    private DepartmentService service;
    private static final Integer SIZE = 50;
    private final ICharFactory factory = new SingleCharFactory();

    @Test
    void findAllDepartment() {
        List<DepartmentVo> allDepartment = service.findAllDepartment();
        log.info("allDepartment size = {}", allDepartment.size());
    }

    @Test
    void findDepartment() {
        int limit = 20;
        Pageable pageable = PageRequest.of(0, limit, Sort.Direction.DESC, "id");
        List<DepartmentVo> department = service.findDepartment(pageable, factory.create(CharEnum.ENGLISH, 2));
        department.forEach(System.out::println);
    }

    @Test
    void findAllDepartmentCount() {
        Long count = service.findAllDepartmentCount();
        log.info("count = {}", count);
    }

    @Test
    void addDepartment() {
        for (int i = 0; i < SIZE; i++) {
            DepartmentVo vo = new DepartmentVo();
            vo.setName("部门" + (i + 1));
            vo.setTimestamp(System.currentTimeMillis());
            service.addDepartment(vo);
        }
    }

    @Test
    void updateDepartment() {
        int id = NumericUtils.randomInteger(1, SIZE);
        DepartmentVo vo = new DepartmentVo();
        vo.setId(id);
        vo.setName(factory.create(CharEnum.ENGLISH, NumericUtils.randomInteger(2, 20)));
        log.info("update vo = {}", vo);
        service.updateDepartment(vo);
    }

    @Test
    void deleteDepartment() {
        int id = NumericUtils.randomInteger(1, SIZE);
        DepartmentVo vo = new DepartmentVo();
        vo.setId(id);
        log.info("delete vo = {}", vo);
        // service.deleteDepartment(vo);
    }

    @Test
    void findDepartmentByName() {
        DepartmentVo vo = new DepartmentVo();
        vo.setName(factory.create(CharEnum.ENGLISH, NumericUtils.randomInteger(0, 2)));
        log.info("find department by name, object vo = {}", vo);
        List<DepartmentVo> departmentByName = service.findDepartmentByName(vo);
        departmentByName.forEach(System.out::println);
    }
}