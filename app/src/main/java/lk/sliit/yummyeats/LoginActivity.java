package lk.sliit.yummyeats;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lk.sliit.yummyeats.Model.Customer;
import lk.sliit.yummyeats.Model.Deliver;
import lk.sliit.yummyeats.Model.Restaurant;

public class LoginActivity extends AppCompatActivity {

    EditText etMobileNumber, etPassword;
    Button btnSignIn, btnSignUp;

    //Init Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference("Customer");
    final DatabaseReference table_deliver = database.getReference("Deliver");
    final DatabaseReference table_restaurant = database.getReference("Restaurant");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // hide the action bar
        getSupportActionBar().hide();

        etMobileNumber = findViewById(R.id.et_mobile);
        etPassword = findViewById(R.id.et_password);
        btnSignIn = findViewById(R.id.btn_signIn_signIn);
        btnSignUp = findViewById(R.id.btn_signIn_signUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check whether the input fields are empty or not
                if(etMobileNumber.getText().toString().matches("") || etPassword.getText().toString().matches("")){
                    Toast.makeText(LoginActivity.this, R.string.login_toast_field_empty, Toast.LENGTH_SHORT).show();
                    return;
                }

                // if input fields are filed
                else{
                    final ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                    mDialog.setMessage("Please wait...");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //check availability in customer
                            if(dataSnapshot.child(etMobileNumber.getText().toString()).exists()) {
                                //get user information
                                mDialog.dismiss();
                                Customer customer = dataSnapshot.child(etMobileNumber.getText().toString()).getValue(Customer.class);
                                if (customer.getPassword().equals(etPassword.getText().toString())) {
                                    Toast.makeText(LoginActivity.this, R.string.login_toast_customer_successfull, Toast.LENGTH_SHORT).show();
                                    Intent customerIntent = new Intent(LoginActivity.this, CustomerMainActivity.class);
                                    startActivity(customerIntent);
                                    finish();
                                } else {
                                    mDialog.dismiss();
                                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
                                    View mView = getLayoutInflater().inflate(R.layout.dialog_incorrect_password, null);
                                    mBuilder.setView(mView);
                                    AlertDialog dialog = mBuilder.create();
                                    dialog.show();
                                }
                            } else {
                                // user does not exist in customer
                                table_deliver.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.child(etMobileNumber.getText().toString()).exists()){
                                            //get driver information
                                            mDialog.dismiss();
                                            Deliver deliver = dataSnapshot.child(etMobileNumber.getText().toString()).getValue(Deliver.class);
                                            if(deliver.getPassword().equals(etPassword.getText().toString())){
                                                Toast.makeText(LoginActivity.this, R.string.login_toast_driver_successfull, Toast.LENGTH_SHORT).show();
                                                Intent deliverIntent = new Intent(LoginActivity.this, DeliveryMainActivity.class);
                                                startActivity(deliverIntent);
                                                finish();
                                            } else {
                                                mDialog.dismiss();
                                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
                                                View mView = getLayoutInflater().inflate(R.layout.dialog_incorrect_password, null);
                                                mBuilder.setView(mView);
                                                AlertDialog dialog = mBuilder.create();
                                                dialog.show();
                                            }
                                        } else {
                                            // user does not exist in deliver
                                            table_restaurant.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if(dataSnapshot.child(etMobileNumber.getText().toString()).exists()){
                                                        mDialog.dismiss();
                                                        Restaurant restaurant = dataSnapshot.child(etMobileNumber.getText().toString()).getValue(Restaurant.class);
                                                        if(restaurant.getPassword().equals(etPassword.getText().toString())){
                                                            Toast.makeText(LoginActivity.this,R.string.login_toast_sign_successfull, Toast.LENGTH_SHORT).show();
                                                            Intent restaurantIntent = new Intent(LoginActivity.this, RestaurantMainActivity.class);
                                                            startActivity(restaurantIntent);
                                                            finish();
                                                        } else {
                                                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
                                                            View mView = getLayoutInflater().inflate(R.layout.dialog_incorrect_password, null);
                                                            mBuilder.setView(mView);
                                                            AlertDialog dialog = mBuilder.create();
                                                            dialog.show();
                                                        }
                                                    } else {
                                                        Toast.makeText(LoginActivity.this, R.string.login_toast_user_not_exist, Toast.LENGTH_SHORT).show();
                                                        mDialog.dismiss();
                                                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
                                                        View mView = getLayoutInflater().inflate(R.layout.dialog_user_doesnot_exist, null);
                                                        mBuilder.setView(mView);
                                                        AlertDialog dialog = mBuilder.create();
                                                        dialog.show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    Toast.makeText(LoginActivity.this,R.string.login_toast_went_wrong, Toast.LENGTH_SHORT).show();
                                                    mDialog.dismiss();
                                                }
                                            });

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(LoginActivity.this, R.string.login_toast_went_wrong, Toast.LENGTH_SHORT).show();
                                        mDialog.dismiss();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(LoginActivity.this, R.string.login_toast_went_wrong, Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    });
                }
            }
        });

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.login_toast_click_back, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false; }
                }, 2000);
    }
}
