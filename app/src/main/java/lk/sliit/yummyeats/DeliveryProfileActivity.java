package lk.sliit.yummyeats;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import lk.sliit.yummyeats.Model.Deliver;
import lk.sliit.yummyeats.Model.SessionUser;

public class DeliveryProfileActivity extends AppCompatActivity {

    TextView tvDeliverName,tvDeliverEmail,tvDeliverContactNumber,tvDeliverVehicleNumber;
    TextView tvDeliverTitleName,tvDeliverTitleEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_profile);
        getSupportActionBar().hide();

        tvDeliverName = findViewById(R.id.tv_deliver_profile_name_value);
        tvDeliverName.setText(SessionUser.deliver.getName());

        tvDeliverEmail = findViewById(R.id.tv_deliver_profile_email_value);
        tvDeliverEmail.setText(SessionUser.deliver.getEmail());

        tvDeliverContactNumber = findViewById(R.id.tv_deliver_profile_contact_value);
        tvDeliverContactNumber.setText(SessionUser.deliver.getMobile());

        tvDeliverVehicleNumber = findViewById(R.id.tv_deliver_profile_vehicle_value);
        tvDeliverVehicleNumber.setText(SessionUser.deliver.getVehicleNo());

        tvDeliverTitleName = findViewById(R.id.deliver_profile_title_name);
        tvDeliverTitleName.setText(SessionUser.deliver.getName());

        tvDeliverTitleEmail = findViewById(R.id.deliver_profile_title_email);
        tvDeliverTitleEmail.setText(SessionUser.deliver.getEmail());


    }

    public void onResume(){
        super.onResume();;
    }

    public void showDialogDeliver(View view){

        TextView tvKey = null;
        TextView tvValue =null;

        ViewGroup card = (ViewGroup) view;

        tvKey = (TextView)card.getChildAt(0);
        tvValue = (TextView)card.getChildAt(1);

        String key = tvKey.getText().toString();
        String value = tvValue.getText().toString();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(DeliveryProfileActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_update, null);

        ViewGroup vg = (ViewGroup)mView;
        ViewGroup viewGroup = (ViewGroup) vg.getChildAt(0);

        TextView updateTvKey = (TextView) viewGroup.getChildAt(0);
        final EditText updateEtValue = (EditText) viewGroup.getChildAt(1);
        Button updateDeliverButton = (Button)viewGroup.getChildAt(2);

        updateTvKey.setText(key);
        updateEtValue.setText(value);

        updateDeliverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update


                Toast.makeText(DeliveryProfileActivity.this,"Data Updated Succesfully!",Toast.LENGTH_SHORT).show();
            }
        });

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
