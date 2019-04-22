package com.sunkang.bolg.controller;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sunkang.bolg.pojo.Topic;
import com.sunkang.bolg.service.TopicService;

import com.sunkang.bolg.entity.*;
import com.sunkang.bolg.util.*;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/topic")
public class TopicController {

	@Autowired
	private TopicService topicService;
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",topicService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",topicService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Topic> pageList = topicService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Topic>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",topicService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param topic
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Topic topic  ){
		String token=(String) request.getAttribute("claims_admin");
		if(token==null|| "".equals(token)){
			return new Result(false,StatusCode.ERROR,"权限不足");
		}
		topicService.add(topic);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param topic
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Topic topic, @PathVariable String id ){
		String token=(String) request.getAttribute("claims_admin");
		if(token==null|| "".equals(token)){
			return new Result(false,StatusCode.ERROR,"权限不足");
		}
		topic.setId(id);
		topicService.update(topic);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		String token=(String) request.getAttribute("claims_admin");
		if(token==null|| "".equals(token)){
			return new Result(false,StatusCode.ERROR,"权限不足");
		}
		topicService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
