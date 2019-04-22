package com.sunkang.bolg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sunkang.bolg.pojo.Label;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface LabelDao extends JpaRepository<Label,String>,JpaSpecificationExecutor<Label>{
	
}
