package com.sunkang.bolg.pojo;

import javax.persistence.*;
import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="t_topic")
public class Topic implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private String id;//


	
	private String topic_name;//

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getTopic_name() {		
		return topic_name;
	}
	public void setTopic_name(String topic_name) {
		this.topic_name = topic_name;
	}


	
}
