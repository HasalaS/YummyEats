package lk.sliit.yummyeats;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lk.sliit.yummyeats.Interface.Showable;
import lk.sliit.yummyeats.Model.Customer;
import lk.sliit.yummyeats.Model.SessionUser;

public class CustomerProfileActivity extends AppCompatActivity {

    //Init Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_customer = database.getReference("Customer");

    ImageView cusProImage;
    TextView tvCusName, tvCusEmail, tvCusMobile, tvCusMobileHead, hCusName, hCusEmail;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        getSupportActionBar().hide();

        tvCusName = findViewById(R.id.tv_cus_profile_card_name_value);
        tvCusName.setText(SessionUser.customer.getName());

        tvCusEmail = findViewById(R.id.tv_cus_profile_card_email_value);
        tvCusEmail.setText(SessionUser.customer.getEmail());

        tvCusMobileHead = findViewById(R.id.tv_cus_profile_card_mobile_key);

        tvCusMobile = findViewById(R.id.tv_cus_profile_card_mobile_value);
        tvCusMobile.setText(SessionUser.customer.getMobile());

        hCusName = findViewById(R.id.cusHeaderName);
        hCusName.setText(SessionUser.customer.getName());

        hCusEmail = findViewById(R.id.cusHeaderEmail);
        hCusEmail.setText(SessionUser.customer.getEmail());

        btnDelete = findViewById(R.id.btn_cus_profile_delete);
    }


    @Override
    protected void onResume() {
        super.onResume();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button btnCancel, btnDelete;

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CustomerProfileActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View mView = inflater.inflate(R.layout.dialog_customer_delete_confirmation, null);

                btnCancel = mView.findViewById(R.id.btn_customer_cancel);
                btnDelete = mView.findViewById(R.id.btn_customer_delete);

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
                    //Delete profile from DB
                    @Override
                    public void onClick(View v) {
                        table_customer.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(SessionUser.customer.getMobile().toLowerCase())){
                                    table_customer.child(SessionUser.customer.getMobile().toLowerCase()).removeValue();
                                    dialog.dismiss();
                                    Toast.makeText(CustomerProfileActivity.this,"Profile deleted successfully!", Toast.LENGTH_SHORT).show();

                                    SessionUser.customer = null;
                                    Intent intent = new Intent(CustomerProfileActivity.this, RegisterActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(CustomerProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(CustomerProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    public void showDialogCustomer(View view){

        TextView tvKey = null;
        TextView tvValue = null;

        ViewGroup card = (ViewGroup) view;
        tvKey = (TextView) card.getChildAt(0);
        tvValue = (TextView) card.getChildAt(1);

        final String key = tvKey.getText().toString();
        final String value = tvValue.getText().toString();

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(CustomerProfileActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_update, null);

        ViewGroup vg = (ViewGroup)mView;
        ViewGroup viewGroup = (ViewGroup) vg.getChildAt(0);

        TextView updateTvKey = (TextView) viewGroup.getChildAt(0);
        final EditText updateEtValue = (EditText) viewGroup.getChildAt(1);
        Button updateBtn = (Button) viewGroup.getChildAt(2);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //write update code here
                try {
                    table_customer.child(SessionUser.customer.getMobile()).child(key.toLowerCase()).setValue(updateEtValue.getText().toString());
                    Toast.makeText(CustomerProfileActivity.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception exc){
                    Toast.makeText(CustomerProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvCusMobileHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomerProfileActivity.this, "Cannot Update Mobile No!", Toast.LENGTH_SHORT).show();
            }
        });

        tvCusMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomerProfileActivity.this, "Cannot Update Mobile No!", Toast.LENGTH_SHORT).show();
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
