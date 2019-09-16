package com.example.gametracker;

import java.io.Serializable;

public class Player implements Serializable {

    private String name;

    Player(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
