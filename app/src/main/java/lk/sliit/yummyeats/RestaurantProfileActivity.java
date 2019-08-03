package lk.sliit.yummyeats;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class RestaurantProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,RestaurantMainActivity.class);
        startActivity(intent);
        finish();

    }
}
