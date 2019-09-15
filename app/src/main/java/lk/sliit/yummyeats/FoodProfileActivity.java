package lk.sliit.yummyeats;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import lk.sliit.yummyeats.Model.SessionUser;

public class FoodProfileActivity extends AppCompatActivity {

    TextView tvName, tvDesc, tvPrice, tvCategory, hName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_profile);
        getSupportActionBar().hide();

        tvName = findViewById(R.id.tv_food_profile_card_name_value);
        tvDesc = findViewById(R.id.tv_food_profile_card_desc_value);
        tvPrice = findViewById(R.id.tv_food_profile_card_price_value);
        tvCategory = findViewById(R.id.tv_food_profile_card_category_value);
        hName = findViewById(R.id.foodHeaderName);
    }

    public void showDialogFood(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(FoodProfileActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_update, null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FoodProfileActivity.this, RestaurantMainActivity.class);
        startActivity(intent);
        finish();
    }
}
