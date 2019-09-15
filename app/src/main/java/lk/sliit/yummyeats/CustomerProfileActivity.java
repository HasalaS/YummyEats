package lk.sliit.yummyeats;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import lk.sliit.yummyeats.Model.SessionUser;

public class CustomerProfileActivity extends AppCompatActivity {

    TextView tvName, tvEmail, tvMobile, hName, hEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        getSupportActionBar().hide();

        tvName = findViewById(R.id.tv_cus_profile_card_name_value);
        tvName.setText(SessionUser.customer.getName());

        tvEmail = findViewById(R.id.tv_cus_profile_card_email_value);
        tvEmail.setText(SessionUser.customer.getEmail());

        tvMobile = findViewById(R.id.tv_cus_profile_card_mobile_value);
        tvMobile.setText(SessionUser.customer.getMobile());

        hName = findViewById(R.id.cusHeaderName);
        hName.setText(SessionUser.customer.getName());

        hEmail = findViewById(R.id.cusHeaderEmail);
        hEmail.setText(SessionUser.customer.getEmail());
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showDialogCustomer(View view){

        TextView tvKey = null;
        TextView tvValue = null;

        ViewGroup card = (ViewGroup) view;
        tvKey = (TextView) card.getChildAt(0);
        tvValue = (TextView) card.getChildAt(1);

        String key = tvKey.getText().toString();
        String value = tvValue.getText().toString();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CustomerProfileActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_update, null);

        ViewGroup vg = (ViewGroup)mView;
        ViewGroup viewGroup = (ViewGroup) vg.getChildAt(0);

        TextView updateTvKey = (TextView) viewGroup.getChildAt(0);
        EditText updateEtValue = (EditText) viewGroup.getChildAt(1);
        Button updateBtn = (Button) viewGroup.getChildAt(2);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //write update code here
                Toast.makeText(CustomerProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
            }
        });

        updateTvKey.setText(key);
        updateEtValue.setText(value);

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
