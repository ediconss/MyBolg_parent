package com.sunkang.bolg.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.sunkang.bolg.dao.TopicDao;
import com.sunkang.bolg.pojo.Topic;

/**
 * 服务层
 *
 * @author Administrator
 *
 */
@Service
@Transactional
public class TopicService {

    @Autowired
    private TopicDao topicDao;

    /**
     * 查询全部列表
     * @return
     */
    public List<Topic> findAll() {
        return topicDao.findAll();
    }


    /**
     * 条件查询+分页
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Topic> findSearch(Map whereMap, int page, int size) {
        Specification<Topic> specification = createSpecification(whereMap);
        PageRequest pageRequest =  PageRequest.of(page-1, size);
        return topicDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     * @param whereMap
     * @return
     */
    public List<Topic> findSearch(Map whereMap) {
        Specification<Topic> specification = createSpecification(whereMap);
        return topicDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    public Topic findById(String id) {
        return topicDao.findById(id).get();
    }

    /**
     * 增加
     * @param topic
     */
    public void add(Topic topic) {

        topicDao.save(topic);
    }

    /**
     * 修改
     * @param topic
     */
    public void update(Topic topic) {
        topicDao.save(topic);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id) {
        topicDao.deleteById(id);
    }

    /**
     * 动态条件构建
     * @param searchMap
     * @return
     */
    private Specification<Topic> createSpecification(Map searchMap) {

        return new Specification<Topic>() {

            @Override
            public Predicate toPredicate(Root<Topic> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                //
                if (searchMap.get("topic_name")!=null && !"".equals(searchMap.get("topic_name"))) {
                    predicateList.add(cb.like(root.get("topic_name").as(String.class), "%"+(String)searchMap.get("topic_name")+"%"));
                }

                return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}
