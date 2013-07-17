package com.socio.recomgift.model;

import java.io.Serializable;
import java.util.List;


public class Friend implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -5998707380180587126L;
private String friendId;
private String friendName;
private List<FriendLike> friendLikes;
private List<FriendInterest> friendInterestsList;
private List<String> giftDescriptionList;
public List<String> getGiftDescription() {
	return giftDescriptionList;
}
public void setGiftDescription(List<String> giftDescriptionList) {
	this.giftDescriptionList = giftDescriptionList;
}
public List<FriendInterest> getFriendInterestsList() {
	return friendInterestsList;
}
public void setFriendInterestsList(List<FriendInterest> friendInterestsList) {
	this.friendInterestsList = friendInterestsList;
}
public String getFriendId() {
	return friendId;
}
public void setFriendId(String friendId) {
	this.friendId = friendId;
}
public String getFriendName() {
	return friendName;
}
public void setFriendName(String friendName) {
	this.friendName = friendName;
}
public List<FriendLike> getFriendLikes() {
	return friendLikes;
}
public void setFriendLikes(List<FriendLike> friendLikes) {
	this.friendLikes = friendLikes;
}

}
