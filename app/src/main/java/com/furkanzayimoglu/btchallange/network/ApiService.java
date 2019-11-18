package com.furkanzayimoglu.btchallange.network;

import com.furkanzayimoglu.btchallange.model.AccessToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("oauth/token")
    @FormUrlEncoded
    Call<AccessToken> getToken(@Field("grant_type") String type, @Field("client_id") String id, @Field("client_secret") String secret);

    @POST("oauth/token")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("grant_type") String type, @Field("client_id") String id, @Field("client_secret") String secret,@Field("refresh_token") String refreshToken);

    @GET("university")
    Call<String> getUni(@Header("Authorization") String token);


    @POST("oauth/token")
    @FormUrlEncoded
    Call<AccessToken> login(@Field("grant_type") String type,@Field("username") String email,@Field("password") String password,@Field("client_id") String id, @Field("client_secret") String secret);


    @GET("university/{uniId}")
    Call<String> getUniDetail(@Header("Authorization") String token,@Path("uniId") long id );



}
