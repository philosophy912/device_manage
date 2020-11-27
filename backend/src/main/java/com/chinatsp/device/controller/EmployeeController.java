package com.chinatsp.device.controller;

import com.chinatsp.device.entity.vo.DepartmentVo;
import com.chinatsp.device.entity.vo.EmployeeVo;
import com.chinatsp.device.entity.vo.PageResponse;
import com.chinatsp.device.entity.vo.Response;
import com.chinatsp.device.service.EmployeeService;
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

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageResponse fetchList(@RequestParam int page,
                                  @RequestParam int limit,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String sort) {
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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
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



}
