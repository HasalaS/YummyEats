package lk.sliit.yummyeats;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import lk.sliit.yummyeats.Registration.CustomerRegistrationActivity;
import lk.sliit.yummyeats.Registration.DelivererRegistrationActivity;
import lk.sliit.yummyeats.Registration.RestaurantsRegistrationActivity;
import lk.sliit.yummyeats.ui.main.CustomPageAdapter;
import lk.sliit.yummyeats.ui.main.SectionsPagerAdapter;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCusSignIn, btnCusSignUp, btnResSignIn, btnResSignUp, btnDelSignIn, btnDelSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        CustomPageAdapter adapter = new CustomPageAdapter(getSupportFragmentManager());
        adapter.AddFragment(new CustomerRegistrationActivity(), "Customer");
        adapter.AddFragment(new DelivererRegistrationActivity(), "Delivery");
        adapter.AddFragment(new RestaurantsRegistrationActivity(), "Restaurant");

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        btnCusSignIn = findViewById(R.id.btn_cus_signIn);
        btnCusSignUp = findViewById(R.id.btn_cus_signUp);
        btnResSignIn = findViewById(R.id.btn_res_signIn);
        btnResSignUp = findViewById(R.id.btn_res_signuP);
        btnDelSignIn = findViewById(R.id.btn_del_signIn);
        btnDelSignUp = findViewById(R.id.btn_del_signUp);

        btnCusSignIn.setOnClickListener(this);
        btnCusSignUp.setOnClickListener(this);
        btnDelSignIn.setOnClickListener(this);
        btnDelSignUp.setOnClickListener(this);
        btnResSignIn.setOnClickListener(this);
        btnResSignUp.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        // Create shared preferences set called welcome
        SharedPreferences profile = getSharedPreferences("profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor profileEditor = profile.edit();

        if(v.equals(btnCusSignIn) || v.equals(btnDelSignIn) || v.equals(btnResSignIn)){
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        else if(v.equals(btnCusSignUp)){

            // Set values of customer to shared preferences
            profileEditor.putString("REGISTRATION_STATUS", "TRUE");
            profileEditor.putString("USER_TYPE", "CUSTOMER");
            profileEditor.putString("LOGIN_STATUS", "TRUE");
            profileEditor.commit();
        }

        else if(v.equals(btnDelSignUp)){
            // Set values of delivery to shared preferences
            profileEditor.putString("REGISTRATION_STATUS", "TRUE");
            profileEditor.putString("USER_TYPE", "DELIVERY");
            profileEditor.putString("LOGIN_STATUS", "TRUE");
            profileEditor.commit();
        }


        else if(v.equals(btnResSignUp)){
            // Set values of restaurant to shared preferences
            profileEditor.putString("REGISTRATION_STATUS", "TRUE");
            profileEditor.putString("USER_TYPE", "RESTAURANT");
            profileEditor.putString("LOGIN_STATUS", "TRUE");
            profileEditor.commit();
        }
    }
}