package com.forkthecode.arms_client;

/**
 * Created by Rohan on 3/9/2016.
 */
public class User {
    private String email;
    private String name;
    private String phone;
    private String restaurauntId;

    public User(){

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getRestaurauntId() {
        return restaurauntId;
    }



    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRestaurauntId(String restaurauntId) {
        this.restaurauntId = restaurauntId;
    }

}
