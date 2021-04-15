package com.briup.cms.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.briup.cms.util.DateJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * 评论
 */
@Entity
@Table(name="cms_comment")
public class Comment implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 编号（主键）
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 评论内容
	 */
	private String content;
	
	/**
	 * 评论时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date time;
	
	/**
	 * 评论人
	 */
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	/**
	 * 评论的文章
	 */
	@ManyToOne
	@JoinColumn(name="article_id")
	private Article article;
	
	
	/**
	 * 该评论的回复
	 * 级联删除评论
	 */
	@ManyToOne
	@JoinColumn(name="parent_id")
	private Comment parent;
	
	@OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
	private List<Comment> childen = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonSerialize(using = DateJsonSerializer.class)
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	/**
	 * @return the childen
	 */
	public List<Comment> getChilden() {
		return childen;
	}
	/**
	 * @param childen the childen to set
	 */
	public void setChilden(List<Comment> childen) {
		this.childen = childen;
	}

}
