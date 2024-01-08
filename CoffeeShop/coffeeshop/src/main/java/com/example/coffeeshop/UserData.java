package com.example.coffeeshop;

public class UserData {
    //This class gets set up at the html page and gets passed on to UserDataController
    private String firstName;
    private String lastName;
    private String email;
    private String drink;
    private String birthday;


    public String GetFirstName(){
        return firstName;
    }
    public String GetLastName(){
        return lastName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDrink() {
        return drink;
    }
    public void setDrink(String drink) {
        this.drink = drink;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
