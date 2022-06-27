package com.example.imdb.presenter;

import com.example.imdb.apicontroller.APIUser;
import com.example.imdb.model.User;

public class PresenterUser {
    private IOnUser i;
    private APIUser api;

    public PresenterUser(IOnUser i){
        this.i=i;
        api= new APIUser(i);
    }
    public void checkLogin(String username,String password){
        api.checkLogin(username,password);
    }
//    public void getUser(User u){
//        api.getUser(u);
//    }

    public void registry(User u){
        api.registry(u);
    }

    public void loginByGmail(User u){
        api.loginByGmail(u);
    }


    public interface IOnUser{
        public void onGetUser(User u);
        public void checkLogin(User u);
        public void registry(User u);
        public void loginByGmail(User u);
    }
}
