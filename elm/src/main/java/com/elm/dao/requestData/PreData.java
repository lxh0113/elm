package com.elm.dao.requestData;

public class PreData {

    private String id;

    private String password;

    private String email;

    private String captcha;

    public PreData() {
    }

    public PreData(String id, String password, String email, String captcha) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.captcha = captcha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public String toString() {
        return "PreData{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", captcha='" + captcha + '\'' +
                '}';
    }
}
