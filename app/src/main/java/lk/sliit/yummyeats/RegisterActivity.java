package lk.sliit.yummyeats;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import lk.sliit.yummyeats.Registration.CustomerRegistrationActivity;
import lk.sliit.yummyeats.Registration.DelivererRegistrationActivity;
import lk.sliit.yummyeats.Registration.RestaurantsRegistrationActivity;
import lk.sliit.yummyeats.ui.main.RegistrationPageAdapter;
import lk.sliit.yummyeats.ui.main.SectionsPagerAdapter;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        RegistrationPageAdapter adapter = new RegistrationPageAdapter(getSupportFragmentManager());
        adapter.AddFragment(new CustomerRegistrationActivity(), "Customer");
        adapter.AddFragment(new DelivererRegistrationActivity(), "Deliverer");
        adapter.AddFragment(new RestaurantsRegistrationActivity(), "Restaurant");

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

    }
}