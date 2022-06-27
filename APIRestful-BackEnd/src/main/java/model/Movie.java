package model;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id,ratings;
    private float star,total_star;
    private String name, director,writers,trailer,storyline,image,release_date,duration,stars;
    private  Category category;

    public Movie(){
    	super();
    }

    public Movie(float star,float total_star, String name, String director, String writers,String stars, String trailer, String storyline, String image, int ratings, String release_date, String duration) {
        this.stars = stars;
        this.name = name;
        this.director = director;
        this.writers = writers;
        this.trailer = trailer;
        this.storyline = storyline;
        this.image = image;
        this.ratings = ratings;
        this.release_date = release_date;
        this.duration = duration;
        this.star=star;
        this.total_star=total_star;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }
    

    public float getTotal_star() {
		return total_star;
	}

	public void setTotal_star(float total_star) {
		this.total_star = total_star;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}


	public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriters() {
        return writers;
    }

    public void setWriters(String writers) {
        this.writers = writers;
    }



    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getStoryline() {
        return storyline;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

