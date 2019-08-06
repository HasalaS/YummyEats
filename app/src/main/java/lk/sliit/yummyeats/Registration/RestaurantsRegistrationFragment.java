package lk.sliit.yummyeats.Registration;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import lk.sliit.yummyeats.DeliveryMainActivity;
import lk.sliit.yummyeats.LoginActivity;
import lk.sliit.yummyeats.Model.Deliver;
import lk.sliit.yummyeats.Model.Restaurant;
import lk.sliit.yummyeats.R;
import lk.sliit.yummyeats.RestaurantMainActivity;

public class RestaurantsRegistrationFragment extends Fragment implements View.OnClickListener {

    EditText etRegisterRestaurantFullName;
    EditText etRegisterRestaurantMobile;
    EditText etRegisterRestaurantEmail;
    EditText etRegisterRestaurantAddress;
    EditText etRegisterRestaurantPassword;
    EditText etRegisterRestaurantConfirmPassword;

    InputValidater inputValidater = new InputValidater();

    //Init Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_restaurant = database.getReference("Restaurant");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_restaurant, container, false);



        etRegisterRestaurantFullName = rootView.findViewById(R.id.et_register_res_full_name);
        etRegisterRestaurantMobile = rootView.findViewById(R.id.et_register_res_mobile);
        etRegisterRestaurantEmail = rootView.findViewById(R.id.et_register_res_email);
        etRegisterRestaurantAddress = rootView.findViewById(R.id.et_register_res_address);
        etRegisterRestaurantPassword = rootView.findViewById(R.id.et_register_res_password);
        etRegisterRestaurantConfirmPassword = rootView.findViewById(R.id.et_register_res_confirm_password);


        Button btnSignIn = rootView.findViewById(R.id.btn_res_signIn);
        Button btnSignUp = rootView.findViewById(R.id.btn_res_signUp);
        btnSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_res_signIn:
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);
                getActivity().finish();
                break;
            case R.id.btn_res_signUp:

                // validate and register the Restaurant
                if (inputValidater.isEmpty(etRegisterRestaurantFullName)||inputValidater.isEmpty(etRegisterRestaurantMobile)||
                        inputValidater.isEmpty(etRegisterRestaurantEmail)||inputValidater.isEmpty(etRegisterRestaurantAddress)
                        ||inputValidater.isEmpty(etRegisterRestaurantPassword)||inputValidater.isEmpty(etRegisterRestaurantConfirmPassword)){
                    Toast.makeText(getActivity(), R.string.reg_empty_field, Toast.LENGTH_SHORT).show();
                }

                else if(!inputValidater.isValidMobile(etRegisterRestaurantMobile)){
                    Toast.makeText(getActivity(), R.string.reg_frag_mobile_valid, Toast.LENGTH_SHORT).show();
                }

                else if(!inputValidater.isValidEmail(etRegisterRestaurantEmail)){
                    Toast.makeText(getActivity(), R.string.reg_frag_email_valid, Toast.LENGTH_SHORT).show();
                }

                else if(!inputValidater.isValidPassword(etRegisterRestaurantPassword)){
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                    View mView = getLayoutInflater().inflate(R.layout.dialog_invalid_password, null);
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                }

                else if(!etRegisterRestaurantPassword.getText().toString().equals(etRegisterRestaurantConfirmPassword.getText().toString())){
                    Toast.makeText(getActivity(), R.string.reg_password_invalid, Toast.LENGTH_SHORT).show();
                }
                else{
                    // add to Firebase
                    final ProgressDialog mDialog = new ProgressDialog(getActivity());
                    mDialog.setMessage("Please wait...");
                    mDialog.show();
                    table_restaurant.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //check weather already registered or not
                            if (dataSnapshot.child(etRegisterRestaurantMobile.getText().toString()).exists()) {
                                mDialog.dismiss();
                                Toast.makeText(getActivity(), R.string.reg_phoneNo_exsist, Toast.LENGTH_SHORT).show();
                            } else {
                                mDialog.dismiss();
                                Restaurant restaurant = new Restaurant(etRegisterRestaurantMobile.getText().toString(), etRegisterRestaurantFullName.getText().toString(),
                                        etRegisterRestaurantPassword.getText().toString(), etRegisterRestaurantAddress.getText().toString(), etRegisterRestaurantEmail.getText().toString());
                                table_restaurant.child(restaurant.getMobile()).setValue(restaurant);
                                Toast.makeText(getActivity(), R.string.reg_successs, Toast.LENGTH_SHORT).show();
                                Intent intent2 = new Intent(getActivity(), RestaurantMainActivity.class);
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
