package com.example.coctailparty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coctailparty.NetworkResponseData.Drink;
import com.example.coctailparty.NetworkResponseData.DrinkAdapter;
import com.example.coctailparty.NetworkResponseData.Drinks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.zip.Inflater;

public class selectionScreen extends AppCompatActivity {

    // MARK: - Variables

    RecyclerView drinksListView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter drinkAdapter;
    ImageView cocktailImage;
    Drink[] arrayDrinks;

    // MARK: - Lifecycle methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);

        Drinks drinks = (Drinks) getIntent().getSerializableExtra("Drinks");
        arrayDrinks = drinks.drinks;

        drinksListView = findViewById(R.id.drinksListView);
        cocktailImage = findViewById(R.id.cocktailImage);
        layoutManager = new LinearLayoutManager(this);
        drinkAdapter = new DrinkAdapter(this,arrayDrinks);

        drinksListView.setLayoutManager(layoutManager);
        drinksListView.setAdapter(drinkAdapter);

    }
}