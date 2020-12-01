package com.chinatsp.device.controller;

import com.chinatsp.device.entity.vo.EmployeeVo;
import com.chinatsp.device.entity.vo.PageResponse;
import com.chinatsp.device.entity.vo.ProjectVo;
import com.chinatsp.device.entity.vo.Response;
import com.chinatsp.device.service.ProjectService;
import com.chinatsp.device.utils.Constant;
import com.chinatsp.device.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author lizhe
 * @date 2020/11/27 16:05
 **/
@RestController
@RequestMapping("/project")
@Slf4j
public class ProjectController {

    @Resource
    private ProjectService projectService;




    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageResponse fetchList(@RequestParam int page,
                                  @RequestParam int limit,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String sort) {
        PageResponse response = new PageResponse();
        Pageable pageable;
        if (Strings.isNotEmpty(sort)){
            if (sort.equalsIgnoreCase(Constant.DESC)) {
                pageable = PageRequest.of(page - 1, limit, Sort.Direction.DESC, "id");
            } else {
                pageable = PageRequest.of(page - 1, limit, Sort.Direction.ASC, "id");
            }
        }else {
            pageable = PageRequest.of(page - 1, limit, Sort.Direction.ASC, "id");
        }
        try {
            List<ProjectVo> departments = projectService.findProject(pageable, name);
            long count = projectService.findAllProjectCount();
            response.setMessage("query success");
            response.setData(departments);
            response.setPageSize(pageable.getPageSize());
            response.setTotalRows((int) count);
            response.setTotalPages(PageUtils.get(count, pageable.getPageSize()));
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("query failed");
        }
        return response;
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public Response findAll() {
        Response response = new Response();
        try {
            List<ProjectVo> departments = projectService.findAllProject();
            response.setMessage("查询成功");
            response.setData(departments);
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("查询失败");
        }
        return response;
    }

    @RequestMapping(value = "/findName", method = RequestMethod.POST)
    public Response fetchName(@RequestBody ProjectVo projectVo) {
        Response response = new Response();
        try {
            List<ProjectVo> departments = projectService.findProjectByName(projectVo);
            response.setMessage("查询成功");
            response.setData(departments);
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("查询失败");
        }
        return response;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Response create(@RequestBody ProjectVo projectVo) {
        Response response = new Response();
        try {
            ProjectVo vo = projectService.addProject(projectVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("create success");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(projectVo.getName() + "is already in database, so create failed");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("create failed");
        }
        return response;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestBody ProjectVo projectVo) {
        Response response = new Response();
        try {
            ProjectVo vo = projectService.updateProject(projectVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("update success");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(projectVo.getName() + "is not in database, so update failed");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("update failed");
        }
        return response;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response delete(@RequestBody ProjectVo projectVo) {
        Response response = new Response();
        try {
            ProjectVo vo = projectService.deleteProject(projectVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("update success");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(projectVo.getName() + "is not in database, so delete failed");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("update failed , reason is " + e.getMessage());
        }
        return response;
    }

}
