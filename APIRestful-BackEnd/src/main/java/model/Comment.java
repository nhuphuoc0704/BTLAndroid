package model;

import java.io.Serializable;

public class Comment implements Serializable{
	private int id;
	private float star;
	private String title,content,created_time;
	private User user;
	private Movie movie;
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Comment(float star, String title, String content,String created_time) {
		super();
		this.star = star;
		this.title = title;
		this.content = content;
		this.created_time=created_time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getStar() {
		return star;
	}
	public void setStar(float star) {
		this.star = star;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	
	

}
