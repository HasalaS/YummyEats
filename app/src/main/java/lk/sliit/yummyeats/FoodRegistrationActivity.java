package lk.sliit.yummyeats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FoodRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_registration);
        getSupportActionBar().hide();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FoodRegistrationActivity.this, RestaurantMainActivity.class);
        startActivity(intent);
        finish();
    }
}
