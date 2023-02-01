package com.smartyhome.app.main.home.roomdevices;

import com.google.gson.annotations.SerializedName;

public class rooms {


    @SerializedName("ID")
    public String ID = "";

    @SerializedName("name")
    public String name = "";


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
