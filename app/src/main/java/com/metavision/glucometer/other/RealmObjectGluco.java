package com.metavision.glucometer.other;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by MetaVision on 19/07/2017.
 */

public class RealmObjectGluco extends RealmObject {
    private String ConfirmPassword, Fullname, Weight, Height, Pie;
    @PrimaryKey
    private String Username;
    private String Password;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.ConfirmPassword = confirmPassword;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        this.Fullname = fullname;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        this.Weight = weight;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        this.Height = height;
    }

    public String getPie() {
        return Pie;
    }

    public void setPie(String pie) {
        this.Pie = pie;
    }
}
