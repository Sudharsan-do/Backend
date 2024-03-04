package com.hackathon.Events.models;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class UserDetails {
	
	@Id
	@GeneratedValue
	private Long userId;
	private String email;
	private String hashedPass;
	@OneToMany(mappedBy = "userDetails")
	private Set<Blog> blogs;
	
	public Set<Blog> getBlogs() {
		return blogs;
	}
	public void setBlogs(Set<Blog> blogs) {
		this.blogs = blogs;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHashedPass() {
		return hashedPass;
	}
	public void setHashedPass(String hashedPass) {
		this.hashedPass = hashedPass;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
