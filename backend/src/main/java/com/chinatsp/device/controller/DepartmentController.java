package com.chinatsp.device.controller;

import com.chinatsp.device.entity.vo.DepartmentVo;
import com.chinatsp.device.entity.vo.PageResponse;
import com.chinatsp.device.entity.vo.Response;
import com.chinatsp.device.service.DepartmentService;
import com.chinatsp.device.utils.Constant;
import com.chinatsp.device.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.dao.DataIntegrityViolationException;
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

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public Response findAll() {
        Response response = new Response();
        try {
            List<DepartmentVo> departments = departmentService.findAllDepartment();
            response.setMessage("查询成功");
            response.setData(departments);
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("查询失败");
        }
        return response;
    }

    @RequestMapping(value = "/findName", method = RequestMethod.POST)
    public Response fetchName(@RequestBody DepartmentVo departmentVo) {
        Response response = new Response();
        try {
            List<DepartmentVo> departments = departmentService.findDepartmentByName(departmentVo);
            response.setMessage("查询成功");
            response.setData(departments);
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("查询失败");
        }
        return response;
    }


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
            List<DepartmentVo> departments = departmentService.findDepartment(pageable, name);
            long count = departmentService.findAllDepartmentCount();
            response.setMessage("查询成功");
            response.setData(departments);
            response.setPageSize(pageable.getPageSize());
            response.setTotalRows((int) count);
            response.setTotalPages(PageUtils.get(count, pageable.getPageSize()));
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("查询失败");
        }
        return response;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Response create(@RequestBody DepartmentVo departmentVo) {
        Response response = new Response();
        String name = departmentVo.getName();
        try {
            DepartmentVo vo = departmentService.addDepartment(departmentVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("create success");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(name + "已经在数据库中存在，无法添加");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("创建失败");
        }
        return response;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestBody DepartmentVo departmentVo) {
        Response response = new Response();
        String name = departmentVo.getName();
        try {
            DepartmentVo vo = departmentService.updateDepartment(departmentVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("更新成功");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(name + "在数据库中未查询到，请检查");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("更新失败");
        }
        return response;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response delete(@RequestBody DepartmentVo departmentVo) {
        Response response = new Response();
        String name = departmentVo.getName();
        try {
            DepartmentVo vo = departmentService.deleteDepartment(departmentVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("删除成功");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(name + "不在数据库中，无法删除");
            }
        } catch (DataIntegrityViolationException e) {
            response.setCode(Constant.NOK);
            response.setMessage("删除失败，部门" + name + "中仍然存在雇员");
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("删除失败, 原因是" + e.getMessage());
        }
        return response;
    }

}
