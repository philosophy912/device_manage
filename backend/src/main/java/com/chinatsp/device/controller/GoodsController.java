package com.chinatsp.device.controller;

import com.chinatsp.device.entity.vo.GoodsVo;
import com.chinatsp.device.entity.vo.PageResponse;
import com.chinatsp.device.entity.vo.Response;
import com.chinatsp.device.service.GoodsService;
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
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

    @Resource
    private GoodsService goodsService;

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
            List<GoodsVo> goodsVos = goodsService.findGoods(pageable, name);
            long count = goodsService.findAllGoodsCount();
            response.setMessage("查询成功");
            response.setData(goodsVos);
            response.setPageSize(pageable.getPageSize());
            response.setTotalRows((int) count);
            response.setTotalPages(PageUtils.get(count, pageable.getPageSize()));
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("查询失败");
        }
        return response;
    }

    @RequestMapping(value = "/findName", method = RequestMethod.POST)
    public Response fetchName(@RequestBody GoodsVo goodsVo) {
        Response response = new Response();
        try {
            List<GoodsVo> departments = goodsService.findGoodsByName(goodsVo);
            response.setMessage("查询成功");
            response.setData(departments);
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("查询失败");
        }
        return response;
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Response create(@RequestBody GoodsVo goodsVo) {
        Response response = new Response();
        try {
            List<GoodsVo> goodsVos = goodsService.addGoods(goodsVo);
            if(goodsVos != null){
                response.setData(goodsVos);
                response.setMessage("create success");
            }else{
                response.setCode(Constant.NOK);
                response.setMessage("create failed, reason is " + goodsVo.getName() + " exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(Constant.NOK);
            response.setMessage("create failed, reason is " + e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestBody GoodsVo goodsVo) {
        Response response = new Response();
        try {
            GoodsVo vo = goodsService.updateGoods(goodsVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("update success");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(goodsVo.getName() + "is not in database, so update failed");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("update failed");
        }
        return response;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response delete(@RequestBody GoodsVo goodsVo) {
        Response response = new Response();
        try {
            GoodsVo vo = goodsService.deleteGoods(goodsVo);
            if (vo != null) {
                response.setData(Collections.singletonList(vo));
                response.setMessage("update success");
            } else {
                response.setCode(Constant.NOK);
                response.setMessage(goodsVo.getName() + "is not in database, so delete failed");
            }
        } catch (Exception e) {
            response.setCode(Constant.NOK);
            response.setMessage("update failed, reason is " + e.getMessage());
        }
        return response;
    }

}
