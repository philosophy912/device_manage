package com.chinatsp.device.service;

import com.chinatsp.device.entity.vo.GoodsVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GoodsService {
    List<GoodsVo> findGoods(Pageable pageable, String name);

    long findAllGoodsCount();

    List<GoodsVo> findGoodsByName(GoodsVo goodsVo);

    List<GoodsVo> addGoods(GoodsVo goodsVo) throws CloneNotSupportedException;

    GoodsVo updateGoods(GoodsVo goodsVo);

    GoodsVo deleteGoods(GoodsVo goodsVo);
}
