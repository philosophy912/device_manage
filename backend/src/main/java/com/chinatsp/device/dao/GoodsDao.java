package com.chinatsp.device.dao;

import com.chinatsp.device.entity.po.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/11/19 18:03
 **/
public interface GoodsDao extends JpaRepository<Goods, Integer>, JpaSpecificationExecutor<Goods> {
    List<Goods> findByName(String name);

    List<Goods> findByNameLike(String name);

}
