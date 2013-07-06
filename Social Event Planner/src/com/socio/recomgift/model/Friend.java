package com.socio.recomgift.model;

import java.util.List;


public class Friend {
private String friendId;
private String friendName;
private List<FriendLike> friendLikes;
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
