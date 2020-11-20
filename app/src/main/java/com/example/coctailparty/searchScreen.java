package com.example.coctailparty;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.coctailparty.NetworkResponseData.Drinks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class searchScreen extends AppCompatActivity {

    // MARK: - Variables

    Button searchName;
    Button searchCategory;
    Button searchIngredient;
    Button searchRandom;
    Button searchButton;
    EditText searchBar;
    String baseURL;
    private ProgressBar spinner;
    String searchURL = "https://www.thecocktaildb.com/api/json/v1/1/random.php";


    // Network call
    public void getCoctails (View view) {
        spinner.setVisibility(View.VISIBLE);
        String userInput = searchBar.getText().toString().toLowerCase();
        if (!(userInput.isEmpty())){
           searchURL = baseURL + userInput;
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, searchURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(),"Something went wrong, please try again",Toast.LENGTH_SHORT).show();
                            spinner.setVisibility(View.GONE);
                            return;
                        }
                        Gson gson = new Gson();
                        Drinks drinks = gson.fromJson(response,Drinks.class);
                        if (drinks.drinks == null) {
                            Toast.makeText(getApplicationContext(),"couldn't find any drinks with that name, please try another!",Toast.LENGTH_SHORT).show();
                            spinner.setVisibility(View.GONE);
                            return;
                        }
                        proceedToDisplay(drinks);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Something went wrong, please try again",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);

    }

    // MARK: Helper methods

    public void handleSelected (View button) {
        Button[] buttonsArray = {searchName,searchCategory,searchIngredient,searchRandom};
        List<Button> buttons = new ArrayList<Button>();
        buttons.addAll(Arrays.asList(buttonsArray));
        searchBar.setEnabled((button == searchRandom ? false : true));
        String tag = (String) button.getTag();
        baseURL = getBaseURL(tag);
        for (int i = 0; i < buttonsArray.length; i++ ) {
            buttons.get(i).setBackgroundColor((buttons.get(i) == button ? Color.BLUE : getResources().getColor(R.color.purple_500)));
        }
    }

    private String getBaseURL(String tag) {
        String baseURL = "";

        switch (tag) {
            case "0":
                baseURL = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=";
                break;
            case "1":
                baseURL = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=";
                break;
            case "2":
                baseURL = "https://www.thecocktaildb.com/api/json/v1/1/search.php?i=";
                break;
            default:
                baseURL = "https://www.thecocktaildb.com/api/json/v1/1/random.php";
                break;
        }

        return baseURL;
    }

    private void proceedToDisplay(Drinks drinks) {
        Intent myIntent = new Intent(getBaseContext(),selectionScreen.class);
        myIntent.putExtra("Drinks", drinks);
        spinner.setVisibility(View.GONE);
        startActivity(myIntent);
    }

    private void showAlert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to log out?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth.getInstance().signOut();
                        Intent myIntent = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(myIntent);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    // MARK: - Lifecycle methods

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        showAlert();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_screen);

        searchName = findViewById(R.id.searchByName);
        searchCategory = findViewById(R.id.searchByCategory);
        searchIngredient = findViewById(R.id.searchByIngredient);
        searchRandom = findViewById(R.id.searchByRandom);
        searchButton = findViewById(R.id.searchButton);
        searchBar = findViewById(R.id.searchbarEditText);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);

        spinner.bringToFront();
        spinner.setVisibility(View.GONE);

        searchName.callOnClick();

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        getCoctails(searchButton);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}