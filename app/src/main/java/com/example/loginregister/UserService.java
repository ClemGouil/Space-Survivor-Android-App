package com.example.loginregister;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @GET("users/{mail}&{password}")
    Call<UserResponse> loginUsers(@Path("mail") String mail, @Path("password") String password);

    @POST("users/register")
    Call<UserResponse> registerUsers(@Body RegisterRequest registerRequest);

    @POST("users/question")
    Call<Question> sendQuestion(@Body Question question);

    @DELETE("users/delete/{mail}&{password}")
    Call<UserResponse> deleteUsers(@Path("mail") String mail, @Path("password") String password);

    @GET("items")
    Call<List<Object>> getObjects();

    @PUT("users")
    Call<UserResponse> updateUser(@Body UserRequest userRequest);

    @PUT("users/shop/buy/{mail}&{password}")
    Call<Object> buyObjet(@Body Object object,@Path("mail") String mail, @Path("password") String password);

    @PUT("users/shop/sell/{mail}&{password}")
    Call<Object> sellObjet(@Body Object object,@Path("mail") String mail, @Path("password") String password);

    @GET("users/inventory/{mail}&{password}")
    Call<List<Object>> getInventory(@Path("mail") String mail, @Path("password") String password);
}
