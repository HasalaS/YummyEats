package lk.sliit.yummyeats;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import lk.sliit.yummyeats.Model.SessionUser;

public class DeliveryProfileActivity extends AppCompatActivity {

    //Init Firebase Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_deliver = database.getReference("Deliver");

    TextView tvDelName, tvDelEmail, tvDelMobile, tvDelMobileHeader, tvDelVehicleNo,  hDelName, hDelEmail;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_profile);
        getSupportActionBar().hide();

        tvDelName = findViewById(R.id.tv_del_profile_card_name_value);
        tvDelName.setText(SessionUser.deliver.getName());

        tvDelEmail = findViewById(R.id.tv_del_profile_card_email_value);
        tvDelEmail.setText(SessionUser.deliver.getEmail());

        tvDelMobileHeader = findViewById(R.id.tv_del_profile_card_mobile_key);
        tvDelMobile = findViewById(R.id.tv_del_profile_card_mobile_value);
        tvDelMobile.setText(SessionUser.deliver.getMobile());

        tvDelVehicleNo = findViewById(R.id.tv_del_profile_card_vehicle_no_value);
        tvDelVehicleNo.setText(SessionUser.deliver.getVehicleNo());
//del
        hDelName = findViewById(R.id.delHeaderName);
        hDelName.setText(SessionUser.deliver.getName());

        hDelEmail = findViewById(R.id.delHeaderEmail);
        hDelEmail.setText(SessionUser.deliver.getEmail());

        btnDelete = findViewById(R.id.btn_del_profile_delete);

    }

    public void showDialogDeliver(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(DeliveryProfileActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_update, null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DeliveryProfileActivity.this, DeliveryMainActivity.class);
        startActivity(intent);
        finish();
    }
}
