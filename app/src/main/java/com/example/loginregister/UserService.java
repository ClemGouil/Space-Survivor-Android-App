package com.example.loginregister;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @GET("users/{mail}&{password}")
    Call<UserResponse> loginUsers(@Path("mail") String mail, @Path("password") String password);

    @POST("users/register")
    Call<UserResponse> registerUsers(@Body RegisterRequest registerRequest);

    @GET("items")
    Call<List<Object>> getObjects();

    @PUT("users")
    Call<UserResponse> updateUser(@Body RegisterRequest registerRequest);
}
