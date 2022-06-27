package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable{
	private int id;
	private String title;
	private List<Movie> movies;
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Category(int id, String title) {
		super();
		this.id = id;
		this.title = title;
		movies= new ArrayList<>();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Movie> getMovies() {
		return movies;
	}
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
	
}
