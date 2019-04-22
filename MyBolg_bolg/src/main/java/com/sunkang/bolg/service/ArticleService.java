package com.sunkang.bolg.service;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.management.RuntimeErrorException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.sunkang.bolg.dao.ArticleDao;
import com.sunkang.bolg.pojo.Article;
import com.sunkang.bolg.util.FreemarkerUtil;

/**
 * 服务层
 *
 * @author Administrator
 *
 */
@Service
@Transactional
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private FreemarkerUtil freemarkerUtil;

    /**
     * 查询全部列表
     * @return
     */
    public List<Article> findAll() {
        return articleDao.findAll();
    }

    /**
     * 条件查询+分页
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findSearch(Map whereMap, int page, int size) {
        Specification<Article> specification = createSpecification(whereMap);
        PageRequest pageRequest =  PageRequest.of(page-1, size);
        return articleDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     * @param whereMap
     * @return
     */
    public List<Article> findSearch(Map whereMap) {
        Specification<Article> specification = createSpecification(whereMap);
        return articleDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    public Article findById(String id) {
        return articleDao.findById(id).get();
    }

    /**
     * 增加
     * @param article
     */
    public String add(Article article){
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHss");
        String id=String.valueOf(format.format(new Date()));
        article.setId(id);
        article.setCreate_time(new Date());
        article.setStart(1);
        articleDao.save(article);
        freemarkerUtil.createArticle(article);
        return id;
    }

    /**
     * 修改
     * @param article
     */
    public void update(Article article) {
        articleDao.save(article);
        freemarkerUtil.createArticle(article);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id) {
        articleDao.deleteById(id);
        try {
         File file=  new File(freemarkerUtil.getTemplateFile()+id+".html");
         file.delete();  
        } catch (Exception e) {
            e.printStackTrace();
         throw new RuntimeException("删除失败");
        }
       

    }

    /**
     * 动态条件构建
     * @param searchMap
     * @return
     */
    private Specification<Article> createSpecification(Map searchMap) {

       

        return new Specification<Article>() {

            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                //
                if (searchMap.get("title")!=null && !"".equals(searchMap.get("title"))) {
                    predicateList.add(cb.like(root.get("title").as(String.class), "%"+(String)searchMap.get("title")+"%"));
                }
                //
                if (searchMap.get("summary")!=null && !"".equals(searchMap.get("summary"))) {
                    predicateList.add(cb.like(root.get("summary").as(String.class), "%"+(String)searchMap.get("summary")+"%"));
                }
                if (searchMap.get("topic")!=null && !"".equals(searchMap.get("topic"))) {
                    String[] topicList=searchMap.get("topic").toString().split(",");
                    for(int i=0;i<topicList.length;i++){
                     predicateList.add(cb.like(root.get("topic").as(String.class), "%"+topicList[i]+"%"));
                    }
             }
               
                if (searchMap.get("label")!=null && !"".equals(searchMap.get("label"))) {
                   String[] labelList=searchMap.get("label").toString().split(",");
                   for(int i=0;i<labelList.length;i++){
                    predicateList.add(cb.like(root.get("label").as(String.class), "%"+labelList[i]+"%"));
                   }
            }
            if (searchMap.get("start")!=null && !"".equals(searchMap.get("start"))) {           
                
                predicateList.add(cb.like(root.get("start").as(String.class), "%"+searchMap.get("start")+"%"));
            }

            if (searchMap.get("create_time")!=null && !"".equals(searchMap.get("create_time"))) {
                List<String> jsonstr=(List)searchMap.get("create_time");//获取到json数据中数组的那一部分
                String[] dateList= jsonstr.toArray(new String[jsonstr.size()]);
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                Date begin;
                Date end;
                try {
                   begin=format.parse(dateList[0].substring(0, 10));
                   end=format.parse(dateList[1].substring(0, 10));
                    System.out.println(begin);
                    System.out.println(end);
                } catch (Exception e) {
                   throw new RuntimeException(e.getMessage());
                }
                predicateList.add(cb.between(root.get("create_time"), begin,end ));
            }

            
               
            return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}
