package com.chinatsp.device.service;

import com.chinatsp.device.entity.vo.GoodsVo;
import com.philosophy.base.util.NumericUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class GoodsServiceTest {
    @Autowired
    private GoodsService goodsService;

    @Test
    void findGoods() {
    }

    @Test
    void findAllGoodsCount() {
    }

    @Test
    void findGoodsByName() {
    }

    @Test
    void addGoods() {
        for (int i = 0; i < 50; i++) {
            GoodsVo goodsVo = new GoodsVo();
            goodsVo.setGoodsStatus(true);
            goodsVo.setInTime(System.currentTimeMillis());
            goodsVo.setName("设备" + (i + 1));
            goodsVo.setRecipientsStatus(true);
            goodsVo.setProjectId(NumericUtils.randomInteger(1, 30));
            goodsVo.setCount(NumericUtils.randomInteger(1, 5));
            if (i % 2 == 0) {
                goodsVo.setEmployeeId(NumericUtils.randomInteger(1, 30));
            }
            goodsService.addGoods(goodsVo);
        }
    }

    @Test
    void updateGoods() {
    }

    @Test
    void deleteGoods() {
    }
}