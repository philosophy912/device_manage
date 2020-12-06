package com.chinatsp.device.service;

import com.chinatsp.device.entity.vo.GoodsVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GoodsService {
    List<GoodsVo> findGoods(Pageable pageable, String name);

    long findAllGoodsCount();

    int findGoodsCountByName(String name);

    List<GoodsVo> findGoodsByName(GoodsVo goodsVo);

    List<GoodsVo> addGoods(GoodsVo goodsVo);

    GoodsVo updateGoods(GoodsVo goodsVo);

    GoodsVo deleteGoods(GoodsVo goodsVo);
}
