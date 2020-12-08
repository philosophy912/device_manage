package com.chinatsp.device.dao;

import com.chinatsp.device.entity.po.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<Goods> findByRecipientsStatusIsTrue(Pageable pageable);

    Page<Goods> findByRecipientsStatusIsTrueAndNameLike(Pageable pageable, String name);

    List<Goods> findByRecipientsStatusIsTrueAndNameLike(String name);

    List<Goods> findByRecipientsStatusIsTrue();

    Page<Goods> findByRecipientsStatusIsFalse(Pageable pageable);

    List<Goods> findByRecipientsStatusIsFalseAndNameLike(String name);

    Page<Goods> findByRecipientsStatusIsFalseAndNameLike(Pageable pageable, String name);

    List<Goods> findByRecipientsStatusIsFalse();

    Goods findByCode(String code);

}
