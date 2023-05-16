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
    Call<LoginResponse> loginUsers(@Path("mail") String mail, @Path("password") String password);

    @POST("users/register")
    Call<RegisterResponse> registerUsers(@Body RegisterRequest registerRequest);

}
