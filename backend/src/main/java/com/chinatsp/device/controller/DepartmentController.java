package com.chinatsp.device.controller;

import com.chinatsp.device.entity.vo.DepartmentVo;
import com.chinatsp.device.entity.vo.Response;
import com.chinatsp.device.service.DepartmentService;
import com.chinatsp.device.utils.Constant;
import com.chinatsp.device.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
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
 * @date 2020/11/26 9:58
 **/
@RestController
@RequestMapping("/department")
@Slf4j
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response fetchList(@RequestParam int page, @RequestParam int limit) {
        Response response = new Response();
        Pageable pageable = PageRequest.of(page, limit, Sort.Direction.DESC, "id");
        try {
            List<DepartmentVo> departments = departmentService.findDepartment(pageable);
            long count = departmentService.findAllDepartmentCount();
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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Response create(@RequestBody DepartmentVo departmentVo) {
        Response response = new Response();
        try {
            DepartmentVo vo = departmentService.addDepartment(departmentVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("create success");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(departmentVo.getName() + "is already in database, so create failed");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("create failed");
        }
        return response;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestBody DepartmentVo departmentVo){
        Response response = new Response();
        try {
            DepartmentVo vo = departmentService.updateDepartment(departmentVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("update success");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(departmentVo.getName() + "is not in database, so update failed");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("update failed");
        }
        return response;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response delete(@RequestBody DepartmentVo departmentVo){
        Response response = new Response();
        try {
            DepartmentVo vo = departmentService.deleteDepartment(departmentVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("update success");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(departmentVo.getName() + "is not in database, so delete failed");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("update failed");
        }
        return response;
    }

}
