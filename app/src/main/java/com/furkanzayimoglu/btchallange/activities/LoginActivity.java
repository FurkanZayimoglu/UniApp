package com.furkanzayimoglu.btchallange.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.furkanzayimoglu.btchallange.R;
import com.furkanzayimoglu.btchallange.model.AccessToken;
import com.furkanzayimoglu.btchallange.model.TokenManager;
import com.furkanzayimoglu.btchallange.network.ApiService;
import com.furkanzayimoglu.btchallange.network.RetrofitBuilder;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout textEmail;
    TextInputLayout textPassword;
    ConstraintLayout constraintLayout;
    LinearLayout linearLayout;

    ApiService apiService;
    TokenManager tokenManager;
    AwesomeValidation validator;
    Call<AccessToken> call;

    public static String grant_type = "password";
    public static String client_id = "testjwtclientid";
    public static String client_secret = "XY7kmzoNzl100";
    public static String type2 = "refresh_token";
    public static String email;
    public static String password;
    public static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        textEmail = findViewById(R.id.email);
        textPassword = findViewById(R.id.pass);
        constraintLayout = findViewById(R.id.constraint);
        linearLayout = findViewById(R.id.linear);

        apiService = RetrofitBuilder.createService(ApiService.class);
        tokenManager = TokenManager.getInstance(getSharedPreferences("logintoken",MODE_PRIVATE));
        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        setupRules();

        Intent intent = getIntent();
        id = intent.getIntExtra("loginId",0);
        System.out.println(id);

    }

    private void setupRules() {

        validator.addValidation(this,R.id.email, Patterns.EMAIL_ADDRESS,R.string.err_email);
        validator.addValidation(this, R.id.pass, RegexTemplate.NOT_EMPTY, R.string.err_password);
    }

    public void Login(View view){

        email = textEmail.getEditText().getText().toString();
        password = textPassword.getEditText().getText().toString();
        validator.clear();
        if (validator.validate()){
           passwordToken();

        }
    }
    private void passwordToken(){
        call = apiService.login(grant_type,email,password,client_id,client_secret);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()){
                    tokenManager.saveToken(response.body());
                    Log.i("loginToken",tokenManager.getToken().getAccessToken());
                    Intent intentLogin = new Intent(LoginActivity.this, UniDetailActivity.class);
                    intentLogin.putExtra("loginId",id);
                    intentLogin.putExtra("info","isLogin");
                    startActivity(intentLogin);
                    finish();
                }else{
                    if (response.code() == 401){
                    }
                }
            }
            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {

            }
        });
    }

}
