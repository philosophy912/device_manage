package com.chinatsp.device.controller;

import com.chinatsp.device.entity.vo.DepartmentVo;
import com.chinatsp.device.entity.vo.Response;
import com.chinatsp.device.service.DepartmentService;
import com.chinatsp.device.utils.Constant;
import com.chinatsp.device.utils.PageUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lizhe
 * @date 2020/11/26 9:58
 **/
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @RequestMapping(value = "/fetchList", method = RequestMethod.GET)
    public Response fetchList(@RequestParam String page, @RequestParam String limit) {
        Response response = new Response();
        try {
            int pageNo = Integer.parseInt(page);
            int pageSize = Integer.parseInt(limit);
            List<DepartmentVo> departments = departmentService.findDepartment(pageNo, pageSize);
            long count = departmentService.findAllDepartmentCount();
            response.setMessage("query success");
            response.setData(departments);
            response.setPageSize(pageSize);
            response.setTotalRows((int) count);
            response.setTotalPages(PageUtils.get(count, pageSize));
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("query failed");
        }
        return response;
    }

}
