package com.codewithrisesh.ramro;

import android.net.Uri;

public class User {
    private int coins;
    private String name;
    private int tokens;
    private String email;
    private String password;
    private String image;
//    private final int addCoin = 10;
    User(){
    }
    User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;

    }
    User(String name, int coins, int tokens){
        this.name = name;
        this.coins = coins;
        this.tokens = tokens;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


//    public int getAddCoin() {
//        return addCoin;
//    }
    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

}
