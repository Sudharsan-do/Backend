package com.hackathon.Events.forms;

public class BlogForm {
	
	private String id;
	private String name;
	private String content;
	private String scheduleAt;
	private Boolean scheduleFlag;
	private String url;
	private String status;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScheduleAt() {
		return scheduleAt;
	}
	public void setScheduleAt(String scheduleAt) {
		this.scheduleAt = scheduleAt;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean getScheduleFlag() {
		return scheduleFlag;
	}
	public void setScheduleFlag(Boolean scheduleFlag) {
		this.scheduleFlag = scheduleFlag;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
