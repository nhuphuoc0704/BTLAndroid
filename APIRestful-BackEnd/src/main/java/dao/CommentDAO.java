package dao;

import java.util.ArrayList;
import java.util.List;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import model.Comment;
import model.Movie;
import model.User;

public class CommentDAO extends DAO{
	private SimpleDateFormat sdf;

	public CommentDAO() {
		super();
		// TODO Auto-generated constructor stub
		sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	public List<Comment> getAllCommentOfMovie(int i){
		List<Comment> list= new ArrayList<Comment>();
		String sql="{call getAllCommentOfMovie(?)}";
		try {
			CallableStatement ps= con.prepareCall(sql);
			ps.setInt(1, i);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				int id= rs.getInt("id");
				String title=rs.getString("title");
				String content= rs.getString("content");
				String time_create=sdf.format(rs.getDate("created_time"));
				float star= rs.getFloat("star");
				
				Comment c= new Comment(star, title, content, time_create);
				c.setId(id);
				//c.setMovie(movie);
				
				id= rs.getInt("iduser");
				String username= rs.getString("username");
				String password= rs.getString("password");
				String email=rs.getString("email");
				String avatar= rs.getString("avatar");
				String fullname = rs.getString("fullname");
				User u= new  User(username, password, fullname, avatar, email);
				u.setId(id);
				
				c.setUser(u);
				list.add(c);
				
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public Comment addRating(Comment c) {
		String sql="insert into tblcomment (title,content,created_time,star,user,movie) values (?,?,?,?,?,?)";
		try {
			PreparedStatement ps= con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, c.getTitle());
			ps.setString(2,c.getContent());
			ps.setString(3, c.getCreated_time());
			ps.setFloat(4, c.getStar());
			ps.setInt(5, c.getUser().getId());
			ps.setInt(6, c.getMovie().getId());
			ps.executeUpdate();
			ResultSet rs= ps.getGeneratedKeys();
			Movie m= c.getMovie();
			if(rs.next() && new MovieDAO().updateRating(m, c.getStar())) {
				c.setMovie(m);
				return c;
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public Comment editComment(Comment c,float oldStar) {
		float oldTotalStar= c.getMovie().getTotal_star();
		float newTotalStar= oldTotalStar- oldStar+ c.getStar();
		float newStar= newTotalStar/c.getMovie().getRatings();
		c.getMovie().setTotal_star(newTotalStar);
		c.getMovie().setStar(newStar);
		String sql= "update tblmovie set star=? , total_star=? where id=?";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setFloat(1, newStar);
			ps.setFloat(2, newTotalStar);
			ps.setInt(3,c.getMovie().getId());
			ps.executeUpdate();
			
			sql= "update tblcomment set star=?, title=?, content=? where id=?";
			ps=con.prepareStatement(sql);
			ps.setFloat(1,c.getStar());
			ps.setString(2, c.getTitle());
			ps.setString(3, c.getContent());
			ps.setInt(4, c.getId());
			ps.executeUpdate();
			return c;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
}
