package com.example.imdb.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InvalidData {
    private String username,password,confirm,fullname,email;
    public static final String regex="^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$";

    public InvalidData(String username, String password, String confirm, String fullname, String email) {
        this.username = username;
        this.password = password;
        this.confirm = confirm;
        this.fullname = fullname;
        this.email = email;
    }
    public String checkIsValid(){
        Pattern p= Pattern.compile(regex);
        if(username.length()==0|password.length()==0|fullname.length()==0|confirm.length()==0|email.length()==0){
            return "Nhập thiếu dữ liệu";
        }
        else if(username.length()<=10)
            return "Username quá ngắn";
        else if(!confirm.equals(password)) return "Hai mật khẩu không trùng khớp";
        Matcher matcher= p.matcher(password);
        if(!matcher.matches()) return "Mật khẩu phải có 1 số, 1 chữ cái hoa, 1 kí tự đặc biệt và phải ít nhất 8 kí tự";
        return "OK";
    }
}
