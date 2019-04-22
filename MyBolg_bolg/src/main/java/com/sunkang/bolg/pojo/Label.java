package com.sunkang.bolg.pojo;

import javax.persistence.*;
import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="t_label")
public class Label implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private String id;//

	private String labelName;//

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabelName() {		
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

}
