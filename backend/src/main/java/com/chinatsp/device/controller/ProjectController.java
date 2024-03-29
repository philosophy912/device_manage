/**
 * @author lizhe
 * @date 2020/11/27 16:05
 **/
package com.chinatsp.device.controller;

import com.chinatsp.device.entity.vo.PageResponse;
import com.chinatsp.device.entity.vo.ProjectVo;
import com.chinatsp.device.entity.vo.Response;
import com.chinatsp.device.service.ProjectService;
import com.chinatsp.device.utils.Constant;
import com.chinatsp.device.utils.PageUtils;
import com.philosophy.base.util.StringsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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


@RestController
@RequestMapping("/project")
@Slf4j
@Api(value = "项目管理接口", tags = {"项目管理"})
public class ProjectController {

    @Resource
    private ProjectService projectService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "查找所有项目")
    public PageResponse fetchList(@ApiParam(value = "页数", required = true, example = "1") @RequestParam int page,
                                  @ApiParam(value = "每页数量", required = true, example = "10") @RequestParam int limit,
                                  @ApiParam(value = "查询的名字", example = "xx项目") @RequestParam(required = false) String name,
                                  @ApiParam(value = "排序方式", example = "+id/-id") @RequestParam(required = false) String sort) {
        PageResponse response = new PageResponse();
        Pageable pageable;
        if (Strings.isNotEmpty(sort)) {
            if (sort.equalsIgnoreCase(Constant.DESC)) {
                pageable = PageRequest.of(page - 1, limit, Sort.Direction.DESC, "id");
            } else {
                pageable = PageRequest.of(page - 1, limit, Sort.Direction.ASC, "id");
            }
        } else {
            pageable = PageRequest.of(page - 1, limit, Sort.Direction.ASC, "id");
        }
        try {
            List<ProjectVo> projectVos = projectService.findProject(pageable, name);
            long count;
            if (StringsUtils.isEmpty(name)) {
                count = projectService.findAllProjectCount();
            } else {
                count = projectService.findProjectCountByName("%" + name + "%");
            }
            response.setMessage("查询成功");
            response.setData(projectVos);
            response.setPageSize(pageable.getPageSize());
            response.setTotalRows((int) count);
            response.setTotalPages(PageUtils.get(count, pageable.getPageSize()));
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("查询失败");
            response.setErrorInfo(e.getMessage());
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
            response.setErrorInfo(e.getMessage());
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
            response.setErrorInfo(e.getMessage());
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
                response.setMessage("创建成功");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(projectVo.getName() + "已存在，无法创建");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("创建失败");
            response.setErrorInfo(e.getMessage());
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
                response.setMessage("更新成功");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(projectVo.getName() + "不在数据库，无法删除");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("更新失败");
            response.setErrorInfo(e.getMessage());
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
                response.setMessage("删除成功");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(projectVo.getName() + "在数据库中不存在，请检查");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("删除失败");
            response.setErrorInfo(e.getMessage());
        }
        return response;
    }

}
