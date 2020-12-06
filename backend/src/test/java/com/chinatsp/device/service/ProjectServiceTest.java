package com.chinatsp.device.service;

import com.chinatsp.device.entity.vo.ProjectVo;
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
class ProjectServiceTest {
    @Autowired
    private ProjectService service;

    @Test
    void findProject() {
    }

    @Test
    void findAllProjectCount() {
    }

    @Test
    void addProject() {
        for (int i = 0; i < 50; i++) {
            ProjectVo vo = new ProjectVo();
            vo.setName("项目" + (i + 1));
            vo.setTimestamp(System.currentTimeMillis());
            service.addProject(vo);
        }
    }

    @Test
    void updateProject() {
    }

    @Test
    void deleteProject() {
    }

    @Test
    void findProjectByName() {
    }

    @Test
    void findAllProject() {
    }
}