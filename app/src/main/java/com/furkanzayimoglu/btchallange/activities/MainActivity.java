package com.furkanzayimoglu.btchallange.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.furkanzayimoglu.btchallange.R;
import com.furkanzayimoglu.btchallange.model.AccessToken;
import com.furkanzayimoglu.btchallange.model.TokenManager;
import com.furkanzayimoglu.btchallange.model.UniModel;
import com.furkanzayimoglu.btchallange.network.ApiService;
import com.furkanzayimoglu.btchallange.network.RetrofitBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    public static String grant_type = "global";
    public  static String client_id = "testjwtclientid";
    public  static String client_secret = "XY7kmzoNzl100";
    public static String type2 = "refresh_token";


    public ApiService service;
    public Call<AccessToken> call;
    public Call<String> stringCall;
    TokenManager tokenManager;
    public static  ArrayList<UniModel> arrayList= new ArrayList<>();

    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        service = RetrofitBuilder.createService(ApiService.class);
        accessToken();

    }


    public void accessToken() {
        call = service.getToken(grant_type,client_id,client_secret);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {

                if (response.isSuccessful()) {
                    tokenManager.saveToken(response.body());
                    Log.i("token",tokenManager.getToken().getAccessToken());
                    getList();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {

            }
        });
    }

    private void getList(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RetrofitBuilder.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
        String token = "bearer"+" "+tokenManager.getToken().getAccessToken();
        stringCall = service.getUni(token);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.i("basarılı", response.body());
                    String jresponse = response.body();
                    getUniList(jresponse);
                    tokenManager.deleteToken();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    private void getUniList(String jresponse){
        try {
            JSONObject jsonObject = new JSONObject(jresponse);
            JSONArray array = jsonObject.getJSONArray("data");
            Log.i("dizi",array.toString());
            for (int i = 0; i < array.length(); i++){

                UniModel uniModel = new UniModel();
                JSONObject resultObject = array.getJSONObject(i);
                if (resultObject.has("url")){
                    uniModel.setName(resultObject.getString("name"));
                    uniModel.setType(resultObject.getString("type"));
                    uniModel.setShortDetail(resultObject.getString("shortDetail"));
                    uniModel.setUrl(resultObject.getString("url"));
                    uniModel.setLat(resultObject.getDouble("lat"));
                    uniModel.setLng(resultObject.getDouble("lng"));
                    uniModel.setId(resultObject.getInt("id"));
                    arrayList.add(uniModel);

                }
                Intent intent = new Intent(MainActivity.this,UniNameActivity.class);
                intent.putParcelableArrayListExtra("list",  arrayList);
                startActivity(intent);
                finish();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
