package com.chinatsp.device.dao;

import com.chinatsp.device.entity.po.Goods;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class GoodsDaoTest {
    @Autowired
    private GoodsDao goodsDao;

    @Test
    void findByRecipientsStatusIsTrue() {
        int limit = 10;
        Pageable pageable = PageRequest.of(0, limit, Sort.Direction.ASC, "id");
        Page<Goods> goodsList;
//        goodsList = goodsDao.findByRecipientsStatusIsTrue(pageable);
//        log.info("size = " + goodsList.getSize());
        goodsList = goodsDao.findByRecipientsStatusIsTrueAndNameLike(pageable, "%设备4%");
        goodsList.forEach(System.out::println);
    }

    @Test
    void findByRecipientsStatusIsFalse() {
    }
}