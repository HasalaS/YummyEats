package lk.sliit.yummyeats;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import lk.sliit.yummyeats.Model.Food;
import lk.sliit.yummyeats.Model.SessionUser;

public class FoodRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    ProgressDialog pd;

    Button btnAddFood, btnUploadImage;
    EditText etName, etDescription, etPrice;
    RadioButton rbFood, rbBeverage;
    ImageView imFoodImage;

    //creating reference to firebase storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://yummyeats-85b04.appspot.com/");

    //Init Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_food = database.getReference("Food");

    Food food = new Food();
    String uploadedFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_registration);
        getSupportActionBar().hide();

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");

        btnAddFood = findViewById(R.id.btn_food_registration_addFood);
        btnUploadImage = findViewById(R.id.btn_food_registration_uploadImage);
        etName = findViewById(R.id.et_food_registration_name);
        etDescription = findViewById(R.id.et_food_registration_description);
        etPrice = findViewById(R.id.et_food_registration_price);

        rbFood = findViewById(R.id.rb_food_registration_food);
        rbBeverage = findViewById(R.id.rb_food_registration_beverage);

        imFoodImage = findViewById(R.id.iv_foodImage);

        btnAddFood.setOnClickListener(this);
        btnUploadImage.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FoodRegistrationActivity.this, RestaurantMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_food_registration_addFood:
                FileUploader();
                break;

            case R.id.btn_food_registration_uploadImage:
                Filechooser();
                break;
        }
    }

    private void FileUploader(){
        if(filePath != null) {
            pd.show();

            final StorageReference childRef = storageRef.child(SessionUser.restaurant.getMobile() + "_" + etName.getText().toString().trim().replace(" ", "_") + ".jpg");

            //uploading the image
            final UploadTask uploadTask = childRef.putFile(filePath);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    // getting uploaded image url and insert data into realtime database
                    childRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            food.setName(etName.getText().toString());
                            food.setDescription(etDescription.getText().toString());
                            food.setPrice(etPrice.getText().toString());
                            food.setRestaurant(SessionUser.restaurant.getAddress());
                            food.setResturantMobile(SessionUser.restaurant.getMobile());
                            food.setImage(uri.toString());

                            if (rbBeverage.isChecked() == true) {
                                food.setCategory("Beverage");
                            } else {
                                food.setCategory("Food");
                            }

                            try{
                                // insert into realtime database
                                table_food.push().setValue(food);
                            } catch (Exception e){
                                Toast.makeText(FoodRegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    pd.dismiss();

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(FoodRegistrationActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.dialog_food_added_successful, null);
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    // Delay two seconds for AlertDialog and return to the home page
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    }, 2000);



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(FoodRegistrationActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(FoodRegistrationActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void Filechooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                imFoodImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
