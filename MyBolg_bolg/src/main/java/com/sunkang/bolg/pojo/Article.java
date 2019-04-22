package com.sunkang.bolg.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="t_article")
public class Article implements Serializable{

	@Id
	private String id;//
	
	private String title;//
	private String summary;//
	private String content;//
	private String cover;//
	private Date create_time;//
	private String label;//
	private String topic;//
	private Integer start;//
	private String founder;

	public String getId() {
		return id;
	}

	/**
	 * @return the cover
	 */
	public String getCover() {
		return cover;
	}

	/**
	 * @param cover the cover to set
	 */
	public void setCover(String cover) {
		this.cover = cover;
	}

	/**
	 * @return the founder
	 */
	public String getFounder() {
		return founder;
	}

	/**
	 * @param founder the founder to set
	 */
	public void setFounder(String founder) {
		this.founder = founder;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {		
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {		
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Integer getStart() {		
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}


	
}
