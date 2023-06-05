package com.example.project_android.Response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("id")
    private String id;

    private transient String token;
    private transient String rol;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}
