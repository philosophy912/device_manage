/**
 * @author lizhe
 * @date 2020/11/26 9:58
 **/
package com.chinatsp.device.controller;

import com.chinatsp.device.entity.vo.DepartmentVo;
import com.chinatsp.device.entity.vo.PageResponse;
import com.chinatsp.device.entity.vo.Response;
import com.chinatsp.device.service.DepartmentService;
import com.chinatsp.device.utils.Constant;
import com.chinatsp.device.utils.PageUtils;
import com.philosophy.base.util.StringsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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


@RestController
@RequestMapping("/department")
@Slf4j
@Api(value = "部门管理接口", tags = {"部门管理"})
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ApiOperation(value = "查找所有的部门信息")
    public Response findAll() {
        Response response = new Response();
        try {
            List<DepartmentVo> departments = departmentService.findAllDepartment();
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
    @ApiOperation(value = "根据用户名查找部门", notes = "以departmentVo为参数，其中name不能为空")
    public Response fetchName(@ApiParam(name = "departmentVo", value = "部门模型，用于前端显示", required = true) @RequestBody DepartmentVo departmentVo) {
        Response response = new Response();
        try {
            List<DepartmentVo> departments = departmentService.findDepartmentByName(departmentVo);
            response.setMessage("查询成功");
            response.setData(departments);
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("查询失败");
            response.setErrorInfo(e.getMessage());
        }
        return response;
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "分页查找部门的列表")
    public PageResponse fetchList(@ApiParam(value = "页数", required = true, example = "1") @RequestParam int page,
                                  @ApiParam(value = "每页数量", required = true, example = "10") @RequestParam int limit,
                                  @ApiParam(value = "查询的名字", example = "部门1") @RequestParam(required = false) String name,
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
            List<DepartmentVo> departments = departmentService.findDepartment(pageable, name);
            long count;
            if (StringsUtils.isEmpty(name)) {
                count = departmentService.findAllDepartmentCount();
            } else {
                count = departmentService.findDepartmentCountByName("%" + name + "%");
            }
            response.setMessage("查询成功");
            response.setData(departments);
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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "添加部门", notes = "其中name和timestamp不能为空")
    public Response create(@RequestBody DepartmentVo departmentVo) {
        Response response = new Response();
        String name = departmentVo.getName();
        try {
            DepartmentVo vo = departmentService.addDepartment(departmentVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("创建成功");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(name + "已经在数据库中存在，无法添加");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("创建失败");
            response.setErrorInfo(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新部门", notes = "仅能更新部门名称")
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
            response.setErrorInfo(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除部门", notes = "删除部门，该部门下不能允许有员工存在")
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
            response.setMessage("删除失败，部门" + name + "中仍然存在员工");
            response.setErrorInfo(e.getMessage());
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("删除失败");
            response.setErrorInfo(e.getMessage());
        }
        return response;
    }

}
