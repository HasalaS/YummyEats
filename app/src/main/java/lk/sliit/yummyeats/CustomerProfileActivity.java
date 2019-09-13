package lk.sliit.yummyeats;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import lk.sliit.yummyeats.Model.SessionUser;

public class CustomerProfileActivity extends AppCompatActivity {

    TextView etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        getSupportActionBar().hide();

        etName = findViewById(R.id.cus_profile_name);
        etName.setText(SessionUser.customer.getName());

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void showDialogCustomer(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CustomerProfileActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_update, null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CustomerProfileActivity.this, CustomerMainActivity.class);
        startActivity(intent);
        finish();
    }
}
