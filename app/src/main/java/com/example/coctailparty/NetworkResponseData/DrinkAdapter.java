package com.example.coctailparty.NetworkResponseData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coctailparty.R;
import com.example.coctailparty.displayScreen;
import com.example.coctailparty.selectionScreen;
import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.ViewHolder> {
    Context context;
    Drink[] drinkArray;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView rowName;
        TextView rowDescription;
        ImageView cocktailImage;
        RecyclerView recyclerView;
        OnClickListener goToDisplay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.coctailNameLabel1);
            rowDescription = itemView.findViewById(R.id.cocktailDescriptionLabel1);
            cocktailImage = itemView.findViewById(R.id.cocktailImage);
            recyclerView = itemView.findViewById(R.id.drinksListView);


        }
    }

    public DrinkAdapter(Context context, Drink[] drinkArray){
        this.context = context;
        this.drinkArray = drinkArray;
    }

    @NonNull
    @Override
    public DrinkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_drink,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkAdapter.ViewHolder holder, int position) {
        holder.rowName.setText(drinkArray[position].strDrink);
        holder.rowDescription.setText(drinkArray[position].strCategory);
        Picasso.get().
                load(drinkArray[position].strDrinkThumb).
                resize(250,250).
                centerCrop().
                into(holder.cocktailImage);

        holder.goToDisplay = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context,displayScreen.class);
                myIntent.putExtra("Drink", drinkArray[position]);
                context.startActivity(myIntent);
            }
        };

        holder.rowName.setOnClickListener(holder.goToDisplay);
        holder.rowDescription.setOnClickListener(holder.goToDisplay);
        holder.cocktailImage.setOnClickListener(holder.goToDisplay);
    }

    @Override
    public int getItemCount() {
        return drinkArray.length;
    }

}
