package lk.sliit.yummyeats.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import lk.sliit.yummyeats.CustomerMainActivity;
import lk.sliit.yummyeats.LoginActivity;
import lk.sliit.yummyeats.Model.Customer;
import lk.sliit.yummyeats.R;
import lk.sliit.yummyeats.RegisterActivity;

public class CustomerRegistrationActivity extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_customer, container, false);

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
                Intent intent2 = new Intent(getActivity(), CustomerMainActivity.class);
                startActivity(intent2);
                getActivity().finish();
        }
    }
}
