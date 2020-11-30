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
        GoodsVo goodsVo = new GoodsVo();
        Employee employee = goods.getEmployee();
        Project project = goods.getProject();
        goodsVo.setId(goods.getId());
        if (null != goods.getCode()) {
            goodsVo.setCode(goods.getCode());
        }
        goodsVo.setImage(goods.getImageUrl());
        goodsVo.setName(goods.getName());
        goodsVo.setEmployeeId(employee.getId());
        if (employee.getName() != null) {
            goodsVo.setEmployeeName(employee.getName());
        }
        goodsVo.setProjectId(project.getId());
        if (project.getName() != null) {
            goodsVo.setProjectName(project.getName());
        }
        if (goods.getRecipientsStatus() != null) {
            goodsVo.setRecipientsStatus(goods.getRecipientsStatus() ? Constant.RECIPIENTS : Constant.NOT_RECIPIENTS);
        }
        if (goods.getGoodsStatus() != null) {
            goodsVo.setGoodsStatus(goods.getGoodsStatus() ? Constant.GOOD : Constant.BAD);
        }
        if (goods.getInTime() != null) {
            goodsVo.setInTime(goods.getInTime());
        }
        if (goods.getRecipientsTime() != null) {
            goodsVo.setRecipientsTime(goods.getRecipientsTime());
        }
        if (goods.getReturnTime() != null) {
            goodsVo.setReturnTime(goods.getReturnTime());
        }
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
        goods.setImageUrl(goodsVo.getImage());
        if (goodsVo.getRecipientsStatus() != null) {
            goods.setRecipientsStatus(goodsVo.getRecipientsStatus().equals(Constant.RECIPIENTS));
        }
        if (goodsVo.getGoodsStatus() != null) {
            goods.setGoodsStatus(goodsVo.getGoodsStatus().equals(Constant.GOOD));
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
        log.debug("goodsVo = {}", goodsVo);
        String name = goodsVo.getName();
        List<GoodsVo> goodsVos = new ArrayList<>();
        List<Goods> goodsList = goodsDao.findByName(name);
        if (goodsList.size() == 0) {
            Goods goods = convert(goodsVo, Constant.CREATE);
            int count = goodsVo.getCount();
            Project project = new Project();
            Employee employee = new Employee();
            project.setId(goodsVo.getProjectId());
            employee.setId(goodsVo.getEmployeeId());
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
            Employee employee = dpt.getEmployee();
            if(employee!=null){
                goodsDao.delete(dpt);
                return goodsVo;
            }else{
                return null;
            }
        } else {
            return null;
        }
    }
}
