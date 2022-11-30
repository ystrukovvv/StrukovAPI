package com.example.strukov.Get;

import android.graphics.Bitmap;

public class Player {
    private String Name;
    private Integer PositionId;
    private Integer PlayerId;

    public Player(String Name, Integer PositionId, Integer PlayerId) {
        this.Name = Name;
        this.PositionId = PositionId;
        this.PlayerId = PlayerId;
    }

    public String getName() {
        return Name;
    }
    public void setName(String login) {
        this.Name = login;
    }

    public Integer getPositionId() {
        return PositionId;
    }
    public void setPositionId(Integer type) {
        this.PositionId = type;
    }

    public  Integer getPlayerId(){
        return PlayerId;
    }
    public  void  setPlayerId(Integer type) {
        this.PlayerId = type;
    }
}

