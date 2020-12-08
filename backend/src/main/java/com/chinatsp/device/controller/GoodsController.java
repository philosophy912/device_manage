package com.chinatsp.device.controller;

import com.chinatsp.device.entity.vo.GoodsVo;
import com.chinatsp.device.entity.vo.PageResponse;
import com.chinatsp.device.entity.vo.Response;
import com.chinatsp.device.service.GoodsService;
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
@RequestMapping("/goods")
@Slf4j
@Api(value = "设备管理接口", tags = {"设备管理"})
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "查找所有设备")
    public PageResponse fetchList(@ApiParam(value = "页数", required = true, example = "1") @RequestParam int page,
                                  @ApiParam(value = "每页数量", required = true, example = "10") @RequestParam int limit,
                                  @ApiParam(value = "查询的名字", example = "电脑") @RequestParam(required = false) String name,
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
            List<GoodsVo> goodsVos = goodsService.findGoods(pageable, name);
            long count;
            if (StringsUtils.isEmpty(name)) {
                count = goodsService.findAllGoodsCount();
            } else {
                count = goodsService.findGoodsCountByName("%" + name + "%");
            }
            response.setMessage("查询成功");
            response.setData(goodsVos);
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

    @RequestMapping(value = "/list/recipients", method = RequestMethod.GET)
    @ApiOperation(value = "查找所有设备")
    public PageResponse fetchRecipients(@ApiParam(value = "页数", required = true, example = "1") @RequestParam int page,
                                        @ApiParam(value = "每页数量", required = true, example = "10") @RequestParam int limit,
                                        @ApiParam(value = "查询的名字", example = "电脑") @RequestParam(required = false) String name,
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
            List<GoodsVo> goodsVos = goodsService.findRecipientsGoods(pageable, name);
            long count;
            if (StringsUtils.isEmpty(name)) {
                count = goodsService.findRecipientsGoodsCount();
            } else {
                count = goodsService.findRecipientsGoodsCountByName("%" + name + "%");
            }
            response.setMessage("查询成功");
            response.setData(goodsVos);
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

    @RequestMapping(value = "/list/none/Recipients", method = RequestMethod.GET)
    @ApiOperation(value = "查找所有设备")
    public PageResponse fetchNonRecipients(@ApiParam(value = "页数", required = true, example = "1") @RequestParam int page,
                                           @ApiParam(value = "每页数量", required = true, example = "10") @RequestParam int limit,
                                           @ApiParam(value = "查询的名字", example = "电脑") @RequestParam(required = false) String name,
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
            List<GoodsVo> goodsVos = goodsService.findNoneRecipientsGoods(pageable, name);
            long count;
            if (StringsUtils.isEmpty(name)) {
                count = goodsService.findNonRecipientsGoodsCount();
            } else {
                count = goodsService.findNonRecipientsGoodsCountByName("%" + name + "%");
            }
            response.setMessage("查询成功");
            response.setData(goodsVos);
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


    @RequestMapping(value = "/findName", method = RequestMethod.POST)
    @ApiOperation(value = "根据名字查找设备", notes = "根据名字查找设备goodsVo中的name必填")
    public Response fetchName(@RequestBody GoodsVo goodsVo) {
        Response response = new Response();
        try {
            List<GoodsVo> departments = goodsService.findGoodsByName(goodsVo);
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
    @ApiOperation(value = "创建设备", notes = "创建设备")
    public Response create(@RequestBody GoodsVo goodsVo) {
        Response response = new Response();
        try {
            List<GoodsVo> goodsVos = goodsService.addGoods(goodsVo);
            if (goodsVos != null) {
                response.setData(goodsVos);
                response.setMessage("创建成功");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage("创建失败，" + goodsVo.getName() + "已存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(Constant.NOK);
            response.setMessage("创建失败");
            response.setErrorInfo(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新设备", notes = "更新设备信息")
    public Response update(@RequestBody GoodsVo goodsVo) {
        Response response = new Response();
        try {
            GoodsVo vo = goodsService.updateGoods(goodsVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("更新成功");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(goodsVo.getName() + "is not in database, so update failed");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("更新失败");
            response.setErrorInfo(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除设备", notes = "根据员工ID删除设备")
    public Response delete(@RequestBody GoodsVo goodsVo) {
        Response response = new Response();
        try {
            GoodsVo vo = goodsService.deleteGoods(goodsVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("删除成功");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(goodsVo.getName() + "不存在，无法删除");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("删除失败, " + goodsVo.getName() + "仍然在使用，请检查");
            response.setErrorInfo(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/recipients", method = RequestMethod.POST)
    @ApiOperation(value = "借出设备", notes = "根据设备Code借出设备")
    public Response recipients(@RequestBody GoodsVo goodsVo) {
        Response response = new Response();
        try {
            GoodsVo vo = goodsService.recipientsGoods(goodsVo);
            response.setData(Collections.singletonList(vo));
            response.setMessage("借出成功");
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("借出失败");
            response.setErrorInfo(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/return", method = RequestMethod.POST)
    @ApiOperation(value = "归还设备", notes = "根据设备Code归还设备")
    public Response returnGoods(@RequestBody GoodsVo goodsVo) {
        Response response = new Response();
        try {
            GoodsVo vo = goodsService.returnGoods(goodsVo);
            response.setData(Collections.singletonList(vo));
            response.setMessage("归还成功");
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("归还失败");
            response.setErrorInfo(e.getMessage());
        }
        return response;
    }

}
