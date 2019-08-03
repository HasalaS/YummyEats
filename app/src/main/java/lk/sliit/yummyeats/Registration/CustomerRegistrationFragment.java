package lk.sliit.yummyeats.Registration;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.EthiopicCalendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lk.sliit.yummyeats.Controller.InputValidater;
import lk.sliit.yummyeats.CustomerMainActivity;
import lk.sliit.yummyeats.LoginActivity;
import lk.sliit.yummyeats.Model.Customer;
import lk.sliit.yummyeats.R;
import lk.sliit.yummyeats.RegisterActivity;

public class CustomerRegistrationFragment extends Fragment  implements View.OnClickListener {

    EditText etRegisterCustomerFullName;
    EditText etRegisterCustomerMobile;
    EditText etRegisterCustomerEmail;
    EditText etRegisterCustomerPassword;
    EditText etRegisterCustomerConfirmPassword;

    InputValidater inputValidater = new InputValidater();

    //Init Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_customer = database.getReference("Customer");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_customer, container, false);

        etRegisterCustomerFullName = rootView.findViewById(R.id.et_register_cus_full_name);
        etRegisterCustomerMobile = rootView.findViewById(R.id.et_register_cus_mobile);
        etRegisterCustomerEmail = rootView.findViewById(R.id.et_register_cus_email);
        etRegisterCustomerPassword = rootView.findViewById(R.id.et_register_cus_password);
        etRegisterCustomerConfirmPassword = rootView.findViewById(R.id.et_register_cus_confirm_password);

        Button btnSignIn = rootView.findViewById(R.id.btn_cus_signIn);
        Button btnSignUp = rootView.findViewById(R.id.btn_cus_signUp);
        btnSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cus_signIn:
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);
                getActivity().finish();
                break;
            case R.id.btn_cus_signUp:

                // validate and register the Customer
                if(inputValidater.isEmpty(etRegisterCustomerFullName) || inputValidater.isEmpty(etRegisterCustomerMobile) ||
                inputValidater.isEmpty(etRegisterCustomerEmail) || inputValidater.isEmpty(etRegisterCustomerPassword) ||
                inputValidater.isEmpty(etRegisterCustomerPassword)){
                    Toast.makeText(getActivity(), "Text fields cannot be empty", Toast.LENGTH_SHORT).show();
                }

                else if(!inputValidater.isValidMobile(etRegisterCustomerMobile)){
                    Toast.makeText(getActivity(), "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
                }

                else if(!inputValidater.isValidEmail(etRegisterCustomerEmail)){
                    Toast.makeText(getActivity(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                }

                else if(!inputValidater.isValidPassword(etRegisterCustomerPassword)){
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                    View mView = getLayoutInflater().inflate(R.layout.dialog_invalid_password, null);
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                }

                else if(!etRegisterCustomerPassword.getText().toString().equals(etRegisterCustomerConfirmPassword.getText().toString())){
                    Toast.makeText(getActivity(), "Password Mismatched", Toast.LENGTH_SHORT).show();
                }

                else {
                    // add to Firebase
                    final ProgressDialog mDialog = new ProgressDialog(getActivity());
                    mDialog.setMessage("Please wait...");
                    mDialog.show();

                    table_customer.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //check weather already registered or not
                            if(dataSnapshot.child(etRegisterCustomerMobile.getText().toString()).exists()){
                                mDialog.dismiss();
                                Toast.makeText(getActivity(), "Phone number has already registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                mDialog.dismiss();
                                Customer customer = new Customer(etRegisterCustomerMobile.getText().toString(), etRegisterCustomerFullName.getText().toString(),
                                        etRegisterCustomerPassword.getText().toString(), etRegisterCustomerEmail.getText().toString());
                                table_customer.child(customer.getMobile()).setValue(customer);
                                Toast.makeText(getActivity(), "SignUp successful", Toast.LENGTH_SHORT).show();

                                Intent intent2 = new Intent(getActivity(), CustomerMainActivity.class);
                                startActivity(intent2);
                                getActivity().finish();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                break;
        }
    }
}
