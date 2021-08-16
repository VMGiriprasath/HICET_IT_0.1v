package com.renotech.app.hicetit.Model_Firebase;

import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;

public class Student implements Serializable {
 String Firstname="";
  String Lastname="";
    String Email="";
  String Password="";

    public Student() {
    }

    public Student(String Firstname, String Lastname, String Email, String Password) {
        this.Firstname = Firstname;
        this.Lastname = Lastname;
        this.Email = Email;
        this.Password = Password;
    }
    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
