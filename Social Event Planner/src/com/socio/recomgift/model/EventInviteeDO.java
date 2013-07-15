package com.socio.recomgift.model;

import java.io.Serializable;

public class EventInviteeDO implements Serializable{

	private static final long serialVersionUID = 8194360152122226727L;
	private String inviteeId;
	private String eventId;
	private String eventName;
	private String inviteeName;
	public String getInviteeId() {
		return inviteeId;
	}
	public void setInviteeId(String inviteeId) {
		this.inviteeId = inviteeId;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getInviteeName() {
		return inviteeName;
	}
	public void setInviteeName(String inviteeName) {
		this.inviteeName = inviteeName;
	}
	
	
}
