package lk.sliit.yummyeats;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lk.sliit.yummyeats.Model.SessionUser;

public class DeliveryProfileActivity extends AppCompatActivity {

    //Init Firebase
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

        hDelName = findViewById(R.id.delHeaderName);
        hDelName.setText(SessionUser.deliver.getName());

        hDelEmail = findViewById(R.id.delHeaderEmail);
        hDelEmail.setText(SessionUser.deliver.getEmail());

        btnDelete = findViewById(R.id.btn_del_profile_delete);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*Button btnCancel, btnDelete;

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(DeliveryProfileActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View mView = inflater.inflate(R.layout.dialog_deliver_delete_confirmation, null);

        btnCancel = findViewById(R.id.btn_deliver_cancel);
        btnDelete = findViewById(R.id.btn_deliver_delete);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_deliver.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(SessionUser.deliver.getMobile())){
                            table_deliver.child(SessionUser.deliver.getMobile()).removeValue();
                            dialog.dismiss();
                            Toast.makeText(DeliveryProfileActivity.this,"Profile deleted successfully!", Toast.LENGTH_SHORT).show();

                            SessionUser.deliver = null;
                            Intent intent = new Intent(DeliveryProfileActivity.this, RegisterActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(DeliveryProfileActivity.this,"Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(DeliveryProfileActivity.this,"Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/
    }

    public void showDialogDeliver(View view){
        TextView tvKey = null;
        TextView tvValue = null;

        ViewGroup card = (ViewGroup) view;
        tvKey = (TextView) card.getChildAt(0);
        tvValue = (TextView) card.getChildAt(1);
        final String key = tvKey.getText().toString();
        final String value = tvValue.getText().toString();

        /*if (key.equals("Vehicle No")){
            key = "vehicleNo";
        }*/

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(DeliveryProfileActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_update, null);

        ViewGroup vg = (ViewGroup)mView;
        ViewGroup viewGroup = (ViewGroup) vg.getChildAt(0);

        TextView updateTvKey = (TextView) viewGroup.getChildAt(0);
        final EditText updateEtValue = (EditText) viewGroup.getChildAt(1);
        final Button updateBtn = (Button) viewGroup.getChildAt(2);

        tvDelMobileHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DeliveryProfileActivity.this, "Cannot Update Mobile No!", Toast.LENGTH_SHORT).show();
            }
        });

        tvDelMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DeliveryProfileActivity.this, "Cannot Update Mobile No!", Toast.LENGTH_SHORT).show();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    table_deliver.child(SessionUser.deliver.getMobile()).child(key.toLowerCase()).setValue(updateEtValue.getText().toString());
                    Toast.makeText(DeliveryProfileActivity.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception exc){
                    Toast.makeText(DeliveryProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
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
        Intent intent = new Intent(DeliveryProfileActivity.this, DeliveryMainActivity.class);
        startActivity(intent);
        finish();
    }
}
