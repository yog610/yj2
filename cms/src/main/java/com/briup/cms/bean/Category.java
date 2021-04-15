package com.briup.cms.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * 文章分类
 */
@Entity
@Table(name="cms_category")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Category implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 编号（主键）
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 *  分类名
	 */
	private String name;
	
	/**
	 *  分类描述信息
	 */
	private String description;
	
	/**
	 * 分类序号（将来可以通过序号值改变分类的排序）
	 */
	private int no;
	
	/**
	 * 当前分类所属一级分类对象（如果没有则为null）
	 * 此处注意自关联的级联删除
	 */
	@ManyToOne
	@JoinColumn(name="parent_id")
	private Category parent;
	
	@OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
	private List<Category> childen = new ArrayList<Category>();
	
	@OneToMany(mappedBy="category",cascade=CascadeType.MERGE,fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Article> articleList=new ArrayList<>();
	
	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}
	public List<Article> getArticleList() {
		return articleList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}
	/**
	 * @return the childen
	 */
	public List<Category> getChilden() {
		return childen;
	}
	/**
	 * @param childen the childen to set
	 */
	public void setChilden(List<Category> childen) {
		this.childen = childen;
	}


}
