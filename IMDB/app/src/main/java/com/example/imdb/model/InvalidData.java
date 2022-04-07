package com.example.imdb.model;

public class InvalidData {
    private String username,password,confirm,fullname,email;

    public InvalidData(String username, String password, String confirm, String fullname, String email) {
        this.username = username;
        this.password = password;
        this.confirm = confirm;
        this.fullname = fullname;
        this.email = email;
    }
    public String checkIsValid(){
        if(username.length()==0|password.length()==0|fullname.length()==0|confirm.length()==0|email.length()==0){
            return "Nhập thiếu dữ liệu";
        }
        else if(username.length()<=10||password.length()<=10)
            return "Username hoặc password quá ngắn";
        else if(!confirm.equals(password)) return "Hai mật khẩu không trùng khớp";

        return "OK";
    }
}
