package lk.sliit.yummyeats.ui.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import lk.sliit.yummyeats.FoodProfileActivity;
import lk.sliit.yummyeats.Model.Food;
import lk.sliit.yummyeats.R;

public class CustomFbResAdapter extends RecyclerView.Adapter<CustomFbResAdapter.CustomViewHolder> {

    Context context;
    ArrayList<Food> foodList;

    //Init Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_food = database.getReference("Food");

    public CustomFbResAdapter(Context cntx, ArrayList<Food> foods){
        context = cntx;
        foodList = foods;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.restaurent_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int position) {
        customViewHolder.id.setText(foodList.get(position).getId());
        customViewHolder.name.setText(foodList.get(position).getName());
        customViewHolder.restaurant.setText(foodList.get(position).getRestaurant());
        customViewHolder.price.setText(foodList.get(position).getPrice());
        customViewHolder.description.setText(foodList.get(position).getDescription());
        Picasso.get().load(foodList.get(position).getImage()).into(customViewHolder.foodImage);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView name, restaurant, price, description, id;
        ImageView foodImage;
        Button btnUpdata, btnDelete;

        public CustomViewHolder(View itemView){
            super(itemView);
            id = itemView.findViewById(R.id.food_card_id);
            name = itemView.findViewById(R.id.res_food_card_name);
            restaurant = itemView.findViewById(R.id.res_food_card_restaurant);
            price = itemView.findViewById(R.id.res_food_card_price);
            description = itemView.findViewById(R.id.res_food_card_description);
            foodImage = itemView.findViewById(R.id.res_food_card_image);
            btnUpdata = itemView.findViewById(R.id.res_food_card_btn_update);
            btnDelete = itemView.findViewById(R.id.res_food_card_btn_delete);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Button btnCancel, btnDelete;
                    final ViewGroup foodCard = (ViewGroup) v.getParent();

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
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
                                    TextView tvID = (TextView) foodCard.getChildAt(0);

                                    if(dataSnapshot.hasChild(tvID.getText().toString())){
                                        table_food.child(tvID.getText().toString()).removeValue();
                                        dialog.dismiss();
                                        Toast.makeText(context, "Food Item Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });

            btnUpdata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        // Converting image to byte array
                        Drawable drawable = foodImage.getDrawable();
                        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] byteImage = baos.toByteArray();

                        Intent intent = new Intent(context, FoodProfileActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("ID", id.getText().toString());
                        bundle.putString("NAME", name.getText().toString());
                        bundle.putString("RESTAURANT", restaurant.getText().toString());
                        bundle.putString("PRICE", price.getText().toString());
                        bundle.putString("DESCRIPTION", description.getText().toString());
                        bundle.putString("CATEGORY", "Food");
                        bundle.putByteArray("IMAGE", byteImage);

                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    } catch (Exception exc){
                        Toast.makeText(context, "Please wait until fetching data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
