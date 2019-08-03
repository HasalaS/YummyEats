package lk.sliit.yummyeats.Registration;

import android.app.AlertDialog;
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

import lk.sliit.yummyeats.Controller.InputValidater;
import lk.sliit.yummyeats.CustomerMainActivity;
import lk.sliit.yummyeats.DeliveryMainActivity;
import lk.sliit.yummyeats.LoginActivity;
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
                    Toast.makeText(getActivity(), "Text fields cannot be empty", Toast.LENGTH_SHORT).show();
                }

                else if(!inputValidater.isValidMobile(etRegisterRestaurantMobile)){
                    Toast.makeText(getActivity(), "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
                }

                else if(!inputValidater.isValidEmail(etRegisterRestaurantEmail)){
                    Toast.makeText(getActivity(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                }

                else if(!inputValidater.isValidPassword(etRegisterRestaurantPassword)){
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                    View mView = getLayoutInflater().inflate(R.layout.dialog_invalid_password, null);
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                }

                else if(!etRegisterRestaurantPassword.getText().toString().equals(etRegisterRestaurantConfirmPassword.getText().toString())){
                    Toast.makeText(getActivity(), "Password Mismatched", Toast.LENGTH_SHORT).show();
                }

                else {
                    Intent intent2 = new Intent(getActivity(), RestaurantMainActivity.class);
                    startActivity(intent2);
                    getActivity().finish();
                    break;
                }
        }
    }
}
