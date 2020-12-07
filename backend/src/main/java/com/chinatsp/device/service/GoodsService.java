package com.chinatsp.device.service;

import com.chinatsp.device.entity.vo.GoodsVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface GoodsService {
    List<GoodsVo> findGoods(Pageable pageable, String name);

    long findAllGoodsCount();

    int findGoodsCountByName(String name);

    List<GoodsVo> findGoodsByName(GoodsVo goodsVo);

    List<GoodsVo> addGoods(GoodsVo goodsVo);

    GoodsVo updateGoods(GoodsVo goodsVo);

    GoodsVo deleteGoods(GoodsVo goodsVo);

    List<GoodsVo> findRecipientsGoods(Pageable pageable, String name);

    List<GoodsVo> findNoneRecipientsGoods(Pageable pageable, String name);

    int findRecipientsGoodsCount();

    int findRecipientsGoodsCountByName(String name);

    int findNonRecipientsGoodsCount();

    int findNonRecipientsGoodsCountByName(String name);
}
