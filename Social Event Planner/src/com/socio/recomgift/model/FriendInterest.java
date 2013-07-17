package com.socio.recomgift.model;

import java.io.Serializable;


public class FriendInterest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String interestId;
	private String interestCategory;
	private String interestName;
	public String getInterestId() {
		return interestId;
	}
	public void setInterestId(String interestId) {
		this.interestId = interestId;
	}
	public String getInterestCategory() {
		return interestCategory;
	}
	public void setInterestCategory(String interestCategory) {
		this.interestCategory = interestCategory;
	}
	public String getInterestName() {
		return interestName;
	}
	public void setInterestName(String interestName) {
		this.interestName = interestName;
	}
	
}
