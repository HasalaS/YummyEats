package lk.sliit.yummyeats;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FoodProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_profile);
        getSupportActionBar().hide();
    }

    public void showDialogFood(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(FoodProfileActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_update, null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
}
