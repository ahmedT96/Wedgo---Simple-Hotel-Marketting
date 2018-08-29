package com.example.lenovo.wedgo;

//the interferenece we used the data in it ; to compare the registered user or not
public class currentUser {
    public String user_id;
    public currentUser(String user_id){
        this.user_id = user_id;

    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

}
