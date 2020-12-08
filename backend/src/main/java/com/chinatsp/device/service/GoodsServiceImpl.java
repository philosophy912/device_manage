package com.chinatsp.device.service;

import com.chinatsp.device.dao.EmployeeDao;
import com.chinatsp.device.dao.GoodsDao;
import com.chinatsp.device.dao.ProjectDao;
import com.chinatsp.device.entity.po.Employee;
import com.chinatsp.device.entity.po.Goods;
import com.chinatsp.device.entity.po.Project;
import com.chinatsp.device.entity.vo.GoodsVo;
import com.chinatsp.device.utils.Constant;
import com.chinatsp.device.utils.ObjectUtils;
import com.philosophy.base.util.StringsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsDao goodsDao;
    @Resource
    private EmployeeDao employeeDao;
    @Resource
    private ProjectDao projectDao;

    private GoodsVo convert(Goods goods) {
        log.debug("goods value is {}", goods);
        GoodsVo vo = new GoodsVo();
        // name/code/recipientsStatus/goodsStatus/inTime/employee/project
        Employee employee = goods.getEmployee();
        Project project = goods.getProject();
        // 设置必填项目
        vo.setId(goods.getId());
        vo.setName(goods.getName());
        vo.setCode(goods.getCode());
        vo.setRecipientsStatus(goods.getRecipientsStatus());
        vo.setGoodsStatus(goods.getGoodsStatus());
        vo.setInTime(goods.getInTime());
        if (null != goods.getEmployee()) {
            vo.setEmployeeId(employee.getId());
            vo.setEmployeeName(employee.getName());
        }
        vo.setProjectId(project.getId());
        vo.setProjectName(project.getName());
        // 设置非必填项目
        // description/image/recipientsTime/returnTime
        if (null != goods.getDescription()) {
            vo.setDescription(goods.getDescription());
        }
        if (null != goods.getImageUrl()) {
            vo.setImage(goods.getImageUrl());
        }
        if (null != goods.getRecipientsTime()) {
            vo.setRecipientsTime(goods.getRecipientsTime());
        }
        if (null != goods.getReturnTime()) {
            vo.setReturnTime(goods.getReturnTime());
        }
        return vo;
    }

    private Goods convert(GoodsVo vo, String type) {
        log.debug("goodsVo is {} and type is {}", vo, type);
        Goods goods = new Goods();
        // 更新的时候需要ID
        if (type.equalsIgnoreCase(Constant.UPDATE)) {
            goods.setId(vo.getId());
        }
        // 创建的时候需要时间
        if (type.equalsIgnoreCase(Constant.CREATE)) {
            goods.setInTime(vo.getInTime());
        }
        if (null != vo.getProjectId()) {
            int projectId = vo.getProjectId();
            Optional<Project> projectOptional = projectDao.findById(projectId);
            Project project = projectOptional.orElseGet(projectOptional::get);
            goods.setProject(project);
        }
        if (null != vo.getEmployeeId()) {
            int employeeId = vo.getEmployeeId();
            Optional<Employee> employeeOptional = employeeDao.findById(employeeId);
            Employee employee = employeeOptional.orElseGet(employeeOptional::get);
            goods.setEmployee(employee);
        }
        if (null != vo.getCode()) {
            goods.setCode(vo.getCode());
        }
        if (null != vo.getName()) {
            goods.setName(vo.getName());
        }
        if (null != vo.getRecipientsStatus()) {
            goods.setRecipientsStatus(vo.getRecipientsStatus());
        } else {
            goods.setRecipientsStatus(false);
        }
        if (null != vo.getGoodsStatus()) {
            goods.setGoodsStatus(vo.getGoodsStatus());
        }
        if (null != vo.getDescription()) {
            goods.setDescription(vo.getDescription());
        }
        if (null != vo.getImage()) {
            goods.setImageUrl(vo.getImage());
        }
        if (null != vo.getRecipientsTime()) {
            goods.setRecipientsTime(vo.getRecipientsTime());
        }
        if (null != vo.getReturnTime()) {
            goods.setReturnTime(vo.getReturnTime());
        }
        log.debug("goods is {}", goods);
        return goods;
    }


    @Override
    public List<GoodsVo> findGoods(Pageable pageable, String name) {
        List<GoodsVo> goodsVos = new ArrayList<>();
        Page<Goods> goodsList = goodsDao.findAll((Specification<Goods>) (root, query, criteriaBuilder) -> {
            // 1. 创建集合 存储查询条件
            List<Predicate> queryList = new ArrayList<>();
            // 2. 添加查询条件
            if (StringsUtils.isNotEmpty(name)) {
                queryList.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            query.where(queryList.toArray(new Predicate[0]));
            return null;
        }, pageable);
        for (Goods goods : goodsList) {
            GoodsVo vo = convert(goods);
            goodsVos.add(vo);
        }
        return goodsVos;
    }

    @Override
    public long findAllGoodsCount() {
        return goodsDao.count();
    }

    @Override
    public int findGoodsCountByName(String name) {
        return goodsDao.findByNameLike(name).size();
    }

    @Override
    public List<GoodsVo> findGoodsByName(GoodsVo goodsVo) {
        String name = goodsVo.getName();
        List<Goods> goodsList = goodsDao.findByName(name);
        List<GoodsVo> goodsVos = new ArrayList<>();
        for (Goods goods : goodsList) {
            GoodsVo vo = convert(goods);
            goodsVos.add(vo);
        }
        return goodsVos;
    }

    @Override
    public List<GoodsVo> addGoods(GoodsVo goodsVo) {
        log.debug("goodsVo = {}", goodsVo);
        String name = goodsVo.getName();
        List<GoodsVo> goodsVos = new ArrayList<>();
        List<Goods> goodsList = goodsDao.findByName(name);
        if (goodsList.size() == 0) {
            Goods goods = convert(goodsVo, Constant.CREATE);
            int count = goodsVo.getCount();
            Project project = new Project();
            project.setId(goodsVo.getProjectId());
            for (int i = 0; i < count; i++) {
                goods.setCode(UUID.randomUUID().toString());
                Goods gds = SerializationUtils.clone(goods);
                Goods dpt = goodsDao.saveAndFlush(gds);
                log.debug("goods = {}", dpt);
                goodsVos.add(convert(dpt));
            }
            return goodsVos;
        }
        return null;
    }

    @Override
    public GoodsVo updateGoods(GoodsVo goodsVo) {
        Goods goods = convert(goodsVo, Constant.UPDATE);
        Optional<Goods> optionalGoods = goodsDao.findById(goodsVo.getId());
        Goods dpt = optionalGoods.orElseGet(optionalGoods::get);
        ObjectUtils.copyFiledValue(goods, dpt);
        log.debug("dpt is {}", dpt);
        goodsDao.saveAndFlush(dpt);
        return goodsVo;
    }

    @Override
    public GoodsVo deleteGoods(GoodsVo goodsVo) {
        int id = goodsVo.getId();
        log.debug("department id = " + id);
        Optional<Goods> optionalGoods = goodsDao.findById(id);
        Goods dpt = optionalGoods.orElseGet(optionalGoods::get);
        goodsDao.delete(dpt);
        return goodsVo;
    }

    @Override
    public List<GoodsVo> findRecipientsGoods(Pageable pageable, String name) {
        List<GoodsVo> goodsVos = new ArrayList<>();
        Page<Goods> goodsList;
        if (StringsUtils.isNotEmpty(name)) {
            goodsList = goodsDao.findByRecipientsStatusIsTrueAndNameLike(pageable, "%" + name + "%");
        } else {
            goodsList = goodsDao.findByRecipientsStatusIsTrue(pageable);
        }
        goodsList.forEach(goods -> goodsVos.add(convert(goods)));
        return goodsVos;
    }

    @Override
    public List<GoodsVo> findNoneRecipientsGoods(Pageable pageable, String name) {
        List<GoodsVo> goodsVos = new ArrayList<>();
        Page<Goods> goodsList;
        if (StringsUtils.isNotEmpty(name)) {
            goodsList = goodsDao.findByRecipientsStatusIsFalseAndNameLike(pageable, "%" + name + "%");
        } else {
            goodsList = goodsDao.findByRecipientsStatusIsFalse(pageable);
        }
        goodsList.forEach(goods -> goodsVos.add(convert(goods)));
        return goodsVos;
    }

    @Override
    public int findRecipientsGoodsCount() {
        return goodsDao.findByRecipientsStatusIsTrue().size();
    }

    @Override
    public int findRecipientsGoodsCountByName(String name) {
        return goodsDao.findByRecipientsStatusIsTrueAndNameLike(name).size();
    }

    @Override
    public int findNonRecipientsGoodsCount() {
        return goodsDao.findByRecipientsStatusIsFalse().size();
    }

    @Override
    public int findNonRecipientsGoodsCountByName(String name) {
        return goodsDao.findByRecipientsStatusIsFalseAndNameLike(name).size();
    }

    @Override
    public GoodsVo recipientsGoods(GoodsVo vo) {
        Goods origin = convert(vo, Constant.UPDATE);
        Goods goods = goodsDao.findByCode(vo.getCode());
        ObjectUtils.copyFiledValue(origin, goods);
        goods.setRecipientsStatus(true);
        goods.setRecipientsTime(System.currentTimeMillis());
        goodsDao.saveAndFlush(goods);
        return vo;
    }

    @Override
    public GoodsVo returnGoods(GoodsVo vo) {
        Goods origin = convert(vo, Constant.UPDATE);
        Goods goods = goodsDao.findByCode(vo.getCode());
        ObjectUtils.copyFiledValue(origin, goods);
        goods.setRecipientsStatus(false);
        goods.setRecipientsTime(null);
        goods.setReturnTime(System.currentTimeMillis());
        goodsDao.saveAndFlush(goods);
        return vo;
    }


}
