package com.example.imdb.presenter;

import com.example.imdb.apicontroller.APIComment;
import com.example.imdb.model.Comment;

import java.util.List;

public class PresenterComment {
    private IOnComment i;
    private APIComment api;

    public PresenterComment(IOnComment i) {
        this.i = i;
        api= new APIComment(i);

    }

    public void getAllCommentOfMovie(int id){
        api.getAllCommentOfMovie(id);
    }

    public void addRating(Comment comment){
        api.addRating(comment);
    }
    public interface IOnComment{
        public void getAllCommentOfMovie(List<Comment> list);
        public void onRatingSuccess(String results);
    }
}
