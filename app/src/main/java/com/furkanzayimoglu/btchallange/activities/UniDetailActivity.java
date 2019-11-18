package com.furkanzayimoglu.btchallange.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.furkanzayimoglu.btchallange.R;
import com.furkanzayimoglu.btchallange.adapters.DepartmentAdapter;
import com.furkanzayimoglu.btchallange.adapters.SlidingImageAdapter;
import com.furkanzayimoglu.btchallange.model.AccessToken;
import com.furkanzayimoglu.btchallange.model.ImageDetail;
import com.furkanzayimoglu.btchallange.model.TokenManager;
import com.furkanzayimoglu.btchallange.model.UniDepartmentDetail;
import com.furkanzayimoglu.btchallange.model.UniModel;
import com.furkanzayimoglu.btchallange.network.ApiService;
import com.furkanzayimoglu.btchallange.network.RetrofitBuilder;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class UniDetailActivity extends AppCompatActivity {

    private ViewPager pager;
    private CirclePageIndicator circleIndicator;
    private SlidingImageAdapter slidingImageAdapter;
    public static ArrayList<ImageDetail> imageList = new ArrayList<>();
    public static ArrayList<UniDepartmentDetail> department= new ArrayList<>();
    public ApiService apiService;
    public ApiService apiService2;
    public TokenManager tokenManager;
    private static int id;
    public UniModel model;
    TextView uninameText;
    TextView unitypeText;
    TextView uniLongDetailText;
    TextView bolumText;
    TextView actionbartext;
    RecyclerView recyclerView;
    DepartmentAdapter adapter;
    Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uni_detail);

        uninameText = findViewById(R.id.uniName);
        uniLongDetailText = findViewById(R.id.longDetails);
        unitypeText = findViewById(R.id.uniType);
        bolumText = findViewById(R.id.bolum);
        actionbartext = findViewById(R.id.action_bar_titlesUni);
        recyclerView = findViewById(R.id.recyclerDepartment);

        Intent intent = getIntent();
        id = intent.getIntExtra("loginId", 0);
        System.out.println(id);
        tokenManager = TokenManager.getInstance(getSharedPreferences("logintoken", MODE_PRIVATE));
        pager = findViewById(R.id.pager);
        retrofit = new Retrofit.Builder().baseUrl(RetrofitBuilder.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        getDetail();


    }

    private void setAdapter(ArrayList<ImageDetail> imageList) {
        slidingImageAdapter = new SlidingImageAdapter(this, imageList);
        pager.setAdapter(slidingImageAdapter);
        circleIndicator = findViewById(R.id.circle);
        circleIndicator.setViewPager(pager);
    }

    private void setText(UniModel model) {
        uninameText.setText(model.getName());
        uniLongDetailText.setText(model.getLongDetail());
        actionbartext.setText(model.getName());
        unitypeText.setText(model.getType());
    }

    private void setRecylerDepartment(ArrayList<UniDepartmentDetail> department) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        adapter = new DepartmentAdapter(UniDetailActivity.this, department);
        recyclerView.setAdapter(adapter);
    }

    private void clearImagelist(){
        imageList.clear();
    }
    private void clearDepertmentList(){
        department.clear();
    }

    private void getDetail() {
        String token = "bearer " + tokenManager.getToken().getAccessToken();
        Call<String> call = apiService.getUniDetail(token, id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body();
                    getDetailUni(jsonResponse);
                } else {
                    if (response.code() == 401 || response.code() == 403) {
                        Log.i("hata kodu", String.valueOf(response.code()));
                           apiService2 = RetrofitBuilder.createService(ApiService.class);
                        Call<AccessToken> call1 = apiService2.refresh(MainActivity.type2,MainActivity.client_id,MainActivity.client_secret,tokenManager.getToken().getRefreshToken());
                           call1.enqueue(new Callback<AccessToken>() {
                            @Override
                            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                                if (response.isSuccessful()) {
                                    tokenManager.saveToken(response.body());
                                    Log.i("yeni alınan token ", response.body().getAccessToken());
                                    getDetail();
                                }
                            }

                            @Override
                            public void onFailure(Call<AccessToken> call, Throwable t) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void getDetailUni(String jsonResponse) {
        try {
            clearImagelist();
            clearDepertmentList();
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            model = new UniModel();
            model.setName(dataObject.getString("name"));
            model.setLongDetail(dataObject.getString("detail"));
            model.setType(dataObject.getString("type"));
            JSONArray imageArray = dataObject.getJSONArray("images");
            for (int i = 0; i < imageArray.length(); i++) {
                JSONObject imageObject = imageArray.getJSONObject(i);
                ImageDetail imageDetail = new ImageDetail();
                imageDetail.setUrl(imageObject.getString("url"));
                imageList.add(imageDetail);
                Log.i("resimler", imageList.get(i).getUrl());
            }
            JSONArray departmentArray = dataObject.getJSONArray("majorDetail");
            for (int i = 0; i < departmentArray.length(); i++) {
                JSONObject detailsObject = departmentArray.getJSONObject(i);
                UniDepartmentDetail departmentDetail = new UniDepartmentDetail();
                departmentDetail.setName(detailsObject.getString("name"));
                departmentDetail.setSchoolType(detailsObject.getString("schoolType"));
                department.add(departmentDetail);
            }
            Log.i("detay listesi", departmentArray.toString());
            Log.i("departmentlist", department.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        bolumText.setVisibility(View.VISIBLE);
        setAdapter(imageList);
        setText(model);
        setRecylerDepartment(department);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
