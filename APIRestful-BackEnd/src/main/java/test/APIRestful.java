package test;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.process.internal.RequestScoped;

import org.glassfish.jersey.media.multipart.FormDataParam;  
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import dao.CategoryDAO;
import dao.CommentDAO;
import dao.MovieDAO;
import dao.UserDAO;
import jdk.jfr.BooleanFlag;
import model.Category;
import model.Comment;
import model.Movie;
import model.MovieWatchList;
import model.User;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;

@Path("")
public class APIRestful {
	UserDAO userDAO= new UserDAO();
	MovieDAO movieDAO= new MovieDAO();
	CategoryDAO categoryDAO= new CategoryDAO();
	CommentDAO cmtDAO= new CommentDAO();
	
	
	
	@GET
	@Path("/user/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUser() {
		return userDAO.getAllUser();
	}
	
	//Check login 
	@GET
	@Path("/user/checklogin/{username}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public User checkLogin(@PathParam("username") String username,@PathParam("password") String password) {
		return userDAO.checkLogin(username, password);
	}
	
	//Đăng kí người dùng
	@POST
	@Path("/user/registry")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User registry(User user){ 
		return userDAO.registry(user);
	   
	}
	
	//Check registry user login by gmail
	@POST
	@Path("/user/loginbygmail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User loginByGmail(User user){ 
			
	      return  userDAO.loginAsGoogleAccount(user); 
	}
	
	// Get all category
	@GET
	@Path("/category/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getAllCategory() {     
		return categoryDAO.getAllCategory();
	}
	
	// Get all comment of movie
	@GET
	@Path("/comment/getAllCommentOfMovie/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getAllCommentOfMovie(@PathParam("id") int id) {
		return cmtDAO.getAllCommentOfMovie(id);
	}
	
	// Add new rating
	@POST
	@Path("/comment/addRating")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Comment addRating(Comment c){ 
			

	      return  cmtDAO.addRating(c); 
	}
	
	// Search movie by name
	@GET
	@Path("/movie/search/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> searchMovie(@PathParam("key") String key) {
		return movieDAO.searchMovie(key);
	}
	
	@POST
	@Path("/category/add_movie_to_watchlist")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public int addMovieWatchList(MovieWatchList m) {
		return categoryDAO.addMovieWatchList(m);
	}
	
	@GET
	@Path("/category/remove_movie_watchlist/{id_movie}/{id_user}")
	@Produces(MediaType.APPLICATION_JSON)
	public int removeMovieWatchList(@PathParam("id_movie") int id_movie,@PathParam("id_user") int id_user) {
		return categoryDAO.removeMovieWatchList(id_movie,id_user);
	}
	
//	@POST
//	@Path("/user/insert")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String insert(List<User> list){ 
//		int rs= userDAO.insertUser(list);
//	     return rs+""; 
//	}
	
	@POST
	@Path("/movie/insertmovie")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String insertMovie(List<Movie> list){ 
			for(Movie m : list) {
				movieDAO.insertMovie(m);
			}
		return list.size()+"";
	     
	}
	
	
	@POST
	@Path("/user/updateAvatar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int upLoadAvatar(User user ){ 
			
		return userDAO.updateAvatar(user);
	     
	}
	

	@PUT
	@Path("/comment/editRating/{oldStar}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Comment editComment(Comment c,@PathParam("oldStar") float oldStar) {
		return cmtDAO.editComment(c, oldStar);
	}
	
}

