package lk.sliit.yummyeats;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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

public class RestaurantProfileActivity extends AppCompatActivity {

    //Init Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_restaurant = database.getReference("Restaurant");

    TextView tvResName, tvResEmail, tvResMobile, tvResMobileHeader, tvResAddress, hResName, hResEmail;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);
        getSupportActionBar().hide();

        tvResName = findViewById(R.id.tv_res_profile_card_name_value);
        tvResName.setText(SessionUser.restaurant.getName());

        tvResEmail = findViewById(R.id.tv_res_profile_card_email_value);
        tvResEmail.setText(SessionUser.restaurant.getEmail());

        tvResMobileHeader = findViewById(R.id.tv_res_profile_card_mobile_key);
        tvResMobile = findViewById(R.id.tv_res_profile_card_mobile_value);
        tvResMobile.setText(SessionUser.restaurant.getMobile());

        tvResAddress = findViewById(R.id.tv_res_profile_card_address_value);
        tvResAddress.setText(SessionUser.restaurant.getAddress());

        hResName = findViewById(R.id.resHeaderName);
        hResName.setText(SessionUser.restaurant.getName());

        hResEmail = findViewById(R.id.resHeaderEmail);
        hResEmail.setText(SessionUser.restaurant.getEmail());

        btnDelete = findViewById(R.id.btn_res_profile_delete);
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btnCancel, btnDelete;

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(RestaurantProfileActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mView = inflater.inflate(R.layout.dialog_restaurant_delete_confirmation, null);

                btnCancel = mView.findViewById(R.id.btn_restaurant_cancel);
                btnDelete = mView.findViewById(R.id.btn_restaurant_delete);

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
                        //Delete profile from DB
                        table_restaurant.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(SessionUser.restaurant.getMobile())){
                                    table_restaurant.child(SessionUser.restaurant.getMobile()).removeValue();
                                    dialog.dismiss();
                                    Toast.makeText(RestaurantProfileActivity.this, "Profile deleted successfully!", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(RestaurantProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(RestaurantProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

    }

    public void showDialogRestaurant(View view){

        TextView tvKey = null;
        TextView tvValue = null;

        ViewGroup card = (ViewGroup) view;
        tvKey = (TextView) card.getChildAt(0);
        tvValue = (TextView) card.getChildAt(1);

        final String key = tvKey.getText().toString();
        final String value = tvValue.getText().toString();

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(RestaurantProfileActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_update, null);

        ViewGroup vg = (ViewGroup)mView;
        ViewGroup viewGroup = (ViewGroup) vg.getChildAt(0);

        TextView updateTvKey = (TextView) viewGroup.getChildAt(0);
        final EditText updateEtValue = (EditText) viewGroup.getChildAt(1);
        Button updateBtn = (Button) viewGroup.getChildAt(2);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    table_restaurant.child(SessionUser.restaurant.getMobile()).child(key.toLowerCase()).setValue(updateEtValue.getText().toString());
                    Toast.makeText(RestaurantProfileActivity.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception exc){
                    Toast.makeText(RestaurantProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this,RestaurantMainActivity.class);
        startActivity(intent);
        finish();

    }
}
