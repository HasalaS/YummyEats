package lk.sliit.yummyeats.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import lk.sliit.yummyeats.Model.Food;
import lk.sliit.yummyeats.R;

public class CustomFirebaseAdapter extends RecyclerView.Adapter<CustomFirebaseAdapter.CustomViewHolder> {

    Context context;
    ArrayList<Food> foodList;

    public CustomFirebaseAdapter(Context cntx, ArrayList<Food> foods){
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

        TextView name, restaurant, price, description;
        ImageView foodImage;

        public CustomViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.res_food_card_name);
            restaurant = itemView.findViewById(R.id.res_food_card_restaurant);
            price = itemView.findViewById(R.id.res_food_card_price);
            description = itemView.findViewById(R.id.res_food_card_description);
            foodImage = itemView.findViewById(R.id.res_food_card_image);
        }
    }

}
