package com.pojo;

public class User {
    private Integer userId;

    private String userAge;

    private String userName;

    // runtime
    private int age;


    public User(Integer userId, String userAge, String userName) {
        this.userId = userId;
        this.userAge = userAge;
        this.userName = userName;
    }



    public User() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge == null ? null : userAge.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userAge='" + userAge + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}