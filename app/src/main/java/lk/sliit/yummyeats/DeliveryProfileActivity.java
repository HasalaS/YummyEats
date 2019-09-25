package lk.sliit.yummyeats;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeliveryProfileActivity extends AppCompatActivity {

    //Init Firebase Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_deliver = database.getReference("Deliver");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_profile);
        getSupportActionBar().hide();


    }

    public void showDialogDeliver(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(DeliveryProfileActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_update, null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DeliveryProfileActivity.this, DeliveryMainActivity.class);
        startActivity(intent);
        finish();
    }
}
