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
import lk.sliit.yummyeats.Model.Customer;
import lk.sliit.yummyeats.Model.Deliver;
import lk.sliit.yummyeats.R;

public class DelivererRegistrationFragment extends Fragment implements View.OnClickListener {

    EditText etRegisterDeliverFullName;
    EditText etRegisterDeliverMobile;
    EditText etRegisterDeliverEmail;
    EditText etRegisterDeliverVehicleNo;
    EditText etRegisterDeliverPassword;
    EditText etRegisterDeliverConfirmPassword;

    InputValidater inputValidater = new InputValidater();

    //Init Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_deliver = database.getReference("Deliver");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_deliverer, container, false);

        etRegisterDeliverFullName = rootView.findViewById(R.id.et_register_del_full_name);
        etRegisterDeliverMobile = rootView.findViewById(R.id.et_register_del_contact_no);
        etRegisterDeliverEmail = rootView.findViewById(R.id.et_register_del_email);
        etRegisterDeliverVehicleNo = rootView.findViewById(R.id.et_register_del_vehicle_no);
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
                        ||inputValidater.isEmpty(etRegisterDeliverVehicleNo)|| inputValidater.isEmpty(etRegisterDeliverPassword)
                        || inputValidater.isEmpty(etRegisterDeliverConfirmPassword)) {
                    Toast.makeText(getActivity(), R.string.reg_empty_field, Toast.LENGTH_SHORT).show();
                }

                else if (!inputValidater.isValidMobile(etRegisterDeliverMobile)) {
                    Toast.makeText(getActivity(), R.string.reg_frag_mobile_valid, Toast.LENGTH_SHORT).show();
                }

                else if (!inputValidater.isValidEmail(etRegisterDeliverEmail)) {
                    Toast.makeText(getActivity(), R.string.reg_frag_email_valid, Toast.LENGTH_SHORT).show();
                }

                else if (!inputValidater.isValidPassword(etRegisterDeliverPassword)) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                    View mView = getLayoutInflater().inflate(R.layout.dialog_invalid_password, null);
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();

                } else if (!etRegisterDeliverConfirmPassword.getText().toString().equals(etRegisterDeliverConfirmPassword.getText().toString())) {
                    Toast.makeText(getActivity(), R.string.reg_password_invalid, Toast.LENGTH_SHORT).show();
                }

                else{
                    // add to Firebase
                    final ProgressDialog mDialog = new ProgressDialog(getActivity());
                    mDialog.setMessage("Please wait...");
                    mDialog.show();

                    table_deliver.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //check weather already registered or not
                            if (dataSnapshot.child(etRegisterDeliverMobile.getText().toString()).exists()) {
                                mDialog.dismiss();
                                Toast.makeText(getActivity(), R.string.reg_phoneNo_exsist, Toast.LENGTH_SHORT).show();
                            } else {
                                mDialog.dismiss();
                                Deliver deliver = new Deliver(etRegisterDeliverMobile.getText().toString(), etRegisterDeliverFullName.getText().toString(),
                                        etRegisterDeliverPassword.getText().toString(), etRegisterDeliverEmail.getText().toString(), etRegisterDeliverVehicleNo.getText().toString());
                                table_deliver.child(deliver.getMobile()).setValue(deliver);
                                Toast.makeText(getActivity(), R.string.reg_successs, Toast.LENGTH_SHORT).show();
                                Intent intent2 = new Intent(getActivity(), DeliveryMainActivity.class);
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