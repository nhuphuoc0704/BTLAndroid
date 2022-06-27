package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{
	
	private int id;
	private String username,email,password, fullname,avatar;
	private List<Movie> listUserWatchList;
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String username, String password, String fullname,String avatar,String email) {
		super();
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.avatar=avatar;
		this.email=email;
		listUserWatchList= new ArrayList<Movie>();
	}
	public int getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public List<Movie> getListUserWatchList() {
		return listUserWatchList;
	}
	public void setListUserWatchList(List<Movie> listUserWatchList) {
		this.listUserWatchList = listUserWatchList;
	}
	
	
	
	
}
