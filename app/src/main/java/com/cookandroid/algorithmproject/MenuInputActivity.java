package com.cookandroid.algorithmproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MenuInputActivity extends AppCompatActivity {
    EditText edt_material;
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_input);

        edt_material = (EditText) findViewById(R.id.edt_material);
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_material.getText().toString().equals("")|| Integer.parseInt(edt_material.getText().toString()) == 0){
                    Toast.makeText(MenuInputActivity.this, "재료비를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent_result = new Intent(getApplicationContext(), MenuOutputActivity.class);
                    intent_result.putExtra("material", Integer.parseInt(edt_material.getText().toString()));
                    startActivity(intent_result);
                }
            }
        });
    }
}