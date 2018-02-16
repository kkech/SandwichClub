package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //Get TextViews By id
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView alsoKnownAsTv = findViewById(R.id.also_known_as_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView placeOfOriginTv = findViewById(R.id.place_of_origin_tv);

        //Place new line in every text view
        placeNewLine(descriptionTv);
        placeNewLine(alsoKnownAsTv);
        placeNewLine(ingredientsTv);
        placeNewLine(placeOfOriginTv);

        //add value from Sandwich Object to UI
        descriptionTv.append(sandwich.getDescription());
        for(int i = 0;i < sandwich.getAlsoKnownAs().size();i++) {
            alsoKnownAsTv.append(sandwich.getAlsoKnownAs().get(i));
            placeNewLine(alsoKnownAsTv);
        }


        for(int i = 0;i < sandwich.getIngredients().size();i++) {
            ingredientsTv.append(sandwich.getIngredients().get(i));
            placeNewLine(ingredientsTv);
        }

        placeOfOriginTv.append(sandwich.getPlaceOfOrigin());



    }


    /**
     * Get a @{@link TextView} and return it with a new line
     *
     * @param textView
     */
    private void placeNewLine(TextView textView){
        textView.append("\n");
    }
}
