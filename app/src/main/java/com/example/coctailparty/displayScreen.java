package com.example.coctailparty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coctailparty.NetworkResponseData.Drink;
import com.example.coctailparty.NetworkResponseData.Drinks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class displayScreen extends AppCompatActivity {

    // MARK: - Variables

    TextView cocktailNameTextview;
    TextView cocktailCategoryTextview;
    TextView coctailIngredients;
    TextView cocktailInstructions;
    TextView coctailMeasurments;
    List<String> ingredients = new ArrayList<String>();
    List<String> measurments = new ArrayList<String>();

    // MARK: - Lifecycle methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_screen);
        cocktailNameTextview = findViewById(R.id.coctailNameTextview);
        cocktailCategoryTextview = findViewById(R.id.cocktailCategoryTextview);
        coctailIngredients = findViewById(R.id.ingredientsTextView);
        cocktailInstructions = findViewById(R.id.instructionsTextView);
        coctailMeasurments = findViewById(R.id.measurmentsTextView);
        Drink drink = (Drink) getIntent().getSerializableExtra("Drink");
        display(drink);
    }

    // MARK: - Helper methods

    private void getIngrediantsAndMeasuremnts(Drink drink) {
        String[] strIngerdiants = {
                drink.strIngredient1,
                drink.strIngredient2,
                drink.strIngredient3,
                drink.strIngredient4,
                drink.strIngredient5,
                drink.strIngredient6,
                drink.strIngredient7,
                drink.strIngredient8,
                drink.strIngredient9,
                drink.strIngredient10
        };

        String[] strMeasurments = {
                drink.strMeasure1,
                drink.strMeasure2,
                drink.strMeasure3,
                drink.strMeasure4,
                drink.strMeasure5,
                drink.strMeasure6,
                drink.strMeasure7,
                drink.strMeasure8,
                drink.strMeasure9,
                drink.strMeasure10
        };

        for (int i = 0; i < strIngerdiants.length; i++ ) {
            if (strIngerdiants[i] != null ) {
                ingredients.add(strIngerdiants[i]);
            }
            if (strMeasurments[i] != null) {
                measurments.add(strMeasurments[i]);
            }
        }
    }

    private String getMesuremntsString() {
        String mes = "";
        for (int i = 0; i<measurments.size(); i++) {
            mes = mes + measurments.get(i) + "\n";
        }
        return mes;
    }

    private String getIngredientString() {
        String ing = "";
        for (int i = 0; i<ingredients.size(); i++) {
            ing = ing + ingredients.get(i) + "\n";
        }
        return ing;
    }

    private void display(Drink drink) {
        cocktailNameTextview.setText(drink.strDrink);
        cocktailCategoryTextview.setText(drink.strCategory);
        cocktailInstructions.setText(drink.strInstructions);
        getIngrediantsAndMeasuremnts(drink);
        String measuremntsString = getMesuremntsString();
        String ingredientString = getIngredientString();
        coctailIngredients.setText(ingredientString);
        coctailMeasurments.setText(measuremntsString);
     }

}