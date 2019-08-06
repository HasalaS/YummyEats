package lk.sliit.yummyeats;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FoodRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAddFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_registration);
        getSupportActionBar().hide();
        btnAddFood = findViewById(R.id.btn_food_reg_add);
        btnAddFood.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FoodRegistrationActivity.this, RestaurantMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_food_reg_add:

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(FoodRegistrationActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_food_added_successful, null);
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                break;
        }
    }
}
