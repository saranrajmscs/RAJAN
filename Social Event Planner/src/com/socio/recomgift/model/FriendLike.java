package com.socio.recomgift.model;

import java.io.Serializable;

public class FriendLike implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String likeCategory;
	private String likeName;
	private String likeId;
	
	public String getLikeCategory() {
		return likeCategory;
	}
	public void setLikeCategory(String likeCategory) {
		this.likeCategory = likeCategory;
	}
	public String getLikeName() {
		return likeName;
	}
	public void setLikeName(String likeName) {
		this.likeName = likeName;
	}
	public String getLikeId() {
		return likeId;
	}
	public void setLikeId(String likeId) {
		this.likeId = likeId;
	}
}
