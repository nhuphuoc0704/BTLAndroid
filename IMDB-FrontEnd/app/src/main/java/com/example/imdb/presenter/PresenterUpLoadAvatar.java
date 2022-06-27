package com.example.imdb.presenter;

import com.example.imdb.apicontroller.APIUpLoadAvatar;
import com.example.imdb.apicontroller.APIUser;
import com.example.imdb.model.User;

public class PresenterUpLoadAvatar {
    private IOnUpLoadAvatar i;
    private APIUpLoadAvatar api;

    public PresenterUpLoadAvatar(IOnUpLoadAvatar i) {
        this.i = i;
        api= new APIUpLoadAvatar(i);
    }

    public void upLoadAvatar(int id_user,String uriPath){
        api.uploadAvatar(id_user,uriPath);
    }

    public void updateAvatar(User user){
        api.updateAvatar(user);
    }
    public interface IOnUpLoadAvatar{
        void upLoadSuccess(String result);
        void updateAvatar(int rs);
    }
}
