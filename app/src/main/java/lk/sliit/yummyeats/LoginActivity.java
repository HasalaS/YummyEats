package lk.sliit.yummyeats;

import android.app.ProgressDialog;
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
    Button btnSignIn;

    //Init Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference("Customer");
    final DatabaseReference table_deliver = database.getReference("Deliver");
    final DatabaseReference table_restaurant = database.getReference("Restaurant");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etMobileNumber = findViewById(R.id.et_mobile);
        etPassword = findViewById(R.id.et_password);
        btnSignIn = findViewById(R.id.btn_signIn_signIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                                Toast.makeText(LoginActivity.this, "Sign in as a customer successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Password Incorrect", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(LoginActivity.this, "Sign in as a driver successful", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Password Incorrect", Toast.LENGTH_SHORT).show();
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
                                                        Toast.makeText(LoginActivity.this, "Sign in as a restaurant successful", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(LoginActivity.this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(LoginActivity.this, "Ehema ekek meke na", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

}
