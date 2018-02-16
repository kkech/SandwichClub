package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwichTotal = new JSONObject(json);
            JSONObject sandwichName = sandwichTotal.getJSONObject("name");

            ArrayList<String> alsoKnownAsList = getArrayListFromJsonArray(sandwichName.getJSONArray("alsoKnownAs"));

            ArrayList<String> ingredientsList = getArrayListFromJsonArray(sandwichTotal.getJSONArray("ingredients"));

            return new Sandwich(sandwichName.getString("mainName"),alsoKnownAsList,sandwichTotal.getString("placeOfOrigin"),sandwichTotal.getString("description"),sandwichTotal.getString("image"),ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get a @{@link JSONArray} and return a @{@link ArrayList} with those data.
     *
     * @param jsonArray
     * @return @{@link ArrayList} with the data in the @{@link JSONArray}
     * @throws JSONException
     */
    private static ArrayList<String> getArrayListFromJsonArray(JSONArray jsonArray) throws JSONException {

        ArrayList<String> returnList = new ArrayList<String>();
        if (jsonArray != null) {
            int len = jsonArray.length();
            for (int i=0;i<len;i++){
                returnList.add(jsonArray.get(i).toString());
            }
        }
        return returnList;
    }
}
