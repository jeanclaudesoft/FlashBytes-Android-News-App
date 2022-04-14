package com.claudylab.flashbytes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.claudylab.flashbytes.R;

import com.claudylab.flashbytes.adapters.LanguageAdapter;
import com.claudylab.flashbytes.models.Languages;

import java.util.ArrayList;
import java.util.List;

public class ChooseLanguageActivity extends AppCompatActivity implements LanguageAdapter.itemClicked{

    LanguageAdapter languageAdapter;
    RecyclerView rvLanguages, resultView;
    List<Languages> langageList;
    String choose = "";
    Button validate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        rvLanguages = findViewById(R.id.rvTopCat);
        validate = findViewById(R.id.url);
        langageList= new ArrayList<>();

//

        rvLanguages.setLayoutManager(new LinearLayoutManager(ChooseLanguageActivity.this, LinearLayoutManager.VERTICAL, false));
        langageList.add(new Languages(" Arabe", "ar"));
        langageList.add(new Languages("Anglais", "en"));
        langageList.add(new Languages("Espagnol", "es"));
        langageList.add(new Languages("French", "fr"));
        langageList.add(new Languages("Hébreux", "he"));
        langageList.add(new Languages("Hindi", "hi"));
        langageList.add(new Languages("Italien", "it"));
        langageList.add(new Languages("Japonais", "ja"));
        langageList.add(new Languages("Malayalam", "ml"));
        langageList.add(new Languages("Marathi", "mr"));
        langageList.add(new Languages("Allemand", "nl"));
        langageList.add(new Languages("Norvegien", "no"));
        langageList.add(new Languages("Portuguais", "pt"));
        langageList.add(new Languages("Russe", "ru"));
        langageList.add(new Languages("Ukrénien", "uk"));
        langageList.add(new Languages("Chinois", "zh"));

        languageAdapter = new LanguageAdapter(ChooseLanguageActivity.this, langageList, this);
        rvLanguages.setAdapter(languageAdapter);

        validate.setOnClickListener(view -> {
            if (choose.equals("")){
                Toast.makeText(ChooseLanguageActivity.this, "Veuillez sélectionner une langue", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences sharedPreferences = getSharedPreferences("flash",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("lang",choose);
                editor.apply();
                startActivity(new Intent(ChooseLanguageActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void sourceClick(int position, int selectItem) {
        choose = langageList.get(position).getCode();
    }
}