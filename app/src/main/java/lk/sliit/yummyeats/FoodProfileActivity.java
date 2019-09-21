package lk.sliit.yummyeats;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lk.sliit.yummyeats.Model.SessionUser;

public class FoodProfileActivity extends AppCompatActivity {

    //Init Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_food = database.getReference("Food");

    TextView tvName, tvDesc, tvPrice, tvCategory, tvHeaderNmme;
    ImageView foodImage;
    Button btnDelete;
    String foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_profile);
        getSupportActionBar().hide();

        btnDelete = findViewById(R.id.btn_food_profile_delete);

        tvName = findViewById(R.id.tv_food_profile_card_name_value);
        tvDesc = findViewById(R.id.tv_food_profile_card_desc_value);
        tvPrice = findViewById(R.id.tv_food_profile_card_price_value);
        tvCategory = findViewById(R.id.tv_food_profile_card_category_value);
        tvHeaderNmme = findViewById(R.id.tb_fp_header_name);
        foodImage = findViewById(R.id.iv_fp_foodImage);

        Bundle bundle = getIntent().getExtras();

        foodId = bundle.getString("ID");

        tvHeaderNmme.setText(bundle.getString("NAME"));
        tvName.setText(bundle.getString("NAME"));
        tvDesc.setText(bundle.getString("DESCRIPTION"));
        tvPrice.setText(bundle.getString("PRICE"));
        tvCategory.setText(bundle.getString("CATEGORY"));

        byte[] byteImage = bundle.getByteArray("IMAGE");
        foodImage.setImageBitmap(BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length));
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button btnCancel, btnDelete;
                final ViewGroup foodCard = (ViewGroup) v.getParent();

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(FoodProfileActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View mView = inflater.inflate(R.layout.dialog_food_delete_confirmation, null);

                btnCancel = mView.findViewById(R.id.btn_delete_food_cancel);
                btnDelete = mView.findViewById(R.id.btn_delete_food_delete);

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
                    @Override
                    public void onClick(View v) {

                        // remove record from firebaser realtime database
                        table_food.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if(dataSnapshot.hasChild(foodId)){
                                    table_food.child(foodId).removeValue();
                                    dialog.dismiss();
                                    Toast.makeText(FoodProfileActivity.this, "Food Item Deleted", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                                else {
                                    Toast.makeText(FoodProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(FoodProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            }
        });
    }

    public void showDialogFood(View view){
        TextView tvKey = null;
        TextView tvValue = null;

        ViewGroup card = (ViewGroup) view;
        tvKey = (TextView) card.getChildAt(0);
        tvValue = (TextView) card.getChildAt(1);

        final String key = tvKey.getText().toString();
        final String value = tvValue.getText().toString();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(FoodProfileActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_update, null);

        ViewGroup vg = (ViewGroup)mView;
        ViewGroup viewGroup = (ViewGroup) vg.getChildAt(0);

        TextView updateTvKey = (TextView) viewGroup.getChildAt(0);
        final EditText updateEtValue = (EditText) viewGroup.getChildAt(1);
        Button updateBtn = (Button) viewGroup.getChildAt(2);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Update the database
                try{
                    table_food.child(foodId).child(key.toLowerCase()).setValue(updateEtValue.getText().toString());
                    Toast.makeText(FoodProfileActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                } catch (Exception exc){
                    Toast.makeText(FoodProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(FoodProfileActivity.this, RestaurantMainActivity.class);
                startActivity(intent);
                finish();
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
        Intent intent = new Intent(FoodProfileActivity.this, RestaurantMainActivity.class);
        startActivity(intent);
        finish();
    }
}
