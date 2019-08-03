package lk.sliit.yummyeats;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RestaurantProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);
        getSupportActionBar().hide();
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,RestaurantMainActivity.class);
        startActivity(intent);
        finish();

    }

    public void showDialogRestaurant(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(RestaurantProfileActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_update, null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
}
