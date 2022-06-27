package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Category;
import model.Movie;
import model.MovieWatchList;

public class CategoryDAO extends DAO {
	SimpleDateFormat sdf;

	public CategoryDAO() {
		super();
		sdf= new SimpleDateFormat("yyyy-MM-dd");
		// TODO Auto-generated constructor stub
	}
	
	public List<Category> getAllCategory() {
		List<Category> list= new ArrayList<>();
		String sql="select * from tblcategory";
		try {
			PreparedStatement ps= con.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				int id= rs.getInt("id");
				String title= rs.getString("title");
				Category cate= new Category(id, title);
				
				sql="select * from tblmovie where category=?";
				ps= con.prepareStatement(sql);
				ps.setInt(1, cate.getId());
				ResultSet rs1= ps.executeQuery();
				while(rs1.next()) {
					id=rs1.getInt("id");
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
					
					Movie m= new Movie(star,total_star, name, director, writers, stars, trailer, storyline, image, ratings, release_date, duration);
					m.setId(id);
					//m.setCategory(cate);
					cate.getMovies().add(m);
				
				}
				list.add(cate);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		return list;
	}
	
	public int addMovieWatchList(MovieWatchList m) {
		String sql= "insert into tblwatchlist (add_time,user_id,movie_id) values (?,?,?)";
		try {
			PreparedStatement ps= con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, m.getAdd_time());
			ps.setInt(2, m.getUser().getId());
			ps.setInt(3, m.getMovie().getId());
			ps.executeUpdate();
			ResultSet rs= ps.getGeneratedKeys();
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int removeMovieWatchList(int id_movie, int id_user) {
		String sql="delete from tblwatchlist where movie_id=? and user_id=?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, id_movie);
			ps.setInt(2, id_user);
			ps.executeUpdate();
			
			return 1;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return 0;
	}

}
