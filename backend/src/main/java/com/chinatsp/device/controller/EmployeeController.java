package com.chinatsp.device.controller;

import com.chinatsp.device.entity.vo.EmployeeVo;
import com.chinatsp.device.entity.vo.PageResponse;
import com.chinatsp.device.entity.vo.Response;
import com.chinatsp.device.service.EmployeeService;
import com.chinatsp.device.utils.Constant;
import com.chinatsp.device.utils.PageUtils;
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
@RequestMapping("/employee")
@Slf4j
@Api(value = "员工管理接口", tags = {"员工管理"})
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "查找所有的员工信息")
    public PageResponse fetchList(@ApiParam(value = "页数", required = true, example = "1") @RequestParam int page,
                                  @ApiParam(value = "每页数量", required = true, example = "10") @RequestParam int limit,
                                  @ApiParam(value = "查询的名字", example = "张三") @RequestParam(required = false) String name,
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
            List<EmployeeVo> employeeVos = employeeService.findEmployee(pageable, name);
            long count = employeeService.findAllEmployeeCount();
            response.setMessage("query success");
            response.setData(employeeVos);
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
    @ApiOperation(value = "查找所有的员工", notes = "查找所有的员工")
    public Response findAll() {
        Response response = new Response();
        try {
            List<EmployeeVo> departments = employeeService.findAllEmployee();
            response.setMessage("查询成功");
            response.setData(departments);
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("查询失败");
        }
        return response;
    }


    @RequestMapping(value = "/findName", method = RequestMethod.POST)
    @ApiOperation(value = "根据员工姓名查找员工", notes = "以employeeVo中的name查找")
    public Response fetchName(@RequestBody EmployeeVo employeeVo) {
        Response response = new Response();
        try {
            List<EmployeeVo> employeeVos = employeeService.findEmployeeByName(employeeVo);
            response.setMessage("查询成功");
            response.setData(employeeVos);
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("查询失败");
        }
        return response;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "创建员工", notes = "创建员工")
    public Response create(@RequestBody EmployeeVo employeeVo) {
        Response response = new Response();
        try {
            EmployeeVo vo = employeeService.addEmployee(employeeVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("create success");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(employeeVo.getName() + "is already in database, so create failed");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("create failed");
        }
        return response;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新员工", notes = "更新员工信息")
    public Response update(@RequestBody EmployeeVo employeeVo) {
        Response response = new Response();
        try {
            EmployeeVo vo = employeeService.updateEmployee(employeeVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("update success");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(employeeVo.getName() + "is not in database, so update failed");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("update failed");
        }
        return response;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除员工", notes = "根据员工ID删除员工")
    public Response delete(@RequestBody EmployeeVo employeeVo) {
        Response response = new Response();
        try {
            EmployeeVo vo = employeeService.deleteEmployee(employeeVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("update success");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(employeeVo.getName() + "is not in database, so delete failed");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("update failed, reason is " + e.getMessage());
        }
        return response;
    }


}
