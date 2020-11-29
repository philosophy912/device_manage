package com.chinatsp.device.service;

import com.chinatsp.device.dao.EmployeeDao;
import com.chinatsp.device.dao.GoodsDao;
import com.chinatsp.device.dao.ProjectDao;
import com.chinatsp.device.entity.po.Employee;
import com.chinatsp.device.entity.po.Goods;
import com.chinatsp.device.entity.po.Project;
import com.chinatsp.device.entity.vo.GoodsVo;
import com.chinatsp.device.utils.Constant;
import com.philosophy.base.util.StringsUtils;
import lombok.extern.slf4j.Slf4j;
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
        GoodsVo goodsVo = new GoodsVo();
        Employee employee = goods.getEmployee();
        Project project = goods.getProject();
        goodsVo.setId(goods.getId());
        if (null != goods.getCode()) {
            goodsVo.setCode(goods.getCode());
        }
        goodsVo.setName(goods.getName());
        goodsVo.setEmployeeId(employee.getId());
        goodsVo.setEmployeeName(employee.getName());
        goodsVo.setProjectId(project.getId());
        goodsVo.setProjectName(project.getName());
        goodsVo.setRecipientsStatus(goods.getRecipientsStatus() ? Constant.NORMAL : Constant.BREAK);
        goodsVo.setGoodsStatus(goods.getGoodsStatus() ? Constant.NORMAL : Constant.BREAK);
        goodsVo.setInTime(goods.getInTime());
        goodsVo.setRecipientsTime(goods.getRecipientsTime());
        goodsVo.setReturnTime(goods.getReturnTime());
        return goodsVo;
    }

    private Goods convert(GoodsVo goodsVo, String type) {
        Goods goods = new Goods();
        int employeeId = goodsVo.getEmployeeId();
        int projectId = goodsVo.getProjectId();
        Employee employee;
        Project project;
        Optional<Employee> employeeOptional = employeeDao.findById(employeeId);
        if (employeeOptional.isPresent()) {
            employee = employeeOptional.get();
            goods.setEmployee(employee);
        }
        Optional<Project> projectOptional = projectDao.findById(projectId);
        if (projectOptional.isPresent()) {
            project = projectOptional.get();
            goods.setProject(project);
        }
        // 更新的时候需要ID
        if (type.equalsIgnoreCase(Constant.UPDATE)) {
            goods.setId(goodsVo.getId());
        }
        goods.setCode(goodsVo.getCode());
        goods.setName(goodsVo.getName());
        if (goodsVo.getRecipientsStatus() != null) {
            goods.setRecipientsStatus(goodsVo.getRecipientsStatus().equals(Constant.NORMAL));
        }
        if (goodsVo.getGoodsStatus() != null) {
            goods.setGoodsStatus(goodsVo.getGoodsStatus().equals(Constant.NORMAL));
        }
        // 创建的时候需要时间
        if (type.equalsIgnoreCase(Constant.CREATE)) {
            goods.setInTime(goodsVo.getInTime());
        }
        goods.setInTime(goodsVo.getInTime());
        goods.setRecipientsTime(goodsVo.getRecipientsTime());
        goods.setReturnTime(goodsVo.getReturnTime());
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
        String name = goodsVo.getName();
        List<GoodsVo> goodsVos = new ArrayList<>();
        List<Goods> goodsList = goodsDao.findByName(name);
        if (goodsList.size() == 0) {
            Goods goods = convert(goodsVo, Constant.CREATE);
            int count = goodsVo.getCount();
            for (int i = 0; i < count; i++) {
                goods.setCode(UUID.randomUUID().toString());
                Goods dpt = goodsDao.saveAndFlush(goods);
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
        if (optionalGoods.isPresent()) {
            Goods dpt = optionalGoods.get();
            dpt.setName(goods.getName());
            goodsDao.saveAndFlush(dpt);
            return goodsVo;
        } else {
            return null;
        }
    }

    @Override
    public GoodsVo deleteGoods(GoodsVo goodsVo) {
        int id = goodsVo.getId();
        log.debug("department id = " + id);
        Optional<Goods> optionalGoods = goodsDao.findById(id);
        if (optionalGoods.isPresent()) {
            Goods dpt = optionalGoods.get();
            goodsDao.delete(dpt);
            return goodsVo;
        } else {
            return null;
        }
    }
}
