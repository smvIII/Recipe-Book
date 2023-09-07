package com.zybooks.recipebook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.zybooks.recipebook.model.Recipe;
import com.zybooks.recipebook.repo.RecipeRepository;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecipeEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeEditFragment newInstance(String param1, String param2) {
        RecipeEditFragment fragment = new RecipeEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_edit, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        EditText editRecipeName = rootView.findViewById(R.id.edit_recipe_name);
        EditText editIngredients = rootView.findViewById(R.id.edit_recipe_ingredients);
        EditText editInstructions = rootView.findViewById(R.id.edit_recipe_instructions);

        Button editButton = rootView.findViewById(R.id.button_edit);

        editRecipeName.setText(RecipeRepository.currentRecipe.getRecipeName());
        editIngredients.setText(RecipeRepository.currentRecipe.getIngredients());
        editInstructions.setText(RecipeRepository.currentRecipe.getInstructions());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe updatedRecipe = RecipeRepository.currentRecipe;
                updatedRecipe.setRecipeName(editRecipeName.getText().toString());
                updatedRecipe.setIngredients(editIngredients.getText().toString());
                updatedRecipe.setInstructions(editInstructions.getText().toString());
                updatedRecipe.setCategoryName((RecipeRepository.currentCategory).toString());
                RecipeRepository.getInstance(getContext()).Update(updatedRecipe);
                Toast.makeText(getActivity(), updatedRecipe.getRecipeName() + " was updated!", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}