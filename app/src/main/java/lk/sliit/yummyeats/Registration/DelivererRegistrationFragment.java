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
import lk.sliit.yummyeats.R;

public class DelivererRegistrationFragment extends Fragment implements View.OnClickListener {

    EditText etRegisterDeliverFullName;
    EditText etRegisterDeliverMobile;
    EditText etRegisterDeliverEmail;
    EditText etRegisterDeliverPassword;
    EditText etRegisterDeliverConfirmPassword;

    InputValidater inputValidater = new InputValidater();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_deliverer, container, false);

        etRegisterDeliverFullName = rootView.findViewById(R.id.et_register_del_full_name);
        etRegisterDeliverMobile = rootView.findViewById(R.id.et_register_del_contact_no);
        etRegisterDeliverEmail = rootView.findViewById(R.id.et_register_del_email);
        etRegisterDeliverPassword = rootView.findViewById(R.id.et_register_del_password);
        etRegisterDeliverConfirmPassword = rootView.findViewById(R.id.et_register_del_confirm_password);

        Button btnSignIn = rootView.findViewById(R.id.btn_del_signIn);
        Button btnSignUp = rootView.findViewById(R.id.btn_del_signUp);
        btnSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_del_signIn:
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);
                getActivity().finish();
                break;
            case R.id.btn_del_signUp:

                // validate and register the Driver
                if (inputValidater.isEmpty(etRegisterDeliverFullName) || inputValidater.isEmpty(etRegisterDeliverMobile) || inputValidater.isEmpty(etRegisterDeliverEmail)
                        || inputValidater.isEmpty(etRegisterDeliverPassword) || inputValidater.isEmpty(etRegisterDeliverConfirmPassword)) {
                    Toast.makeText(getActivity(), "Text fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (!inputValidater.isValidMobile(etRegisterDeliverMobile)) {
                    Toast.makeText(getActivity(), "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
                } else if (!inputValidater.isValidEmail(etRegisterDeliverEmail)) {
                    Toast.makeText(getActivity(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                } else if (!inputValidater.isValidPassword(etRegisterDeliverPassword)) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                    View mView = getLayoutInflater().inflate(R.layout.dialog_invalid_password, null);
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                } else if (!etRegisterDeliverConfirmPassword.getText().toString().equals(etRegisterDeliverConfirmPassword.getText().toString())) {
                    Toast.makeText(getActivity(), "Password Mismatched", Toast.LENGTH_SHORT).show();

                    Intent intent2 = new Intent(getActivity(), DeliveryMainActivity.class);
                    startActivity(intent2);
                    getActivity().finish();
                    break;
                }
        }
    }
}
