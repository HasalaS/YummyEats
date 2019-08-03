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

public class RegisterActivity extends AppCompatActivity{

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

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}