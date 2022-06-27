package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import java.sql.CallableStatement;

import model.Movie;
import model.User;

public class UserDAO extends DAO {
	private SimpleDateFormat sdf;
	public UserDAO() {
		super();
		sdf= new SimpleDateFormat("yyyy-MM-dd");
	}
	
	public List<User> getAllUser(){
		List<User> list= new ArrayList<User>();
		String sql= "select * from tbluser";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				int id= rs.getInt("id");
				String username=rs.getString("username");
				String password= rs.getString("password");
				String fullname= rs.getString("fullname");
				String email=rs.getString("email");
				String avatar=rs.getString("avatar");
				User u= new User(username, password, fullname, avatar, email);
				u.setId(id);
				list.add(u);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return list;
	}
	
	public User createUser(User u) {
		String sql="insert into tbluser (username,password,fullname,email,avatar) values (?,?,?,?,?)";
		try {
			PreparedStatement ps= con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, u.getUsername());
			ps.setString(2,u.getPassword());
			ps.setString(3, u.getFullname());
			ps.setString(4, u.getEmail());
			ps.setString(5, u.getAvatar());
			ps.executeUpdate();
			ResultSet rs= ps.getGeneratedKeys();
			if(rs.next()) u.setId(rs.getInt(1));
			return u;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public User getUser(User u) {
		String sql= "select * from tbluser where username=? and password=?";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,u.getUsername());
			ps.setString(2,u.getPassword());
			ResultSet rs= ps.executeQuery();
			if(rs.next()) {
				int id= rs.getInt("id");
				String avatar=rs.getString("avatar");
				String fullname=rs.getString("fullname");
				String email=rs.getString("email");
				User user= new User(u.getUsername(), u.getPassword(), fullname, avatar, email);
				user.setId(id);
				return user;
			}
			else {
				createUser(u);
				return u;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public User checkLogin(String username,String password) {
		String sql="{call check_login(?,?)}";
		try {
			CallableStatement ps=con.prepareCall(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs= ps.executeQuery();
			if(rs.next()) {
				int id= rs.getInt("id");
				String fullname= rs.getString("fullname");
				String email=rs.getString("email");
				String avatar=rs.getString("avatar");
				User user= new User(username, password, fullname, avatar, email);
				user.setId(id);
				getUserWatchList(user);
				return user;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	private void getUserWatchList(User user) {
		List<Movie> list=new ArrayList<Movie>();
		// TODO Auto-generated method stub
		String sql= "{call get_user_watch_list(?)}";
		try {
			CallableStatement ps=con.prepareCall(sql);
			ps.setInt(1, user.getId());
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
			
			user.setListUserWatchList(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public User registry(User u) {
		String sql="select * from tbluser where username=?";
		try {
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1,u.getUsername());
			ResultSet rs= ps.executeQuery();
			if(rs.next()) {
				return null;
			}
			createUser(u);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return u;
	}
	
	public User loginAsGoogleAccount(User u) {
		String sql="select id from tbluser where username=?";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,u.getUsername());
			ps.execute();
			ResultSet rs= ps.executeQuery();
			if(rs.next()) {
				int id= rs.getInt("id");
				u.setId(id);
				return u;
			}
			return createUser(u);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int updateAvatar(User u) {
		String sql= "update tbluser set avatar=? where id=?";
		try {
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, u.getAvatar());
			ps.setInt(2, u.getId());
			ps.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return 0;
	}
	
	
	
	
}
