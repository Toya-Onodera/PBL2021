package com.example.pbl2021timerapp;

public class CotohaBearerToken {
    public String accessToken;
    public String tokenType;
    public String expiresIn;
    public String scope;
    public String issuedAt;

    public CotohaBearerToken(String accessToken, String tokenType, String expiresIn, String scope, String issuedAt) {
        System.out.println(accessToken);

        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.scope = scope;
        this.issuedAt = issuedAt;
    }
}
