/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.news.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.tisco.modules.fileupload.entity.FileUpload;
import com.weeln.common.persistence.DataEntity;
import com.weeln.common.utils.excel.annotation.ExcelField;
import com.weeln.modules.sys.entity.User;

/**
 * 文章新闻Entity
 * @author Ancle
 * @version 2016-10-04
 */
public class Article extends DataEntity<Article> {
	
	private static final long serialVersionUID = 1L;
	
	public static final String ARTICLE_ISSUE_STATUS_0 = "0"; // 草稿
	public static final String ARTICLE_ISSUE_STATUS_1 = "1"; // 待审核
	public static final String ARTICLE_ISSUE_STATUS_2 = "2"; // 被退回
	public static final String ARTICLE_ISSUE_STATUS_3 = "3"; // 待发布
	public static final String ARTICLE_ISSUE_STATUS_4 = "4"; // 已发布
	
	private String title;		// 标题
	private NewsType type;		// 类型
	private String summary;
	private String content;		// 新闻内容
	private String origin;		// 来源
	private User issuer;		// 发布人
	private String contentType;  // 新闻类型
	private String isRecommend;		// 是否推荐
	private String status;		// 状态（草稿or申请or发布）
	private String isTopline="0";//是否为头条
	private String enableCommentsOrNot = "1";//是否允许评论
	private String isPushMsg = "0"; //是否推送消息
	private int sort;      //排序
	private String picId;  //图片编号
	private String twoLevel;//二级标题
	private String audioId;//音频编号
	private String videoId;//视频编号
	private String author;//作者
	private String isFavorite;//文章是否收藏
	private Date   issuedDate;//发布日期
	private String multiStatus;
	private AuditComment auditComments;
	private String toplineFocus;//推荐焦点
	private String recommendNews;//推荐新闻
	private String countLike;//点赞次数
	private String isLikes;//是否点赞
	private String countFavorites;//收藏次数

	private List<FileUpload> fileUploads;//附件
	
	public Article() {
		super();
	}

	public Article(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public NewsType getType() {
		return type;
	}

	public void setType(NewsType type) {
		this.type = type;
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@ExcelField(title="新闻内容", align=2, sort=9)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=64, message="来源长度必须介于 0 和 64 之间")
	@ExcelField(title="来源", align=2, sort=10)
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public User getIssuer() {
		return issuer;
	}

	public void setIssuer(User issuer) {
		this.issuer = issuer;
	}

	public List<FileUpload> getFileUploads() {
		return fileUploads;
	}

	public void setFileUploads(List<FileUpload> fileUploads) {
		this.fileUploads = fileUploads;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public String getTwoLevel() {
		return twoLevel;
	}

	public void setTwoLevel(String twoLevel) {
		this.twoLevel = twoLevel;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAudioId() {
		return audioId;
	}

	public void setAudioId(String audioId) {
		this.audioId = audioId;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getIsTopline() {
		return isTopline;
	}

	public void setIsTopline(String isTopline) {
		this.isTopline = isTopline;
	}
	
	public String getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(String isFavorite) {
		this.isFavorite = isFavorite;
	}

	public String getEnableCommentsOrNot() {
		return enableCommentsOrNot;
	}

	public void setEnableCommentsOrNot(String enableCommentsOrNot) {
		this.enableCommentsOrNot = enableCommentsOrNot;
	}

	public AuditComment getAuditComments() {
		return auditComments;
	}

	public void setAuditComments(AuditComment auditComments) {
		this.auditComments = auditComments;
	}

	public String getIsPushMsg() {
		return isPushMsg;
	}

	public void setIsPushMsg(String isPushMsg) {
		this.isPushMsg = isPushMsg;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getMultiStatus() {
		return multiStatus;
	}

	public void setMultiStatus(String multiStatus) {
		this.multiStatus = multiStatus;
	}
	public String getToplineFocus() {
		return toplineFocus;
	}

	public void setToplineFocus(String toplineFocus) {
		this.toplineFocus = toplineFocus;
	}
	public String getRecommendNews() {
		return recommendNews;
	}

	public void setRecommendNews(String recommendNews) {
		this.recommendNews = recommendNews;
	}
	public String getCountLike() {
		return countLike;
	}

	public void setCountLike(String countLike) {
		this.countLike = countLike;
	}
	public String getIsLikes() {
		return isLikes;
	}

	public void setIsLikes(String isLikes) {
		this.isLikes = isLikes;
	}

	public String getCountFavorites() {
		return countFavorites;
	}

	public void setCountFavorites(String countFavorites) {
		this.countFavorites = countFavorites;
	}
}