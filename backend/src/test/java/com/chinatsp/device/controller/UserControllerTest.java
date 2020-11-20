package com.chinatsp.device.controller;

import com.alibaba.fastjson.JSON;
import com.chinatsp.device.entity.po.User;
import com.chinatsp.device.entity.vo.Response;
import com.chinatsp.device.entity.vo.UserVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONUtil;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonbTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    String token;

    @SneakyThrows
    @Test
    void a() {
        UserVo userVo = new UserVo(1, "lizhe", "test");
        String userVoStr = JSON.toJSONString(userVo);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userVoStr))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        Response response = JSON.parseObject(result, Response.class);
        Map<String, String> data = (Map<String, String>) response.getData();
        token = data.get("token");
        log.info("token = {}", token);
    }

    @SneakyThrows
    @Test
    void b() {
        log.info("bInfo token = {}", token);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/info")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .param("token", token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        log.debug(result);
    }

    @SneakyThrows
    @Test
    void c() {
        log.info("cLogout token = {}", token);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Token", token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        log.debug(result);
    }
}