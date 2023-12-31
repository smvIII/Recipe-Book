package com.zybooks.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zybooks.recipebook.model.Recipe;
import com.zybooks.recipebook.repo.RecipeRepository;

public class RecipeCreateActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;
    Uri selectedImageUri;
    boolean imageSelected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_create);
    }

    public void onCreateClicked (View view)
    {
        if (imageSelected)
        {
            imageSelected = false;
            Recipe recipe = new Recipe();
            EditText editRecipeName = findViewById(R.id.edit_recipe_name);
            recipe.setRecipeName(editRecipeName.getText().toString());
            EditText editRecipeIngredients = findViewById(R.id.edit_recipe_ingredients);
            recipe.setIngredients(editRecipeIngredients.getText().toString());
            EditText editRecipeInstructions = findViewById(R.id.edit_recipe_instructions);
            recipe.setInstructions(editRecipeInstructions.getText().toString());
            recipe.setCategoryName((RecipeRepository.currentCategory).toString());
            recipe.setImageUri(selectedImageUri.toString());
            RecipeRepository.getInstance(getApplicationContext()).Add(recipe);
            Toast.makeText(this, recipe.getRecipeName() + " was added!", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Please select an image from your gallery", Toast.LENGTH_SHORT).show();

    }
    public void onUploadPhotoClicked(View view)
    {
        imageSelected = true;
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == SELECT_PICTURE)
            {
                selectedImageUri = data.getData();
                if (selectedImageUri != null)
                {
                    if (selectedImageUri != null)
                    {
                        final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
                        getContentResolver().takePersistableUriPermission(selectedImageUri, takeFlags);
                    }
                }
            }
        }
    }
}