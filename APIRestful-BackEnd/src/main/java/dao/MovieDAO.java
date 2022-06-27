package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Movie;

public class MovieDAO extends DAO{
	SimpleDateFormat sdf;

	public MovieDAO() {
		super();
		sdf= new SimpleDateFormat("yyyy-MM-dd");
		// TODO Auto-generated constructor stub
	}
	public int insertMovie(Movie m) {
		String sql= "insert into tblmovie (name,director,writers,trailer,storyline,image,star,stars,ratings,duration,release_date,total_star) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, m.getName());
			ps.setString(2, m.getDirector());
			ps.setString(3, m.getWriters());
			ps.setString(4,m.getTrailer());
			ps.setString(5, m.getStoryline());
			ps.setString(6, m.getImage());
			ps.setFloat(7, m.getStar());
			ps.setString(8, m.getStars());
			ps.setInt(9, m.getRatings());
			ps.setString(10,m.getDuration());
			ps.setString(11, m.getRelease_date());
			ps.setFloat(12,m.getTotal_star());
			ps.execute();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	public boolean updateRating(Movie m, float star) {
		float total_star_new=m.getTotal_star()+star;
		int ratings_new=(m.getRatings()+1);
		float newStar=total_star_new/ratings_new;
		newStar=(float) Math.round(newStar * 10) / 10;
		m.setTotal_star(total_star_new);
		m.setRatings(ratings_new);
		m.setStar(newStar);
		
		String sql= "update tblmovie set star=?, ratings=? ,total_star=? where id=?";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setFloat(1, newStar);
			ps.setInt(2, ratings_new);
			ps.setFloat(3, total_star_new);
			ps.setInt(4, m.getId());
			ps.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	public List<Movie> searchMovie(String key){
		List<Movie> list= new ArrayList<Movie>();
		String sql="select * from tblmovie where name like ?";
		try {
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, "%"+key+"%");
			ResultSet rs1= ps.executeQuery();
			while(rs1.next()) {
				int id=rs1.getInt("id");
				String name=rs1.getString("name");
				String director= rs1.getString("director");
				String stars= rs1.getString("stars");
				float star= rs1.getFloat("star");
				String storyline=rs1.getString("storyline");
				String release_date= sdf.format(rs1.getDate("release_date")) ;
				String writers= rs1.getString("writers");
				String trailer= rs1.getString("trailer");
				String image=rs1.getString("image");
				int ratings= rs1.getInt("ratings");
				String duration= rs1.getString("duration");
				float total_star= rs1.getFloat("total_star");
				
				Movie m= new Movie(star, total_star, name, director, writers, stars, trailer, storyline, image, ratings, release_date, duration);
				m.setId(id);
				list.add(m);
				
			}
			return list;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
}
